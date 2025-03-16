package com.example.brash.aprendizado.gestaoDeConteudo.data.repository

import android.util.Log
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Baralho
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Pasta
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import org.w3c.dom.Document
import java.time.ZoneId
import java.util.Date

class PastaRepository {

    private val fireStoreDB = FirebaseFirestore.getInstance()
    private val fireBaseAuth = FirebaseAuth.getInstance()

    suspend fun createFolder(pasta: Pasta): Result<String> {
        val currentUserEmail = fireBaseAuth.currentUser?.email
        if (currentUserEmail.isNullOrEmpty()) {
            return Result.failure(Throwable("Usuário não autenticado"))
        }

        return try {
            val foldersRef = fireStoreDB.collection("users")
                .document(currentUserEmail)
                .collection("folders")

            val documentRef = foldersRef.add(hashMapOf<String, Any>()).await()
            val generatedId = documentRef.id

            val newFolder = hashMapOf(
                "id" to generatedId,
                "name" to pasta.nome,
            )
            documentRef.set(newFolder).await()

            Result.success(generatedId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateFolder(folder : Pasta, newName : String) : Result<Unit>{
        val currentUserEmail = fireBaseAuth.currentUser?.email
        if (currentUserEmail.isNullOrEmpty()) {
            return Result.failure(Throwable("Usuário não autenticado"))
        }

        return try {
            val folderRef = fireStoreDB
                .collection("users")
                .document(currentUserEmail)
                .collection("folders")
                .document(folder.idPasta)

            val newInfo = mapOf(
                "name" to newName
            )
            folderRef.update(newInfo).await()
            Result.success(Unit)
        }
        catch (e : Exception){
            Result.failure(e)
        }
    }

    suspend fun deleteFolder(folder: Pasta): Result<Unit> {
        val currentUserEmail = fireBaseAuth.currentUser?.email
        if (currentUserEmail.isNullOrEmpty()) {
            return Result.failure(Throwable("Usuário não autenticado"))
        }
        return runCatching { // A pasta vai estar vazia, não precisa de exclusão recursiva.
            val folderRef = fireStoreDB
                .collection("users")
                .document(currentUserEmail)
                .collection("folders")
                .document(folder.idPasta)
            folderRef.delete().await()
            Unit
        }
    }


    suspend fun getFolders(): Result<List<Pasta>> {
        val foldersList = mutableListOf<Pasta>()
        val currentUserEmail = fireBaseAuth.currentUser?.email

        if (currentUserEmail.isNullOrEmpty()) {
            return Result.failure(Throwable("Usuário não autenticado"))
        }

        return try {
            val userRef = fireStoreDB.collection("users").document(currentUserEmail)
            val foldersRef = userRef.collection("folders")
            val rootRef = userRef.collection("root")

            val rootFolder = Pasta(idPasta = "root", nome = "root")
            foldersList.add(rootFolder)

            recGetDecks(rootRef, rootFolder)

            val foldersSnapshot = foldersRef.get().await()
            for (document in foldersSnapshot) {
                val documentData = document.data
                val folder = Pasta(
                    idPasta = documentData["id"].toString(),
                    nome = documentData["name"].toString(),
                )
                foldersList.add(folder)

                recGetDecks(document.reference.collection("decks"), folder)
            }

            Result.success(foldersList)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private suspend fun recGetDecks(decksRef: CollectionReference, folder: Pasta) {
        val deckSnapshot = decksRef.get().await()
        for (deckDocument in deckSnapshot) {
            val deckData = deckDocument.data
            val deck = Baralho(
                idBaralho = deckData["id"].toString(),
                nome = deckData["name"].toString(),
                descricao = deckData["description"].toString(),
                cartoesNovosPorDia = deckData["numberNewCardsPerDay"].toString().toInt(),
                publico = deckData["public"].toString().toBoolean(),
                pasta = folder
            )
            folder.baralhos.add(deck)
        }
        return
    }

    private fun getDeckReference(deck: Baralho, folder: Pasta, currentUserEmail: String): DocumentReference {
        val userRef = fireStoreDB.collection("users").document(currentUserEmail)
        return if (folder.idPasta != "root") {
            userRef.collection("folders").document(folder.idPasta).collection("decks").document(deck.idBaralho)
        } else {
            userRef.collection("root").document(deck.idBaralho)
        }
    }

    private fun getDecksReference(folder : Pasta, currentUserEmail : String) : CollectionReference{
        val userRef = fireStoreDB.collection("users").document(currentUserEmail)
        return if (folder.idPasta != "root") {
            userRef.collection("folders").document(folder.idPasta).collection("decks")
        } else {
            userRef.collection("root")
        }
    }

    // users/emaildousuariopostoubaralho/root

    suspend fun copyDeck(folder: Pasta, deck: Baralho): Result<String> {
        val currentUserEmail = fireBaseAuth.currentUser?.email
            ?: return Result.failure(Throwable("Usuário não autenticado"))

        return runCatching {
            val folderDeck = deck.pasta ?: return Result.failure(Throwable("Pasta do deck não encontrada (copyDeck)"))
            val deckRef = getDeckReference(deck, folderDeck, currentUserEmail) // Localização original do baralho

            val decksRef = getDecksReference(folder, currentUserEmail) // destino da cópia
            val newDeckRef = decksRef.add(hashMapOf<String, Any>()).await()
            val newDeckGeneratedId = newDeckRef.id

            val deckRefSnapshot = deckRef.get().await()
            val deckData = deckRefSnapshot.data ?: return Result.failure(Exception("Ocorreu algum erro pegando dados do baralho (copyDeck::PastaRepository)"))

            val isPublic = deckData["public"].toString().toBoolean()
            val newDeckInfo = hashMapOf(
                "id" to newDeckGeneratedId,
                "name" to deckData["name"].toString(),
                "description" to deckData["description"].toString(),
                "public" to isPublic,
                "numberNewCardsPerDay" to deckData["numberNewCardsPerDay"].toString().toInt(),
            )

            if(isPublic){ // cria entrada pública pro novo baralho
                val publicDecksRef = fireStoreDB.collection("publicDecks")
                val newPublicDeckRef = publicDecksRef.add(hashMapOf<String, Any>()).await()

                newDeckInfo["publicId"] = newPublicDeckRef.id
                val newPublicDeckInfo = hashMapOf(
                    "id" to newPublicDeckRef.id,
                    "userId" to currentUserEmail,
                    "deckPath" to newDeckRef.path,
                )
                newPublicDeckRef.set(newPublicDeckInfo).await()
            }

            newDeckRef.set(newDeckInfo).await()

            val newCardsRef = newDeckRef.collection("cards")
            val cardsRef = deckRef.collection("cards")

            val cardsSnapshot = cardsRef.get().await()
            for (card in cardsSnapshot) {
                val originalCardId = card.id
                val newCardRef = newCardsRef.document(originalCardId)

                val cardData = card.data.toMutableMap()
                newCardRef.set(cardData).await()

                val hintsRefSnapshot = card.reference.collection("hints").get().await()
                val newHintsRef = newCardRef.collection("hints")
                for (hint in hintsRefSnapshot) {
                    val originalHintId = hint.id
                    val newHintRef = newHintsRef.document(originalHintId)

                    val hintData = hint.data.toMutableMap()
                    newHintRef.set(hintData).await()
                }
            }

            newDeckGeneratedId
        }
    }

}