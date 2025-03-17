package com.example.brash.aprendizado.gestaoDeConteudo.data.repository

import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Cartao
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Dica
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

/**
 * Repository for managing hints (Dica) associated with cards (Cartao) in the Firestore database.
 * Provides functions to create, update, and delete hints.
 */
class DicaRepository2 {

    private val fireStoreDB = FirebaseFirestore.getInstance()
    private val fireBaseAuth = FirebaseAuth.getInstance()

    /**
     * Creates a new hint for a card.
     *
     * @param card The card to which the hint will be associated.
     * @param hint The hint to be created.
     * @return A Result containing the ID of the newly created hint or an error.
     */
    suspend fun createHint2(card : Cartao, hint : Dica) : Result<String>{
        val currentUserEmail = fireBaseAuth.currentUser?.email
            ?: return Result.failure(Throwable("Usuário não autenticado"))

        return runCatching {
            val hintsRef = fireStoreDB.collection("hints")
            val newHintRef = hintsRef.add(hashMapOf<String, Any>()).await()

            val newHintId = newHintRef.id
            val newHintInfo = hashMapOf(
                "id" to newHintId,
                "cardId" to card.idCartao,
                "text" to hint.texto,
            )
            newHintRef.set(newHintInfo).await()
            newHintId
        }
    }

    /**
     * Deletes a hint from the Firestore database.
     *
     * @param hint The hint to be deleted.
     * @return A Result indicating the success or failure of the operation.
     */
    suspend fun deleteHint2(hint : Dica) : Result<Unit>{
        val currentUserEmail = fireBaseAuth.currentUser?.email
            ?: return Result.failure(Throwable("Usuário não autenticado"))
        return runCatching {
            val hintsRef = fireStoreDB.collection("hints")
            val hintRef = hintsRef.document(hint.idDica)
            hintRef.delete().await()
        }
    }

    /**
     * Updates the text of an existing hint.
     *
     * @param hint The hint to be updated.
     * @param text The new text for the hint.
     * @return A Result indicating the success or failure of the operation.
     */
    suspend fun updateHint2(hint : Dica, text : String) : Result<Unit>{
        val currentUserEmail = fireBaseAuth.currentUser?.email
            ?: return Result.failure(Throwable("Usuário não autenticado"))

        return runCatching {
            val hintsRef = fireStoreDB.collection("hints")
            val hintRef = hintsRef.document(hint.idDica)
            val hintUpdateInfo = mapOf(
                "text" to text
            )
            hintRef.update(hintUpdateInfo).await()
        }
    }


}