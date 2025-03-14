package com.example.brash.aprendizado.gestaoDeConteudo.data.repository

import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Baralho
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Pasta
import com.example.brash.nucleo.domain.model.Usuario
import com.example.brash.nucleo.utils.UtilsFoos
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

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
                val generatedId = document.id // 🔹 Pegamos o ID gerado automaticamente

                val newDeck = hashMapOf(
                    "id" to generatedId,
                    "name" to deck.nome,
                    "description" to deck.descricao,
                    "public" to deck.publico,
                    "numberNewCardsPerDay" to deck.cartoesNovosPorDia
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
}