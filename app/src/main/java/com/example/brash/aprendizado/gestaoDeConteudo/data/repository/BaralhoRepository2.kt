package com.example.brash.aprendizado.gestaoDeConteudo.data.repository

import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Anotacao
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

    suspend fun makePublic2(deck: Baralho): Result<Unit> {
        val currentUserEmail = fireBaseAuth.currentUser?.email
        if (currentUserEmail.isNullOrEmpty()) {
            return Result.failure(Throwable("Usuário não autenticado"))
        }
        return runCatching {

            val deckRef = fireStoreDB.collection("decks").document(deck.idBaralho)
            val deckData = deckRef.get().await().data ?: return Result.failure(Exception("Erro ao pegar dados do baralho (makepublic::BaralhoRepository2)"))

            val isPublic = deckData["public"].toString().toBoolean()
            if(isPublic){ // se for público retorna
                return Result.success(Unit)
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
    }

    suspend fun unmakePublic2(deck: Baralho) : Result<Unit>{
        val currentUserEmail = fireBaseAuth.currentUser?.email
        if (currentUserEmail.isNullOrEmpty()) {
            return Result.failure(Throwable("Usuário não autenticado"))
        }
        return runCatching {

            val deckRef = fireStoreDB.collection("decks").document(deck.idBaralho)
            val deckData = deckRef.get().await().data ?: return Result.failure(Exception("Erro ao pegar dados do baralho (makepublic::BaralhoRepository2)"))

            val isPublic = deckData["public"].toString().toBoolean()
            if(isPublic){ // se não for público retorna
                return Result.success(Unit)
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
    }


}