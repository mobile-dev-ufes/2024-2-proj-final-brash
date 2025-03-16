package com.example.brash.aprendizado.gestaoDeConteudo.data.repository

import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Baralho
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Cartao
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.CategoriaDoAprendizado
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Dica
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Pasta
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date

class CartaoRepository {

    private val fireStoreDB = FirebaseFirestore.getInstance()
    private val fireBaseAuth = FirebaseAuth.getInstance()

    suspend fun createCard(deck: Baralho, card: Cartao): Result<Unit> {
        val currentUserEmail = fireBaseAuth.currentUser?.email
        if (currentUserEmail.isNullOrEmpty()) {
            return Result.failure(Throwable("Usuário não autenticado"))
        }
        val folder = deck.pasta ?: return Result.failure(Throwable("Pasta do deck não encontrada (createCard)"))

        return try {
            val deckRef = getDeckReference(deck, folder, currentUserEmail)
            val cardsRef = deckRef.collection("cards")

            val cardRef = cardsRef.add(hashMapOf<String, Any>()).await()
            val generatedId = cardRef.id

            val newCard = hashMapOf(
                "id" to generatedId,
                "question" to card.pergunta,
                "answer" to card.resposta,
                "reviewFactor" to card.fatorDeRevisao,
                "reviewInterval" to card.intervaloRevisao,
                "reviewDate" to Timestamp(Date.from(card.dataDeRevisao.atZone(ZoneId.systemDefault()).toInstant())),
                "categoryOfLearning" to card.categoriaDoAprendizado.name
            )

            cardRef.set(newCard).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
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

    private fun getCardRef(folder: Pasta, deck: Baralho, card: Cartao, currentUserEmail: String): DocumentReference {
        val deckRef = getDeckReference(deck, folder, currentUserEmail)
        return deckRef.collection("cards").document(card.idCartao)
    }

    // testar
    suspend fun updateCardFromReview(card : Cartao, reviewFactor : Double, reviewInterval : Int, reviewDate: LocalDateTime, categoryOfLearning : CategoriaDoAprendizado) : Result<Unit>{
        val currentUserEmail = fireBaseAuth.currentUser?.email
        if (currentUserEmail.isNullOrEmpty()) {
            return Result.failure(Throwable("Usuário não autenticado (deleteCard)"))
        }
        return runCatching {
            val deck = card.baralho
            val folder = deck.pasta ?: return Result.failure(Throwable("Pasta do card não encontrada (updateCardFromReview)"))

            val cardRef = getCardRef(folder, deck, card, currentUserEmail)

            val updateInfo = mapOf(
                "reviewFactor" to reviewFactor,
                "reviewInterval" to reviewInterval,
                "reviewDate" to Timestamp(Date.from(reviewDate.atZone(ZoneId.systemDefault()).toInstant())),
                "categoryOfLearning" to categoryOfLearning.name,
            )
            cardRef.update(updateInfo).await()
        }
    }

    // testar
    suspend fun updateCardQA(card : Cartao, question : String, answer : String) : Result<Unit>{
        val currentUserEmail = fireBaseAuth.currentUser?.email
        if (currentUserEmail.isNullOrEmpty()) {
            return Result.failure(Throwable("Usuário não autenticado (deleteCard)"))
        }
        return runCatching {
            val deck = card.baralho
            val folder = deck.pasta ?: return Result.failure(Throwable("Pasta do card não encontrada (updateCardQA)"))

            val cardRef = getCardRef(folder, deck, card, currentUserEmail)

            val updateInfo = mapOf(
                "question" to question,
                "answer" to answer,
            )
            cardRef.update(updateInfo).await()
        }
    }

    suspend fun deleteCard(card: Cartao): Result<Unit> {
        val currentUserEmail = fireBaseAuth.currentUser?.email
        if (currentUserEmail.isNullOrEmpty()) {
            return Result.failure(Throwable("Usuário não autenticado (deleteCard)"))
        }

        return try {
            val deck = card.baralho
            val folder = deck.pasta ?: return Result.failure(Throwable("Pasta do deck não encontrada (deleteCard)"))

            val cardRef = getCardRef(folder, deck, card, currentUserEmail)
            val hintsRef = cardRef.collection("hints")

            deleteHints(hintsRef)
            cardRef.delete().await()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private suspend fun deleteHints(hintsRef: CollectionReference) {
        val hintsSnapshot = hintsRef.get().await()
        for (hint in hintsSnapshot) {
            hint.reference.delete().await()
        }
    }

    // testar
    suspend fun getHints(card : Cartao) : Result<List<Dica>>{
        val currentUserEmail = fireBaseAuth.currentUser?.email
        if (currentUserEmail.isNullOrEmpty()) {
            return Result.failure(Throwable("Usuário não autenticado (deleteCard)"))
        }
        return runCatching {

            val deck = card.baralho
            val folder = deck.pasta ?: return Result.failure(Throwable("Pasta do deck não encontrada (getHints::CartaoRepository)"))
            val cardRef = getCardRef(folder, deck, card, currentUserEmail)
            val hintsRefSnapshot = cardRef.collection("hints").get().await()

            val hintsList = mutableListOf<Dica>()
            for(hintDocument in hintsRefSnapshot){
                val hintData = hintDocument.data
                val hintObject = Dica(
                    idDica = hintData["id"].toString(),
                    texto = hintData["text"].toString(),
                    cartao = card,
                )
                hintsList.add(hintObject)
            }
            hintsList
        }
    }


}