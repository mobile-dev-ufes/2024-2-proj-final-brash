package com.example.brash.aprendizado.gestaoDeConteudo.data.repository

import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Baralho
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Cartao
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.CategoriaDoAprendizado
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Dica
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Pasta
import com.example.brash.nucleo.domain.model.Usuario
import com.example.brash.nucleo.utils.UtilsFoos
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date

class BaralhoRepository {

    private val fireStoreDB = FirebaseFirestore.getInstance()
    private val fireBaseAuth = FirebaseAuth.getInstance()

    suspend fun createDeck(deck: Baralho): Result<String> {
        val currentUserEmail = fireBaseAuth.currentUser?.email
        if (currentUserEmail.isNullOrEmpty()) {
            return Result.failure(Throwable("Usuário não autenticado"))
        }

        return try {
            val rootRef = fireStoreDB.collection("users")
                .document(currentUserEmail)
                .collection("root")

            val documentRef = rootRef.add(hashMapOf<String, Any>()).await()
            val generatedId = documentRef.id

            val newDeck = hashMapOf(
                "id" to generatedId,
                "name" to deck.nome,
                "description" to deck.descricao,
                "public" to deck.publico,
                "numberNewCardsPerDay" to deck.cartoesNovosPorDia
            )

            documentRef.set(newDeck).await()

            Result.success(generatedId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    suspend fun updateDeck(deck : Baralho, name : String, description : String, numberNewCardsPerDay : Int, public : Boolean): Result<Unit>{
        val currentUserEmail = fireBaseAuth.currentUser?.email
        if (currentUserEmail.isNullOrEmpty()) {
            return Result.failure(Throwable("Usuário não autenticado"))
        }
        try {
            val folder = deck.pasta!!
            val userRef = fireStoreDB.collection("users").document(currentUserEmail)

            val decksRef = if(folder.idPasta != "root"){
                userRef.collection("folders").document(folder.idPasta).collection("decks")
            }
            else{
                userRef.collection("root")
            }
            val deckRef = decksRef.document(deck.idBaralho)
            val newInfo = mapOf(
                "name" to name,
                "description" to description,
                "numberNewCardsPerDay" to numberNewCardsPerDay,
                "public" to public
            )
            deckRef.update(newInfo).await()
            return Result.success(Unit)
        }
        catch (e : Exception){
            return Result.failure(e)
        }
    }

    suspend fun deleteDeck(deck: Baralho): Result<Unit> {
        val currentUserEmail = fireBaseAuth.currentUser?.email
        if (currentUserEmail.isNullOrEmpty()) {
            return Result.failure(Throwable("Usuário não autenticado"))
        }

        return try {

            val folder = deck.pasta ?: return Result.failure(Throwable("Pasta do deck não encontrada (deleteDeck)"))
            val userRef = fireStoreDB.collection("users")
                .document(currentUserEmail)

            val decksRef = if (folder.idPasta != "root") {
                userRef.collection("folders").document(folder.idPasta).collection("decks")
            } else {
                userRef.collection("root")
            }
            val deckRef = decksRef.document(deck.idBaralho)

            deleteCards(deckRef.collection("cards"))
            deckRef.delete().await()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private suspend fun deleteCards(cardsRef: CollectionReference) {
        val cardsSnapshot = cardsRef.get().await()
        for (card in cardsSnapshot) {
            val hintsRef = card.reference.collection("hints")
            deleteHints(hintsRef)
            card.reference.delete().await()
        }
    }

    private suspend fun deleteHints(hintsRef: CollectionReference) {
        val hintsSnapshot = hintsRef.get().await()
        for (hint in hintsSnapshot) {
            hint.reference.delete().await()
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

    suspend fun getCards(deck : Baralho) : Result<List<Cartao>>{

        val currentUserEmail = fireBaseAuth.currentUser?.email
        if (currentUserEmail.isNullOrEmpty()) {
            return Result.failure(Throwable("Usuário não autenticado"))
        }
        return try {
            val folder = deck.pasta ?: return Result.failure(Throwable("Pasta do deck não encontrada (getCards)"))
            val deckRef = getDeckReference(deck, folder, currentUserEmail)
            val cardsRef = deckRef.collection("cards")

            val cardsSnapshot = cardsRef.get().await()
            val cardsList = mutableListOf<Cartao>()
            for (document in cardsSnapshot){
                val cardData = document.data

                val reviewDateTimestamp = cardData["reviewDate"] as Timestamp
                val reviewDate = reviewDateTimestamp.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime()

                val _card = Cartao(
                    idCartao = cardData["id"].toString(),
                    pergunta = cardData["question"].toString(),
                    resposta = cardData["answer"].toString(),
                    fatorDeRevisao = cardData["reviewFactor"].toString().toDouble(),
                    intervaloRevisao = cardData["reviewInterval"].toString().toInt(),
                    dataDeRevisao = reviewDate,
                    baralho = deck,
                    categoriaDoAprendizado = CategoriaDoAprendizado.valueOf(cardData["categoryOfLearning"].toString())
                )
                cardsList.add(_card)
            }
            return Result.success(cardsList)
        }
        catch (e : Exception){
            return Result.failure(e)
        }
    }


    fun addCard(deck : Baralho, card : Cartao){

        val currentUserEmail = fireBaseAuth.currentUser?.email
        if (currentUserEmail.isNullOrEmpty()) {
            return
        }

        val userRef = fireStoreDB.collection("users")
            .document(currentUserEmail)

        //var decksRef = userRef.collection("root")
        //val folder = deck.pasta!!
        //if(folder.nome != "root"){
            //decksRef = userRef.collection("folders").document(folder.idPasta).collection("decks")
        //}

        val decksRef = userRef.collection("root")
        val cardsRef = decksRef.document("Cmpzp5ySYkWqoSKzzTNq").collection("cards")
        val cardRef = cardsRef.add(hashMapOf<String, Any>())
        cardRef
            .addOnSuccessListener { document ->
                val generatedId = document.id

                val newCard = hashMapOf(
                    "id" to generatedId,
                    "question" to card.pergunta,
                    "answer" to card.resposta,
                    "reviewFactor" to card.fatorDeRevisao,
                    "reviewInterval" to card.intervaloRevisao,
                    "reviewDate" to Timestamp(Date.from(card.dataDeRevisao.atZone(ZoneId.systemDefault()).toInstant())),
                    "categoryOfLearning" to card.categoriaDoAprendizado.name
                )
                document.set(newCard)
                    .addOnSuccessListener {

                    }
                    .addOnFailureListener {

                    }
            }
            .addOnFailureListener {

            }

        return
    }

}