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

    private suspend fun deleteNotes2(deckId : String){
        val notesQuerySnapshot = fireStoreDB.collection("notes")
            .whereEqualTo("deckId", deckId)
            .get().await()
        for(noteDocument in notesQuerySnapshot){
            noteDocument.reference.delete().await()
        }
    }

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


    suspend fun getPublicDecks() : Result<List<BaralhoPublico>>{
        val currentUserEmail = fireBaseAuth.currentUser?.email
            ?: return Result.failure(Throwable("Usuário não autenticado"))

        return runCatching {
            val publicDeckList = mutableListOf<BaralhoPublico>()

            val publicCardsSnapshot = fireStoreDB.collection("publicDecks").get().await()
            for(publicCardDocument in publicCardsSnapshot){
                val publicCardData = publicCardDocument.data

                val publicDeckId = publicCardData["id"].toString()
                val userId = publicCardData["userId"].toString()
                val deckId = publicCardData["deckId"].toString()

                publicDeckList.add(getPublicDeck(publicDeckId, deckId, userId))
            }
            publicDeckList
        }
    }


    private suspend fun getPublicDeck(publicDeckId : String, deckId : String, userId : String) : BaralhoPublico{
        val numberCards = fireStoreDB.collection("cards")
            .whereEqualTo("deckId", deckId)
            .get().await().size()

        val deckRef = fireStoreDB.collection("decks").document(deckId).get().await()
        val deckData = deckRef.data ?: throw(Exception("Erro ao pegar dado do baralho (<private>getPublicDeck::BaralhoRepository2)"))

        val deckName = deckData["name"].toString()
        val deckDescription = deckData["description"].toString()

        val userRef = fireStoreDB.collection("users").document(userId).get().await()
        val userData = userRef.data ?: throw(Exception("Erro ao pegar dado do usuário (<private>getPublicDeck::BaralhoRepository2)"))

        val userName = userData["userName"].toString()
        val exhibitionName = userData["exhibitionName"].toString()

        return BaralhoPublico(
            idBaralhoPublico = publicDeckId,
            idBaralho = deckId,
            idUsuario = userId,
            numeroCartoesBaralho = numberCards,
            nomeBaralho = deckName,
            descricaoBaralho = deckDescription,
            nomeDeUsuario = userName,
            nomeDeExibicaoUsuario = exhibitionName,
        )
    }


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