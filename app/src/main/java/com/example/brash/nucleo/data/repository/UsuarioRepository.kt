package com.example.brash.nucleo.data.repository

import android.util.Log
import com.example.brash.nucleo.domain.model.IconeDeUsuario
import com.example.brash.nucleo.domain.model.Usuario
import com.example.brash.nucleo.utils.IconeCor
import com.example.brash.nucleo.utils.IconeImagem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.time.LocalDateTime

class UsuarioRepository {

    private val fireStoreDB = FirebaseFirestore.getInstance()
    private val fireBaseAuth = FirebaseAuth.getInstance()

    // testar
    suspend fun getUser() : Result<Usuario> {
        val currentUserEmail = fireBaseAuth.currentUser?.email
        if (currentUserEmail.isNullOrEmpty()) {
            return Result.failure(Throwable("Usuário não autenticado"))
        }
        return runCatching {
            val userRef = fireStoreDB.collection("users").document(currentUserEmail)
            val userRefSnapshot = userRef.get().await()
            val userData = userRefSnapshot.data ?: throw Exception("Erro obtendo informações de usuario (getUser::UsuarioRepository)")
            val iconObject = IconeDeUsuario(
                cor = IconeCor.valueOf(userData["iconColor"].toString()),
                imagem = IconeImagem.valueOf(userData["iconImage"].toString()),
            )
            val userObject = Usuario(
                idUsuario = currentUserEmail,
                nomeDeUsuario = userData["userName"].toString(),
                nomeDeExibicao = userData["exhibitionName"].toString(),
                email = currentUserEmail,
                iconeDeUsuario = iconObject
            )
            userObject
        }
    }

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

    suspend fun checkExistsUserName(userName: String): Result<Boolean> {
        val currentUserEmail = fireBaseAuth.currentUser?.email
        if (currentUserEmail.isNullOrEmpty()) {
            return Result.failure(Throwable("Usuário não autenticado"))
        }
        return runCatching {
            val usersRef = fireStoreDB.collection("users")
            val querySnapshot = usersRef
                .whereEqualTo("userName", userName)
                .limit(1)
                .get()
                .await()
            !querySnapshot.isEmpty
        }
    }

    /*
    *
    * exhibitionName
    * iconColor
    * iconImage
    * userName
    * */

    suspend fun updateUser(userName : String, exhibitionName : String, iconColor : IconeCor, iconImage: IconeImagem) : Result<Unit>{
        val currentUserEmail = fireBaseAuth.currentUser?.email
        if (currentUserEmail.isNullOrEmpty()) {
            return Result.failure(Throwable("Usuário não autenticado"))
        }
        return runCatching {
            val userRef = fireStoreDB.collection("users").document(currentUserEmail)
            val newUserInfo = mapOf(
                "userName" to userName,
                "exhibitionName" to exhibitionName,
                "iconColor" to iconColor.name,
                "iconImage" to iconImage.name,
            )
            userRef.update(newUserInfo).await()
        }
    }


}