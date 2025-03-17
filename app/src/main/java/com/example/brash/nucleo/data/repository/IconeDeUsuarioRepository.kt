package com.example.brash.nucleo.data.repository

import com.example.brash.nucleo.domain.model.IconeDeUsuario
import com.example.brash.nucleo.utils.IconeCor
import com.example.brash.nucleo.utils.IconeImagem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

//NOT USED

//data class IconeDeUsuario (
//    val idIconeDeUsuario: Long = 0,
//    val cor: IconeCor = IconeCor.DEEP_PURPLE,
//    val imagemPath: IconeImagem = IconeImagem.PADRAO
//)

class IconeDeUsuarioRepository {

    private val fireStoreDB = FirebaseFirestore.getInstance()
    private val fireBaseAuth = FirebaseAuth.getInstance()

    // testar
    suspend fun updateIcon(iconColor : IconeCor, iconImage : IconeImagem) : Result<Unit>{
        val currentUserEmail = fireBaseAuth.currentUser?.email
        if (currentUserEmail.isNullOrEmpty()) {
            return Result.failure(Throwable("Usuário não autenticado"))
        }
        return runCatching {
            val userRef = fireStoreDB.collection("users").document(currentUserEmail)
            val newIconInfo = mapOf(
                "iconColor" to iconColor.name,
                "iconImage" to iconImage.name,
            )
            userRef.update(newIconInfo).await()
        }
    }

    // testar
    suspend fun getIcon() : Result<IconeDeUsuario>{
        val currentUserEmail = fireBaseAuth.currentUser?.email
        if (currentUserEmail.isNullOrEmpty()) {
            return Result.failure(Throwable("Usuário não autenticado"))
        }
        return runCatching {
            val userRef = fireStoreDB.collection("users").document(currentUserEmail)
            val userRefSnapshot = userRef.get().await()
            val userData = userRefSnapshot.data ?: throw Exception("Dados do usuário não encontrados (getIcon::IconeDeUsuarioRepository)")
            val iconObject = IconeDeUsuario(
                cor = IconeCor.valueOf(userData["iconColor"].toString()),
                imagem = IconeImagem.valueOf(userData["iconImage"].toString()),
            )
            iconObject
        }
    }

}