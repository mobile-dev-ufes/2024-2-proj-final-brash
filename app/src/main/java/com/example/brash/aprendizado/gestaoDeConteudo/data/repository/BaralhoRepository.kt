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
import com.google.firebase.firestore.FirebaseFirestore
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date

class BaralhoRepository {

    private val fireStoreDB = FirebaseFirestore.getInstance()
    private val fireBaseAuth = FirebaseAuth.getInstance()


    fun createDeck(deck : Baralho, onSuccess: () -> Unit, onFailure : () -> Unit){

        val currentUserEmail = fireBaseAuth.currentUser?.email
        if (currentUserEmail.isNullOrEmpty()) {
            onFailure()
            return
        }

        val rootRef = fireStoreDB.collection("users")
            .document(currentUserEmail)
            .collection("root")

        val deckRef = rootRef.add(hashMapOf<String, Any>())
        deckRef
            .addOnSuccessListener { document ->
                val generatedId = document.id

                val newDeck = hashMapOf(
                    "id" to generatedId,
                    "name" to deck.nome,
                    "description" to deck.descricao,
                    "public" to deck.publico,
                    "numberNewCardsPerDay" to deck.cartoesNovosPorDia,
                )
                document.set(newDeck)
                    .addOnSuccessListener {
                        onSuccess()
                    }
                    .addOnFailureListener {
                        onFailure()
                    }
            }
            .addOnFailureListener {
                onFailure()
            }
        return
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