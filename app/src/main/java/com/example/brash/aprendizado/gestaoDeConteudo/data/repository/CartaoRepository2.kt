package com.example.brash.aprendizado.gestaoDeConteudo.data.repository

import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Baralho
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Cartao
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.CategoriaDoAprendizado
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Dica
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date

class CartaoRepository2 {

    private val fireStoreDB = FirebaseFirestore.getInstance()
    private val fireBaseAuth = FirebaseAuth.getInstance()


    suspend fun createCard2(deck: Baralho, card: Cartao): Result<String> {
        val currentUserEmail = fireBaseAuth.currentUser?.email
        if (currentUserEmail.isNullOrEmpty()) {
            return Result.failure(Throwable("Usuário não autenticado"))
        }
        return runCatching {

            val deckId = deck.idBaralho
            val cardsRef = fireStoreDB.collection("cards")
            val newCardRef = cardsRef.add(hashMapOf<String, Any>()).await()
            val newCardId = newCardRef.id

            val newCardInfo = hashMapOf(
                "id" to newCardId,
                "deckId" to deckId,
                "question" to card.pergunta,
                "answer" to card.resposta,
                "reviewFactor" to card.fatorDeRevisao,
                "reviewInterval" to card.intervaloRevisao,
                "reviewDate" to Timestamp(Date.from(card.dataDeRevisao.atZone(ZoneId.systemDefault()).toInstant())),
                "categoryOfLearning" to card.categoriaDoAprendizado.name
            )

            newCardRef.set(newCardInfo).await()
            newCardId // retornando o id
        }
    }

    suspend fun updateCardFromReview2(card : Cartao, reviewFactor : Double, reviewInterval : Int, reviewDate: LocalDateTime, categoryOfLearning : CategoriaDoAprendizado) : Result<Unit>{
        val currentUserEmail = fireBaseAuth.currentUser?.email
        if (currentUserEmail.isNullOrEmpty()) {
            return Result.failure(Throwable("Usuário não autenticado (deleteCard)"))
        }
        return runCatching {
            val cardRef = fireStoreDB.collection("cards").document(card.idCartao)
            val updateInfo = mapOf(
                "reviewFactor" to reviewFactor,
                "reviewInterval" to reviewInterval,
                "reviewDate" to Timestamp(Date.from(reviewDate.atZone(ZoneId.systemDefault()).toInstant())),
                "categoryOfLearning" to categoryOfLearning.name,
            )
            cardRef.update(updateInfo).await()
        }
    }


    suspend fun updateCardQA2(card : Cartao, question : String, answer : String) : Result<Unit>{
        val currentUserEmail = fireBaseAuth.currentUser?.email
        if (currentUserEmail.isNullOrEmpty()) {
            return Result.failure(Throwable("Usuário não autenticado (deleteCard)"))
        }
        return runCatching {
            val cardRef = fireStoreDB.collection("cards").document(card.idCartao)
            val updateInfo = mapOf(
                "question" to question,
                "answer" to answer,
            )
            cardRef.update(updateInfo).await()
        }
    }

    suspend fun deleteCard2(card: Cartao): Result<Unit>{
        val currentUserEmail = fireBaseAuth.currentUser?.email
        if (currentUserEmail.isNullOrEmpty()) {
            return Result.failure(Throwable("Usuário não autenticado (deleteCard)"))
        }
        return runCatching {
            val cardRef = fireStoreDB.collection("cards").document(card.idCartao)
            deleteHints2(cardRef.id)
            cardRef.delete().await()
        }
    }

    private suspend fun deleteHints2(cardId : String){
        val hintsQuerySnapshot = fireStoreDB.collection("hints")
            .whereEqualTo("cardId", cardId)
            .get().await()
        for(hintDocument in hintsQuerySnapshot){
            hintDocument.reference.delete().await()
        }
    }


    suspend fun getHints2(card : Cartao) : Result<List<Dica>>{
        val currentUserEmail = fireBaseAuth.currentUser?.email
        if (currentUserEmail.isNullOrEmpty()) {
            return Result.failure(Throwable("Usuário não autenticado (deleteCard)"))
        }
        return runCatching {
            val hintList = mutableListOf<Dica>()

            val hintsQuerySnapshot = fireStoreDB.collection("hints")
                .whereEqualTo("cardId", card.idCartao)
                .get().await()

            for(hintDocument in hintsQuerySnapshot){
                val hintData = hintDocument.data
                val hintObject = Dica(
                    idDica = hintData["id"].toString(),
                    texto = hintData["text"].toString(),
                    cartao = card,
                )
                hintList.add(hintObject)
            }
            hintList
        }
    }


}