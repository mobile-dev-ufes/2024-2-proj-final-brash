package com.example.brash.aprendizado.gestaoDeConteudo.data.repository

import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Anotacao
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Baralho
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AnotacaoRepository2 {

    private val fireStoreDB = FirebaseFirestore.getInstance()
    private val fireBaseAuth = FirebaseAuth.getInstance()

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

    suspend fun deleteNote2(note : Anotacao) : Result<Unit>{
        val currentUserEmail = fireBaseAuth.currentUser?.email
            ?: return Result.failure(Throwable("Usuário não autenticado"))

        return runCatching {
            val notesRef = fireStoreDB.collection("notes")
            val noteRef = notesRef.document(note.idAnotacao)
            noteRef.delete().await()
        }
    }

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