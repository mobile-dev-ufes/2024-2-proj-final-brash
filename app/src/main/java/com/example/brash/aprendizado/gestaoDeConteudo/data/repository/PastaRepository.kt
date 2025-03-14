package com.example.brash.aprendizado.gestaoDeConteudo.data.repository

import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Baralho
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Pasta
import com.example.brash.nucleo.domain.model.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

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

}