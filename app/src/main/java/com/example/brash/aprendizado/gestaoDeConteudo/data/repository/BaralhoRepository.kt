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
import com.google.firebase.firestore.FieldValue
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
            val deckData = deckRef.get().await().data ?: return Result.failure(Exception("Erro pegando data do baralho (deleteDeck::BaralhoRepository)"))

            val isPublic = deckData["public"].toString().toBoolean()

            if(isPublic){ // deletar entrada pública do baralho
                val publicId = deckData["publicId"].toString()
                val publicDeckRef = fireStoreDB.collection("publicDecks").document(publicId)
                publicDeckRef.delete().await()
            }

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

    // testar
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

                val hintsList = getHints(document.reference, cardObject)
                cardObject.dica = hintsList.toMutableList()
                cardsList.add(cardObject)
            }
            return Result.success(cardsList)
        }
        catch (e : Exception){
            return Result.failure(e)
        }
    }

    // testar
    private suspend fun getHints(cardRef : DocumentReference, cardObject : Cartao) : List<Dica>{
        val hintsList = mutableListOf<Dica>()
        val hintsRefSnapshot = cardRef.collection("hints").get().await()
        for(hintDocument in hintsRefSnapshot){
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

    // testar
    suspend fun makePublic(deck: Baralho): Result<Unit> {
        val currentUserEmail = fireBaseAuth.currentUser?.email
        if (currentUserEmail.isNullOrEmpty()) {
            return Result.failure(Throwable("Usuário não autenticado"))
        }
        return runCatching {
            val folder = deck.pasta ?: return Result.failure(Throwable("Pasta do deck não encontrada"))
            val deckRef = getDeckReference(deck, folder, currentUserEmail) // Obtém referência ao deck
            val cardsRef = deckRef.collection("cards")

            val publicDecksRef = fireStoreDB.collection("publicDecks")

            //val numberCards = cardsRef.get().await().size()
            val deckSnapshot = deckRef.get().await()
            //val deckData = deckSnapshot.data ?: return Result.failure(Throwable("Erro ao pegar dados do baralho"))

            val publicDeckRef = publicDecksRef.add(hashMapOf<String, Any>()).await()
            val publicDeckInfo = hashMapOf(
                "id" to publicDeckRef.id,
                "userId" to currentUserEmail,
                "deckPath" to deckRef.path,
            )
            publicDeckRef.set(publicDeckInfo).await()
            deckRef.update(mapOf(
                "public" to true,
                "publicId" to publicDeckRef.id,
            ))
        }
    }

    // testar
    suspend fun unmakePublic(deck: Baralho): Result<Unit> {
        val currentUserEmail = fireBaseAuth.currentUser?.email
        if (currentUserEmail.isNullOrEmpty()) {
            return Result.failure(Throwable("Usuário não autenticado"))
        }
        return runCatching {
            val folder = deck.pasta ?: return Result.failure(Throwable("Pasta do deck não encontrada"))
            val deckRef = getDeckReference(deck, folder, currentUserEmail) // Obtém referência ao deck

            val deckSnapshot = deckRef.get().await()
            val deckData = deckSnapshot.data ?: return Result.failure(Throwable("Erro ao pegar dados do baralho"))

            val isPublic = deckData["public"].toString().toBoolean()
            if(isPublic){
                val publicDecksRef = fireStoreDB.collection("publicDecks")
                val publicDeckId = deckData["publicId"].toString()

                val publicDeckRef = publicDecksRef.whereEqualTo("id", publicDeckId).limit(1).get().await()
                for (document in publicDeckRef) {
                    document.reference.delete().await()
                }

                deckRef.update(
                    mapOf(
                        "publicId" to FieldValue.delete(),
                        "public" to false
                    )
                ).await()
            }
        }
    }

}