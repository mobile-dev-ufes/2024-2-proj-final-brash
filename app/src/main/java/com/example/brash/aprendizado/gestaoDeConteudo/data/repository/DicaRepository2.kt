package com.example.brash.aprendizado.gestaoDeConteudo.data.repository

import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Cartao
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Dica
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class DicaRepository2 {

    private val fireStoreDB = FirebaseFirestore.getInstance()
    private val fireBaseAuth = FirebaseAuth.getInstance()


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


}