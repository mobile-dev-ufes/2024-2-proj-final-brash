package com.example.brash.aprendizado.gestaoDeConteudo.data.repository

import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Baralho
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Cartao
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Dica
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Pasta
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class DicaRepository {

    private val fireStoreDB = FirebaseFirestore.getInstance()
    private val fireBaseAuth = FirebaseAuth.getInstance()

    // data class Dica (
    //    val idDica: String = "",
    //    val texto: String = "",
    //    val cartao: Cartao? = null
    //)

    private fun constructFireStoreHint(id : String, text : String) : HashMap<String, Any>{
        return hashMapOf(
            "text" to text,
            "id" to id
        )
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
    suspend fun createHint(card : Cartao, hint : Dica) : Result<String>{
        val currentUserEmail = fireBaseAuth.currentUser?.email
            ?: return Result.failure(Throwable("Usuário não autenticado"))

        return runCatching {
            val deck = card.baralho
            val folder = deck.pasta ?: return Result.failure(Throwable("Pasta do cartão não encontrada (createHint)"))

            val cardRef = getDeckReference(deck, folder, currentUserEmail)
                .collection("cards")
                .document(card.idCartao)

            val hintsRef = cardRef.collection("hints")
            val hintId = hintsRef.document().id
            val newHintInfo = constructFireStoreHint(hintId, hint.texto)
            hintsRef.document(hintId).set(newHintInfo).await()

            hintId
        }
    }

    // testar
    suspend fun deleteHint(hint : Dica) : Result<Unit>{
        val currentUserEmail = fireBaseAuth.currentUser?.email
            ?: return Result.failure(Throwable("Usuário não autenticado"))
        return runCatching {

            val card = hint.cartao
            val deck = card?.baralho
            val folder = deck?.pasta ?: return Result.failure(Throwable("Pasta do cartão não encontrada (deleteHint)"))

            val hintRef = getDeckReference(deck, folder, currentUserEmail)
                .collection("cards")
                .document(card.idCartao)
                .collection("hints")
                .document(hint.idDica)

            hintRef.delete().await()
        }
    }

    // testar
    suspend fun updateHint(hint : Dica, text : String) : Result<Unit>{
        val currentUserEmail = fireBaseAuth.currentUser?.email
            ?: return Result.failure(Throwable("Usuário não autenticado"))
        return runCatching {

            val card = hint.cartao
            val deck = card?.baralho
            val folder = deck?.pasta ?: return Result.failure(Throwable("Pasta do cartão não encontrada (deleteHint)"))

            val hintRef = getDeckReference(deck, folder, currentUserEmail)
                .collection("cards")
                .document(card.idCartao)
                .collection("hints")
                .document(hint.idDica)
            val updateInfo = mapOf(
                "text" to text
            )
            hintRef.update(updateInfo).await()
        }
    }


}