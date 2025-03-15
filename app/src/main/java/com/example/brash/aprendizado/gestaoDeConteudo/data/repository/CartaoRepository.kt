package com.example.brash.aprendizado.gestaoDeConteudo.data.repository

import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Baralho
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Cartao
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Pasta
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
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

        val folder = deck.pasta ?: return Result.failure(Throwable("Pasta do deck não encontrada"))

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

    private fun getDeckReference(deck: Baralho, folder : Pasta, currentUserEmail: String): DocumentReference {
        val userRef = fireStoreDB.collection("users").document(currentUserEmail)
        return if (folder.idPasta != "root") {
            userRef.collection("folders").document(folder.idPasta).collection("decks").document(deck.idBaralho)
        } else {
            userRef.collection("root").document(deck.idBaralho)
        }
    }

    suspend fun deleteCard(card : Cartao) : Result<Unit>{
        return try{
            return Result.success(Unit)
        }
        catch (e : Exception){
            return Result.failure(e)
        }

    }


}