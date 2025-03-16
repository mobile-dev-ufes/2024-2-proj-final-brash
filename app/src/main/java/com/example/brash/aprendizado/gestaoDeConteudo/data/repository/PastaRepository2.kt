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

class PastaRepository2 {

    private val fireStoreDB = FirebaseFirestore.getInstance()
    private val fireBaseAuth = FirebaseAuth.getInstance()

    suspend fun createFolder2(pasta : Pasta) : Result<String>{
        val currentUserEmail = fireBaseAuth.currentUser?.email
        if (currentUserEmail.isNullOrEmpty()) {
            return Result.failure(Throwable("Usuário não autenticado"))
        }
        return runCatching {

            val foldersRef = fireStoreDB.collection("folders")
            val newFolderRef = foldersRef.add(hashMapOf<String, Any>()).await()
            val newFolderId = newFolderRef.id

            val newFolderInfo = hashMapOf(
                "id" to newFolderId,
                "userId" to currentUserEmail,
                "name" to pasta.nome,
            )

            newFolderRef.set(newFolderInfo).await()
            newFolderId
        }

    }

    suspend fun updateFolder2(folder : Pasta, newName : String) : Result<Unit>{
        val currentUserEmail = fireBaseAuth.currentUser?.email
        if (currentUserEmail.isNullOrEmpty()) {
            return Result.failure(Throwable("Usuário não autenticado"))
        }
        return runCatching {
            val folderRef = fireStoreDB.collection("folders").document(folder.idPasta)
            val newInfo = mapOf(
                "name" to newName
            )
            folderRef.update(newInfo).await()
        }
    }


    suspend fun deleteFolder2(folder: Pasta): Result<Unit> {
        val currentUserEmail = fireBaseAuth.currentUser?.email
        if (currentUserEmail.isNullOrEmpty()) {
            return Result.failure(Throwable("Usuário não autenticado"))
        }
        return runCatching { // A pasta vai estar vazia, não precisa de exclusão recursiva.
            val folderRef = fireStoreDB.collection("folders").document(folder.idPasta)
            folderRef.delete().await()
        }
    }


    suspend fun getFolders2(): Result<List<Pasta>> {
        val currentUserEmail = fireBaseAuth.currentUser?.email
        if (currentUserEmail.isNullOrEmpty()) {
            return Result.failure(Throwable("Usuário não autenticado"))
        }

        return runCatching {
            val folderList = mutableListOf<Pasta>()

            val foldersQuerySnapshot = fireStoreDB.collection("folders")
                .whereEqualTo("userId", currentUserEmail)
                .get().await()

            for(folderDocument in foldersQuerySnapshot){
                val documentData = folderDocument.data
                val folder = Pasta(
                    idPasta = documentData["id"].toString(),
                    nome = documentData["name"].toString(),
                )
                folderList.add(folder)
                recGetDecks2(folder)
            }

            val root = Pasta(
                idPasta = "root/$currentUserEmail",
                nome = "root",
            )

            folderList.add(root)
            recGetDecks2(root)

            folderList
        }
    }

    private suspend fun recGetDecks2(folderObject : Pasta){

        val deckQuerySnapshot = fireStoreDB.collection("decks")
            .whereEqualTo("folderId", folderObject.idPasta)
            .get().await()

        for(deckDocument in deckQuerySnapshot){
            val deckData = deckDocument.data
            val deckObject = Baralho(
                idBaralho = deckData["id"].toString(),
                nome = deckData["name"].toString(),
                descricao = deckData["description"].toString(),
                cartoesNovosPorDia = deckData["numberNewCardsPerDay"].toString().toInt(),
                publico = deckData["public"].toString().toBoolean(),
                pasta = folderObject
            )
            folderObject.baralhos.add(deckObject)
        }
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

    suspend fun moveDeckToFolder2(folder : Pasta, deck : Baralho) : Result<Unit>{
        val currentUserEmail = fireBaseAuth.currentUser?.email
        if (currentUserEmail.isNullOrEmpty()) {
            return Result.failure(Throwable("Usuário não autenticado"))
        }
        return runCatching {

            val deckRef = fireStoreDB.collection("decks").document(deck.idBaralho)
            val newDeckInfo = mapOf(
                "folderId" to folder.idPasta
            )
            deckRef.update(newDeckInfo).await()
        }
    }

}