package com.example.brash.aprendizado.gestaoDeConteudo.data.repository

import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Anotacao
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Baralho
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

/**
 * Repository for managing annotations (notes) in Firebase Firestore.
 *
 * This class provides methods to create, delete, and update notes associated with decks.
 */

class AnotacaoRepository2 {

    private val fireStoreDB = FirebaseFirestore.getInstance()
    private val fireBaseAuth = FirebaseAuth.getInstance()

    /**
     * Creates a new note in Firestore.
     *
     * @param deck The deck to which the note belongs.
     * @param note The note to be created.
     * @return A [Result] containing the newly created note's ID or an error if the operation fails.
     */
    suspend fun createNote2(deck : Baralho, note : Anotacao) : Result<String>{
        val currentUserEmail = fireBaseAuth.currentUser?.email
            ?: return Result.failure(Throwable("Usuário não autenticado"))

        return runCatching {
            val notesRef = fireStoreDB.collection("notes")
            val newNoteRef = notesRef.add(hashMapOf<String, Any>()).await()
            val newNoteId = newNoteRef.id
            val newNoteInfo = hashMapOf(
                "id" to newNoteId,
                "deckId" to deck.idBaralho,
                "name" to note.nome,
                "text" to note.texto,
            )
            newNoteRef.set(newNoteInfo).await()
            newNoteId
        }
    }

    /**
     * Deletes a note from Firestore.
     *
     * @param note The note to be deleted.
     * @return A [Result] indicating success or failure of the operation.
     */
    suspend fun deleteNote2(note : Anotacao) : Result<Unit>{
        val currentUserEmail = fireBaseAuth.currentUser?.email
            ?: return Result.failure(Throwable("Usuário não autenticado"))

        return runCatching {
            val notesRef = fireStoreDB.collection("notes")
            val noteRef = notesRef.document(note.idAnotacao)
            noteRef.delete().await()
        }
    }

    /**
     * Updates an existing note in Firestore.
     *
     * @param note The note to be updated.
     * @param name The new name for the note.
     * @param text The new text content for the note.
     * @return A [Result] indicating success or failure of the operation.
     */
    suspend fun updateNote2(note : Anotacao, name : String, text: String) : Result<Unit>{
        val currentUserEmail = fireBaseAuth.currentUser?.email
            ?: return Result.failure(Throwable("Usuário não autenticado"))

        return runCatching {
            val notesRef = fireStoreDB.collection("notes")
            val noteRef = notesRef.document(note.idAnotacao)
            val noteUpdateInfo = mapOf(
                "name" to name,
                "text" to text,
            )
            noteRef.update(noteUpdateInfo).await()
        }
    }

}