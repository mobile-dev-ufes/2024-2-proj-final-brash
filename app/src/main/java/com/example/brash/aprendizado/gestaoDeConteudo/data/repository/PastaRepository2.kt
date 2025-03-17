package com.example.brash.aprendizado.gestaoDeConteudo.data.repository

import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Baralho
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Pasta
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await


/**
 * Repository for managing folders (Pasta) and decks (Baralho) in the Firestore database.
 * Provides functions to create, update, delete, and retrieve folders, as well as moving decks between folders.
 */
class PastaRepository2 {

    private val fireStoreDB = FirebaseFirestore.getInstance()
    private val fireBaseAuth = FirebaseAuth.getInstance()

    /**
     * Creates a new folder in the Firestore database.
     *
     * @param pasta The folder to be created.
     * @return A Result containing the ID of the newly created folder or an error.
     */
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

    /**
     * Updates the name of an existing folder.
     *
     * @param folder The folder to be updated.
     * @param newName The new name for the folder.
     * @return A Result indicating the success or failure of the operation.
     */
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


    /**
     * Deletes a folder from the Firestore database.
     *
     * @param folder The folder to be deleted.
     * @return A Result indicating the success or failure of the operation.
     */
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

    /**
     * Retrieves all folders belonging to the current user from the Firestore database.
     *
     * @return A Result containing a list of folders or an error.
     */
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

    /**
     * Recursively retrieves all decks for a given folder.
     *
     * @param folderObject The folder to retrieve decks from.
     */
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

    /**
     * Gets the reference to a specific deck document.
     *
     * @param deck The deck for which to get the reference.
     * @param folder The folder to which the deck belongs.
     * @param currentUserEmail The email of the current user.
     * @return A DocumentReference pointing to the deck document in Firestore.
     */
    private fun getDeckReference(deck: Baralho, folder: Pasta, currentUserEmail: String): DocumentReference {
        val userRef = fireStoreDB.collection("users").document(currentUserEmail)
        return if (folder.idPasta != "root") {
            userRef.collection("folders").document(folder.idPasta).collection("decks").document(deck.idBaralho)
        } else {
            userRef.collection("root").document(deck.idBaralho)
        }
    }

    /**
     * Gets the reference to the collection of decks for a specific folder.
     *
     * @param folder The folder for which to get the deck collection reference.
     * @param currentUserEmail The email of the current user.
     * @return A CollectionReference pointing to the decks collection in Firestore.
     */
    private fun getDecksReference(folder : Pasta, currentUserEmail : String) : CollectionReference{
        val userRef = fireStoreDB.collection("users").document(currentUserEmail)
        return if (folder.idPasta != "root") {
            userRef.collection("folders").document(folder.idPasta).collection("decks")
        } else {
            userRef.collection("root")
        }
    }

    /**
     * Moves a deck to a different folder.
     *
     * @param folder The folder to which the deck will be moved.
     * @param deck The deck to be moved.
     * @return A Result indicating the success or failure of the operation.
     */
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