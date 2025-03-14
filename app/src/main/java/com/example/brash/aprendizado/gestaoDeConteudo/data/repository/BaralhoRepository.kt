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

        val newDeck = hashMapOf(
            "name" to deck.nome,
            "description" to deck.descricao,
            "public" to deck.publico,
            "numberNewCardsPerDay" to deck.cartoesNovosPorDia
        )

        val rootCollectionRef = fireStoreDB.collection("users")
            .document(currentUserEmail)
            .collection("root")

        rootCollectionRef.document(deck.nome).set(newDeck)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onFailure()
            }

        return

    }

    private fun createRoot( onSuccess : () -> Unit, onFailure: () -> Unit){

        val currentUserEmail = fireBaseAuth.currentUser?.email
        if (currentUserEmail == null) {
            onFailure()
            return
        }

        val foldersRef = fireStoreDB.collection("users").document(currentUserEmail)
            .collection("folders")
            .document("root")

        foldersRef.get()
            .addOnSuccessListener { document ->
                if (!document.exists()) {
                    // Se o documento "root" não existir, cria ele
                    val rootFolder = hashMapOf("name" to "root")
                    foldersRef.set(rootFolder)
                        .addOnFailureListener {
                            onFailure()
                        }.addOnSuccessListener {
                            onSuccess()
                        }
                }else{
                    onSuccess()
                }
            }
            .addOnFailureListener {
                onFailure()
            }
    }


}