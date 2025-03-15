package com.example.brash.aprendizado.gestaoDeConteudo.data.repository

import android.util.Log
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Baralho
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Pasta
import com.example.brash.nucleo.domain.model.Usuario
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await

class PastaRepository {

    private val fireStoreDB = FirebaseFirestore.getInstance()
    private val fireBaseAuth = FirebaseAuth.getInstance()

    suspend fun createFolder(name : String): Result<Unit> {
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
                "name" to name,
            )
            documentRef.set(newFolder).await()

            Result.success(Unit)
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


    fun moveDeck(targetFolder : Pasta, sourceFolder : Pasta, deck : Baralho, onFailure: () -> Unit){

    }

    fun addDeck(folder : Pasta, deck : Baralho){

        val currentUserEmail = fireBaseAuth.currentUser?.email
        if (currentUserEmail.isNullOrEmpty()) {
            return
        }

        val decksRef = fireStoreDB
            .collection("users")
            .document(currentUserEmail)
            .collection("folders")
            .document(folder.idPasta)
            .collection("decks")

        val deckRef = decksRef.add(hashMapOf<String, Any>())
        deckRef
            .addOnSuccessListener { document ->
                val generatedId = document.id

                val newDeck = hashMapOf(
                    "id" to generatedId,
                    "name" to deck.nome,
                    "description" to deck.descricao,
                    "public" to deck.publico,
                    "numberNewCardsPerDay" to deck.cartoesNovosPorDia,
                )
                document.set(newDeck)
                    .addOnSuccessListener {
                        Log.e("debug add deck", "certo 1")
                    }
                    .addOnFailureListener {
                        Log.e("debug add deck", "erro 1")
                    }
            }
            .addOnFailureListener {
                Log.e("debug add deck", "erro 2")
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


}