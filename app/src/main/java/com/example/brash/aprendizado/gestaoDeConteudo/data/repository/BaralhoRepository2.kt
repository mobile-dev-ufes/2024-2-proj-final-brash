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
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.time.ZoneId

class BaralhoRepository2 {

    private val fireStoreDB = FirebaseFirestore.getInstance()
    private val fireBaseAuth = FirebaseAuth.getInstance()

    suspend fun createDeck2(deck: Baralho) : Result<String>{
        val currentUserEmail = fireBaseAuth.currentUser?.email
        if (currentUserEmail.isNullOrEmpty()) {
            return Result.failure(Throwable("Usuário não autenticado"))
        }
        return runCatching {

            val decksRef = fireStoreDB.collection("decks")
            val newDeckRef = decksRef.add(hashMapOf<String, Any>()).await()
            val newDeckId = newDeckRef.id

            val newDeckInfo = hashMapOf(
                "id" to newDeckId,
                "folderId" to "root",
                "name" to deck.nome,
                "description" to deck.descricao,
                "public" to deck.publico,
                "numberNewCardsPerDay" to deck.cartoesNovosPorDia,
            )
            newDeckRef.set(newDeckInfo).await()
            newDeckId
        }
    }

    suspend fun updateDeck2(deck : Baralho, name : String, description : String, numberNewCardsPerDay : Int, public : Boolean): Result<Unit>{
        val currentUserEmail = fireBaseAuth.currentUser?.email
        if (currentUserEmail.isNullOrEmpty()) {
            return Result.failure(Throwable("Usuário não autenticado"))
        }
        return runCatching {
            val deckRef = fireStoreDB.collection("decks").document(deck.idBaralho)
            val newDeckInfo = mapOf(
                "name" to name,
                "description" to description,
                "numberNewCardsPerDay" to numberNewCardsPerDay,
                "public" to public
            )
            deckRef.update(newDeckInfo).await()
        }
    }

    suspend fun deleteDeck2(deck: Baralho): Result<Unit>{
        val currentUserEmail = fireBaseAuth.currentUser?.email
        if (currentUserEmail.isNullOrEmpty()) {
            return Result.failure(Throwable("Usuário não autenticado"))
        }
        return runCatching {
            val deckRef = fireStoreDB.collection("decks").document(deck.idBaralho)
            deleteCards2(deckRef.id)
            deckRef.delete().await()
        }
    }

    private suspend fun deleteCards2(deckId : String){
        val cardsQuerySnapshot = fireStoreDB.collection("cards")
            .whereEqualTo("deckId", deckId)
            .get().await()
        for(cardDocument in cardsQuerySnapshot){
            deleteHints2(cardDocument.id)
            cardDocument.reference.delete().await()
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


    private fun getDeckReference(deck: Baralho, folder: Pasta, currentUserEmail: String): DocumentReference {
        val userRef = fireStoreDB.collection("users").document(currentUserEmail)
        return if (folder.idPasta != "root") {
            userRef.collection("folders").document(folder.idPasta).collection("decks").document(deck.idBaralho)
        } else {
            userRef.collection("root").document(deck.idBaralho)
        }
    }


    suspend fun getCards2(deck : Baralho) : Result<List<Cartao>>{
        val currentUserEmail = fireBaseAuth.currentUser?.email
        if (currentUserEmail.isNullOrEmpty()) {
            return Result.failure(Throwable("Usuário não autenticado"))
        }
        return runCatching {
            val cardList = mutableListOf<Cartao>()

            val deckRef = fireStoreDB.collection("decks").document(deck.idBaralho)
            val cardsQuerySnapshot = fireStoreDB.collection("cards")
                .whereEqualTo("deckId", deckRef.id)
                .get().await()

            for(cardDocument in cardsQuerySnapshot){
                val cardData = cardDocument.data

                val reviewDateTimestamp = cardData["reviewDate"] as Timestamp
                val reviewDate = reviewDateTimestamp.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime()

                val cardObject = Cartao(
                    idCartao = cardData["id"].toString(),
                    pergunta = cardData["question"].toString(),
                    resposta = cardData["answer"].toString(),
                    fatorDeRevisao = cardData["reviewFactor"].toString().toDouble(),
                    intervaloRevisao = cardData["reviewInterval"].toString().toInt(),
                    dataDeRevisao = reviewDate,
                    baralho = deck,
                    categoriaDoAprendizado = CategoriaDoAprendizado.valueOf(cardData["categoryOfLearning"].toString())
                )

                val hintsList = getHints2(cardObject)
                cardObject.dica = hintsList.toMutableList()
                cardList.add(cardObject)
            }

            cardList
        }
    }

    private suspend fun getHints2(cardObject: Cartao) : List<Dica>{
        val hintsList = mutableListOf<Dica>()
        val hintsQuerySnapshot = fireStoreDB.collection("hints")
            .whereEqualTo("cardId", cardObject.idCartao)
            .get().await()
        for(hintDocument in hintsQuerySnapshot){
            val hintData = hintDocument.data
            val hintObject = Dica(
                idDica = hintData["id"].toString(),
                texto = hintData["text"].toString(),
                cartao = cardObject,
            )
            hintsList.add(hintObject)
        }
        return hintsList
    }

    suspend fun makePublic2(deck: Baralho): Result<Unit> {
        val currentUserEmail = fireBaseAuth.currentUser?.email
        if (currentUserEmail.isNullOrEmpty()) {
            return Result.failure(Throwable("Usuário não autenticado"))
        }
        return runCatching {

            if(deck.publico){ // se for público retorna
                return Result.success(Unit)
            }

            val publicDecksRef = fireStoreDB.collection("publicDecks")
            val deckRef = fireStoreDB.collection("decks").document(deck.idBaralho)

            val newPublicDeckRef = publicDecksRef.add(hashMapOf<String, Any>()).await()
            val publicDeckInfo = hashMapOf(
                "id" to newPublicDeckRef.id,
                "userId" to currentUserEmail,
                "deckId" to deckRef.id,
            )
            newPublicDeckRef.set(publicDeckInfo).await()
            val updateInfo = mapOf(
                "public" to true,
                "publicId" to newPublicDeckRef.id,
            )
            deckRef.update(updateInfo).await()
        }
    }

    suspend fun unmakePublic2(deck: Baralho) : Result<Unit>{
        val currentUserEmail = fireBaseAuth.currentUser?.email
        if (currentUserEmail.isNullOrEmpty()) {
            return Result.failure(Throwable("Usuário não autenticado"))
        }
        return runCatching {

            if(!deck.publico){ // se não for público retorna
                return Result.success(Unit)
            }

            val deckRef = fireStoreDB.collection("decks").document(deck.idBaralho)
            val deckSnapshot = deckRef.get().await()
            val deckData = deckSnapshot.data ?: return Result.failure(Throwable("Erro ao pegar dados do baralho (unmakePublic::BaralhoRepository2)"))

            val publicDeckId = deckData["publicId"].toString()
            val publicDeckRef = fireStoreDB.collection("publicDecks").document(publicDeckId)

            publicDeckRef.delete().await()
            val updateDeckInfo = mapOf(
                "publicId" to FieldValue.delete(),
                "public" to false
            )
            deckRef.update(updateDeckInfo).await()
        }
    }



    suspend fun getPublicDeck() : Result<Unit>{
        val currentUserEmail = fireBaseAuth.currentUser?.email
        if (currentUserEmail.isNullOrEmpty()) {
            return Result.failure(Throwable("Usuário não autenticado"))
        }
        return runCatching {

        }
    }
}