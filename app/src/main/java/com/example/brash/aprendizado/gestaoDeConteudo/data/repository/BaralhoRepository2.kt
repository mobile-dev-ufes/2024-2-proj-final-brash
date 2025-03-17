package com.example.brash.aprendizado.gestaoDeConteudo.data.repository

import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Anotacao
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Baralho
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.BaralhoPublico
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
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date

/**
 * Repository class responsible for handling operations related to Decks (Baralho) in Firestore.
 */
class BaralhoRepository2 {

    private val fireStoreDB = FirebaseFirestore.getInstance()
    private val fireBaseAuth = FirebaseAuth.getInstance()


    /**
     * Creates a new deck (Baralho) in Firestore.
     * @param deck The deck to be created.
     * @return Result containing the newly created deck ID or an error.
     */
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
                "folderId" to "root/$currentUserEmail",
                "name" to deck.nome,
                "description" to deck.descricao,
                "public" to deck.publico,
                "numberNewCardsPerDay" to deck.cartoesNovosPorDia,
            )
            newDeckRef.set(newDeckInfo).await()
            newDeckId
        }
    }

    /**
     * Updates an existing deck.
     * @param deck The deck to be updated.
     * @param name New name for the deck.
     * @param description New description.
     * @param numberNewCardsPerDay New number of cards per day.
     * @param public Whether the deck is public or not.
     * @return Result indicating success or failure.
     */
    suspend fun updateDeck2(deck : Baralho, name : String, description : String, numberNewCardsPerDay : Int, public : Boolean): Result<Unit>{
        val currentUserEmail = fireBaseAuth.currentUser?.email
        if (currentUserEmail.isNullOrEmpty()) {
            return Result.failure(Throwable("Usuário não autenticado"))
        }
        return runCatching {
            val deckRef = fireStoreDB.collection("decks").document(deck.idBaralho)

            if(deck.publico && !public){
                unmakePublic2(deck)
            }else if(!deck.publico && public){
                makePublic2(deck)
            }

            val newDeckInfo = mapOf(
                "name" to name,
                "description" to description,
                "numberNewCardsPerDay" to numberNewCardsPerDay,
                "public" to public
            )
            deckRef.update(newDeckInfo).await()
        }
    }

    /**
     * Deletes a deck and its associated data (cards, notes, hints).
     * @param deck The deck to be deleted.
     * @return Result indicating success or failure.
     */
    suspend fun deleteDeck2(deck: Baralho): Result<Unit>{
        val currentUserEmail = fireBaseAuth.currentUser?.email
        if (currentUserEmail.isNullOrEmpty()) {
            return Result.failure(Throwable("Usuário não autenticado"))
        }

        return runCatching {
            val deckRef = fireStoreDB.collection("decks").document(deck.idBaralho)
            val deckData = deckRef.get().await().data ?: return Result.failure(Exception("Erro pegando data do baralho (deleteDeck::BaralhoRepository2)"))

            val isPublic = deckData["public"].toString().toBoolean()
            if(isPublic){ // se ele for público então deleta o publicDeck
                val publicDeckId = deckData["publicId"].toString()
                val publicDeckRef = fireStoreDB.collection("publicDecks").document(publicDeckId)
                publicDeckRef.delete().await()
            }

            deleteCards2(deckRef.id)
            deleteNotes2(deckRef.id)
            deckRef.delete().await()
        }
    }

    /**
     * Deletes all cards associated with a given deck.
     * @param deckId The ID of the deck.
     */
    private suspend fun deleteCards2(deckId : String){
        val cardsQuerySnapshot = fireStoreDB.collection("cards")
            .whereEqualTo("deckId", deckId)
            .get().await()
        for(cardDocument in cardsQuerySnapshot){
            deleteHints2(cardDocument.id)
            cardDocument.reference.delete().await()
        }
    }

    /**
     * Deletes all hints associated with a given card.
     * @param cardId The ID of the card.
     */
    private suspend fun deleteHints2(cardId : String){
        val hintsQuerySnapshot = fireStoreDB.collection("hints")
            .whereEqualTo("cardId", cardId)
            .get().await()
        for(hintDocument in hintsQuerySnapshot){
            hintDocument.reference.delete().await()
        }
    }

    /**
     * Deletes all notes associated with a given deck.
     * @param deckId The ID of the deck.
     */
    private suspend fun deleteNotes2(deckId : String){
        val notesQuerySnapshot = fireStoreDB.collection("notes")
            .whereEqualTo("deckId", deckId)
            .get().await()
        for(noteDocument in notesQuerySnapshot){
            noteDocument.reference.delete().await()
        }
    }


    /**
     * Retrieves a list of notes associated with the given deck from Firestore.
     *
     * @param deck The deck for which notes should be retrieved.
     * @return A Result containing a list of notes or an error if the user is not authenticated.
     */
    suspend fun getNotes2(deck : Baralho) : Result<List<Anotacao>>{
        val currentUserEmail = fireBaseAuth.currentUser?.email
        if (currentUserEmail.isNullOrEmpty()) {
            return Result.failure(Throwable("Usuário não autenticado"))
        }

        return runCatching {
            val noteList = mutableListOf<Anotacao>()
            val deckId = deck.idBaralho

            val notesQuerySnapshot = fireStoreDB.collection("notes")
                .whereEqualTo("deckId", deckId)
                .get().await()

            for(noteDocument in notesQuerySnapshot){
                val noteData = noteDocument.data
                val noteObject = Anotacao(
                    idAnotacao = noteData["id"].toString(),
                    nome = noteData["name"].toString(),
                    texto = noteData["text"].toString(),
                    baralho = deck
                )
                noteList.add(noteObject)
            }
            noteList
        }
    }

    /**
     * Retrieves a list of cards associated with the given deck from Firestore.
     *
     * @param deck The deck for which cards should be retrieved.
     * @return A Result containing a list of cards or an error if the user is not authenticated.
     */
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

    /**
     * Retrieves a list of hints associated with the given card from Firestore.
     *
     * @param cardObject The card for which hints should be retrieved.
     * @return A list of hints associated with the specified card.
     */
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

    /**
     * Makes the given deck public by adding it to the publicDecks collection.
     *
     * @param deck The deck to be made public.
     * @throws Exception If the user is not authenticated or if there is an error retrieving deck data.
     */
    private suspend fun makePublic2(deck: Baralho){
        val currentUserEmail = fireBaseAuth.currentUser?.email
        if (currentUserEmail.isNullOrEmpty()) {
            throw Exception("Usuário não autenticado")
        }

        val deckRef = fireStoreDB.collection("decks").document(deck.idBaralho)
        val deckData = deckRef.get().await().data ?: throw Exception("Erro ao pegar dados do baralho (makepublic::BaralhoRepository2)")

        val isPublic = deckData["public"].toString().toBoolean()
        if(isPublic){
            return
        }

        val publicDecksRef = fireStoreDB.collection("publicDecks")

        val newPublicDeckRef = publicDecksRef.add(hashMapOf<String, Any>()).await()
        val publicDeckInfo = hashMapOf(
            "id" to newPublicDeckRef.id,
            "userId" to currentUserEmail,
            "deckId" to deckRef.id,
        )
        newPublicDeckRef.set(publicDeckInfo).await()
        val deckUpdateInfo = mapOf(
            "public" to true,
            "publicId" to newPublicDeckRef.id,
        )
        deckRef.update(deckUpdateInfo).await()

    }

    /**
     * Removes the given deck from the publicDecks collection, making it private.
     *
     * @param deck The deck to be made private.
     * @throws Exception If the user is not authenticated or if there is an error retrieving deck data.
     */
    private suspend fun unmakePublic2(deck: Baralho){
        val currentUserEmail = fireBaseAuth.currentUser?.email
        if (currentUserEmail.isNullOrEmpty()) {
            throw Exception("Usuário não autenticado")
        }

        val deckRef = fireStoreDB.collection("decks").document(deck.idBaralho)
        val deckData = deckRef.get().await().data ?: throw Exception("Erro ao pegar dados do baralho (makepublic::BaralhoRepository2)")

        val isPublic = deckData["public"].toString().toBoolean()
        if(!isPublic){ // se não for público retorna
            return
        }

        val publicDeckId = deckData["publicId"].toString()

        val publicDeckRef = fireStoreDB.collection("publicDecks").document(publicDeckId)
        publicDeckRef.delete().await()

        val deckUpdateInfo = mapOf(
            "publicId" to FieldValue.delete(),
            "public" to false
        )

        deckRef.update(deckUpdateInfo).await()
    }

    /**
     * Retrieves a list of all public decks.
     *
     * @return A Result containing a list of public decks or an error if the user is not authenticated.
     */
    suspend fun getPublicDecks(): Result<List<BaralhoPublico>> {
        val currentUserEmail = fireBaseAuth.currentUser?.email
            ?: return Result.failure(Throwable("Usuário não autenticado"))

        return runCatching {
            val publicDecksSnapshot = fireStoreDB.collection("publicDecks").get().await()

            coroutineScope {
                val publicDeckList = publicDecksSnapshot.map { publicDeckDocument ->
                    val publicDeckData = publicDeckDocument.data

                    val publicDeckId = publicDeckData["id"].toString()
                    val userId = publicDeckData["userId"].toString()
                    val deckId = publicDeckData["deckId"].toString()

                    val deckDataDeferred = async { getDeckData(deckId) }
                    val userDataDeferred = async { getUserData(userId) }
                    val numberCardsDeferred = async { getNumberOfCards(deckId) }

                    var (deckData, userData, numberCards) = awaitAll(deckDataDeferred, userDataDeferred, numberCardsDeferred)

                    deckData = deckData as Map<*, *>
                    userData = userData as Map<*, *>
                    numberCards = numberCards as Int

                    BaralhoPublico(
                        idBaralhoPublico = publicDeckId,
                        idBaralho = deckId,
                        idUsuario = userId,
                        numeroCartoesBaralho = numberCards,
                        nomeBaralho = deckData["name"].toString(),
                        descricaoBaralho = deckData["description"].toString(),
                        nomeDeUsuario = userData["userName"].toString(),
                        nomeDeExibicaoUsuario = userData["exhibitionName"].toString()
                    )
                }

                publicDeckList
            }
        }
    }

    /**
     * Fetches the data of a deck from the Firestore database.
     *
     * @param deckId The unique identifier of the deck.
     * @return A map containing the deck data retrieved from the database.
     * @throws Exception If there is an error retrieving the deck data.
     */
    private suspend fun getDeckData(deckId: String): Map<String, Any> {
        val deckRef = fireStoreDB.collection("decks").document(deckId).get().await()
        return deckRef.data ?: throw Exception("Erro ao pegar dado do baralho")
    }

    /**
     * Fetches the data of a user from the Firestore database.
     *
     * @param userId The unique identifier of the user.
     * @return A map containing the user data retrieved from the database.
     * @throws Exception If there is an error retrieving the user data.
     */
    private suspend fun getUserData(userId: String): Map<String, Any> {
        val userRef = fireStoreDB.collection("users").document(userId).get().await()
        return userRef.data ?: throw Exception("Erro ao pegar dado do usuário")
    }

    /**
     * Retrieves the number of cards in a specific deck from the Firestore database.
     *
     * @param deckId The unique identifier of the deck.
     * @return The number of cards present in the deck.
     */
    private suspend fun getNumberOfCards(deckId: String): Int {
        return fireStoreDB.collection("cards")
            .whereEqualTo("deckId", deckId)
            .get().await().size()
    }

    /**
     * Copies a public deck to the user's personal deck.
     *
     * This function creates a new deck in the user's folder, copying the necessary data
     * from the original public deck and associated cards and notes.
     *
     * @param publicDeck The public deck that needs to be copied.
     * @param newDeckName The name of the new deck to be created.
     * @return A Result containing either the success or failure of the operation.
     * @throws Throwable If the user is not authenticated or any error occurs during the copy process.
     */
    suspend fun copyToUserPublicDeck(publicDeck: BaralhoPublico, newDeckName: String): Result<Unit> {
        val currentUserEmail = fireBaseAuth.currentUser?.email
            ?: return Result.failure(Throwable("Usuário não autenticado"))

        return runCatching {
            val deckSnapshot = fireStoreDB.collection("decks").document(publicDeck.idBaralho).get().await()
            val deckData = deckSnapshot.data ?: return Result.failure(Exception("Erro ao pegar dados do baralho"))

            val deckId = publicDeck.idBaralho

            val newDeckRef = fireStoreDB.collection("decks").add(hashMapOf<String, Any>()).await()
            val newDeckId = newDeckRef.id

            val newDeckInfo = mapOf(
                "id" to newDeckId,
                "folderId" to "root/$currentUserEmail",
                "description" to deckData["description"].toString(),
                "name" to newDeckName,
                "numberNewCardsPerDay" to 20,
                "public" to false,
            )
            newDeckRef.set(newDeckInfo).await()

            coroutineScope {
                val copyNotes = async { copyNotesFromDeck(newDeckId, deckId) }
                val copyCards = async { copyCardsFromDeck(newDeckId, deckId) }
                copyNotes.await()
                copyCards.await()
            }
        }
    }

    /**
     * Copies all notes from one deck to a new deck.
     *
     * This function retrieves all notes associated with a given deck (identified by `deckId`)
     * and copies them to a new deck (identified by `newDeckId`). It uses Firestore's batch
     * operations to ensure that all note insertions are handled efficiently in a single commit.
     *
     * @param newDeckId The ID of the new deck to which the notes should be copied.
     * @param deckId The ID of the original deck from which the notes are copied.
     */
    private suspend fun copyNotesFromDeck(newDeckId: String, deckId: String) {
        val notesSnapshot = fireStoreDB.collection("notes")
            .whereEqualTo("deckId", deckId)
            .get().await()

        val batch = fireStoreDB.batch()

        for (note in notesSnapshot) {
            val newNoteRef = fireStoreDB.collection("notes").add(hashMapOf<String, Any>()).await()
            val newNoteInfo = mapOf(
                "id" to newNoteRef.id,
                "deckId" to newDeckId,
                "name" to note.getString("name"),
                "text" to note.getString("text"),
            )
            batch.set(newNoteRef, newNoteInfo)
        }

        batch.commit().await()
    }

    /**
     * Copies all cards from one deck to a new deck.
     *
     * This function retrieves all cards associated with a given deck (identified by `deckId`)
     * and copies them to a new deck (identified by `newDeckId`). It also copies any associated
     * hints for each card. Firestore batch operations are used to optimize the process of adding
     * cards and hints. This function launches a coroutine for copying hints and commits the batch
     * of card operations once all cards are processed.
     *
     * @param newDeckId The ID of the new deck to which the cards should be copied.
     * @param deckId The ID of the original deck from which the cards are copied.
     */
    private suspend fun copyCardsFromDeck(newDeckId: String, deckId: String) = coroutineScope {
        val cardsSnapshot = fireStoreDB.collection("cards")
            .whereEqualTo("deckId", deckId)
            .get().await()

        val batch = fireStoreDB.batch()

        val copyHintsJobs = mutableListOf<Deferred<Unit>>()

        for (card in cardsSnapshot) {
            val newCardRef = fireStoreDB.collection("cards").add(hashMapOf<String, Any>()).await()
            val newCardId = newCardRef.id

            val newCardInfo = mapOf(
                "id" to newCardId,
                "deckId" to newDeckId,
                "answer" to card.getString("answer"),
                "categoryOfLearning" to CategoriaDoAprendizado.NOVO.name,
                "question" to card.getString("question"),
                "reviewDate" to Timestamp(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant())),
                "reviewFactor" to 2.5,
                "reviewInterval" to 0,
            )
            batch.set(newCardRef, newCardInfo)

            copyHintsJobs.add(async { copyHintsFromCard(newCardId, card.id) })
        }

        batch.commit().await()

        copyHintsJobs.awaitAll()
    }

    /**
     * Copies all hints from one card to a new card.
     *
     * This function retrieves all hints associated with a given card (identified by `cardId`)
     * and copies them to a new card (identified by `newCardId`). The Firestore batch operation
     * is used to efficiently save all hints in a single commit.
     *
     * @param newCardId The ID of the new card to which the hints should be copied.
     * @param cardId The ID of the original card from which the hints are copied.
     */
    private suspend fun copyHintsFromCard(newCardId: String, cardId: String) {
        val hintsSnapshot = fireStoreDB.collection("hints")
            .whereEqualTo("cardId", cardId)
            .get().await()

        val batch = fireStoreDB.batch()

        for (hint in hintsSnapshot) {
            val newHintRef = fireStoreDB.collection("hints").add(hashMapOf<String, Any>()).await()
            val newHintInfo = mapOf(
                "id" to newHintRef.id,
                "cardId" to newCardId,
                "text" to hint.getString("text"),
            )
            batch.set(newHintRef, newHintInfo)
        }

        batch.commit().await()
    }


}