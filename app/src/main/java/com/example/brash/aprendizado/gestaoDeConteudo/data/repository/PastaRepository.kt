package com.example.brash.aprendizado.gestaoDeConteudo.data.repository

import android.util.Log
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Baralho
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Pasta
import com.example.brash.nucleo.domain.model.Usuario
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot

class PastaRepository {

    private val fireStoreDB = FirebaseFirestore.getInstance()
    private val fireBaseAuth = FirebaseAuth.getInstance()

    fun createFolder(folder : Pasta, onSuccess: () -> Unit, onFailure : () -> Unit){

        val currentUserEmail = fireBaseAuth.currentUser?.email
        if (currentUserEmail.isNullOrEmpty()) {
            onFailure()
            return
        }

        val foldersRef = fireStoreDB.collection("users")
            .document(currentUserEmail)
            .collection("folders")

        val folderRef = foldersRef.add(hashMapOf<String, Any>())
        folderRef
            .addOnSuccessListener { document ->
                val generatedId = document.id
                val newFolder = hashMapOf(
                    "id" to generatedId,
                    "name" to folder.nome,
                )
                document.set(newFolder)
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

    fun moveDeck(targetFolder : Pasta, sourceFolder : Pasta, deck : Baralho, onFailure: () -> Unit){

    }

    fun addDeck(folder : Pasta, deck : Baralho){

        val currentUserEmail = fireBaseAuth.currentUser?.email
        if (currentUserEmail.isNullOrEmpty()) {
            return
        }

        val decksRef = fireStoreDB
            .collection("users")
            .document(currentUserEmail)
            .collection("folders")
            .document(folder.idPasta)
            .collection("decks")

        val deckRef = decksRef.add(hashMapOf<String, Any>())
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
                        Log.e("debug add deck", "certo 1")
                    }
                    .addOnFailureListener {
                        Log.e("debug add deck", "erro 1")
                    }
            }
            .addOnFailureListener {
                Log.e("debug add deck", "erro 2")
            }
    }

    fun getFolders(onSuccess: (List<Pasta>) -> Unit, onFailure: () -> Unit){

        val foldersList = mutableListOf<Pasta>()
        val deckLoadTasks = mutableListOf<Task<QuerySnapshot>>()

        val currentUserEmail = fireBaseAuth.currentUser?.email
        if (currentUserEmail.isNullOrEmpty()) {
            onFailure()
            return
        }


        val userRef = fireStoreDB.collection("users").document(currentUserEmail)
        val foldersRef = userRef.collection("folders")
        val rootRef = userRef.collection("root")

        val rootFolder = Pasta(idPasta = "root", nome = "root")
        foldersList.add(rootFolder)

        deckLoadTasks.add(recGetDecks(rootRef, rootFolder))

        foldersRef.get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val documentData = document.data
                    val folder = Pasta(
                        idPasta = documentData["id"].toString(),
                        nome = documentData["name"].toString(),
                    )
                    foldersList.add(folder)
                    deckLoadTasks.add(recGetDecks(document.reference.collection("decks"), folder))
                }

                Tasks.whenAllSuccess<QuerySnapshot>(deckLoadTasks)
                    .addOnSuccessListener {
                        onSuccess(foldersList)
                    }
                    .addOnFailureListener {
                        onFailure()
                    }
            }
            .addOnFailureListener {
                onFailure()
            }
    }

    private fun recGetDecks(decksRef: CollectionReference, folder: Pasta): Task<QuerySnapshot> {

        return decksRef.get()
            .addOnSuccessListener { deckResult ->
                for (deckDocument in deckResult) {
                    val deckData = deckDocument.data
                    Log.e("pritando deck", "$deckData")
                    val deck = Baralho(
                        idBaralho = deckData["id"].toString(),
                        nome = deckData["name"].toString(),
                        descricao = deckData["description"].toString(),
                        cartoesNovosPorDia = deckData["numberNewCardsPerDay"].toString().toInt(),
                        publico = deckData["public"].toString().toBoolean(),
                        pasta = folder
                    )
                    folder.baralhos.add(deck) // Agora os baralhos vão estar disponíveis na pasta
                }
            }
            .addOnFailureListener {
                Log.e("Firestore", "Erro ao recuperar baralhos", it)
            }
    }


}