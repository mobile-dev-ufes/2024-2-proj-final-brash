package com.example.brash.nucleo.data.repository

import com.example.brash.nucleo.domain.model.IconeDeUsuario
import com.example.brash.nucleo.domain.model.Usuario
import com.example.brash.nucleo.utils.IconeCor
import com.example.brash.nucleo.utils.IconeImagem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

/**
 * UsuarioRepository is a repository class responsible for handling user-related data operations.
 * It interacts with Firebase Firestore and Firebase Authentication to retrieve and manage user data.
 * This class provides methods for retrieving and updating user information, including user icons and profile details.
 */
class UsuarioRepository {

    private val fireStoreDB = FirebaseFirestore.getInstance()
    private val fireBaseAuth = FirebaseAuth.getInstance()

    /**
     * Retrieves the current authenticated user's details, including their username, display name,
     * and user icon (color and image).
     * @return A Result object containing the user information wrapped in a Usuario object or an error if any.
     */
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

    /**
     * Updates the user's icon color and image in Firestore.
     * @param iconColor The new icon color to set for the user.
     * @param iconImage The new icon image to set for the user.
     * @return A Result indicating success or failure of the update.
     */
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

    /**
     * Retrieves the current user's icon (color and image) from Firestore.
     * @return A Result containing the IconeDeUsuario object or an error.
     */
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

    /**
     * Checks if a given username already exists in the database.
     * @param userName The username to check.
     * @return A Result indicating whether the username already exists (true) or not (false).
     */
    suspend fun checkExistsUserName(userName: String): Result<Boolean> {
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

    /**
     * Creates a new user in Firestore with the specified user details.
     * Initially sets default icon color and image for the user.
     * @param userName The username of the user.
     * @param exhibitionName The display name of the user.
     * @param email The email address of the user.
     * @return A Result indicating success or failure of the operation.
     */
    suspend fun createUser(userName : String, exhibitionName : String, email : String) : Result<Unit>{
        return runCatching {
            val userMap = hashMapOf(
                "userName" to userName,
                "exhibitionName" to exhibitionName,
                "email" to email,
                "iconColor" to IconeCor.DEEP_PURPLE,
                "iconImage" to IconeImagem.PADRAO
            )
            fireStoreDB.collection("users").document(email).set(userMap).await()
        }
    }

    /**
     * Updates the user profile with new username, exhibition name, icon color, and icon image.
     * @param userName The new username to set.
     * @param exhibitionName The new display name to set.
     * @param iconColor The new icon color to set.
     * @param iconImage The new icon image to set.
     * @return A Result indicating success or failure of the update operation.
     */
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

    /**
     * Retrieves the root folder ID for the current user, based on their email.
     * @return The root folder ID as a string.
     * @throws Exception if the user is not authenticated.
     */
    fun getRootFolderId() : String{
        val currentUserEmail = fireBaseAuth.currentUser?.email
        if (currentUserEmail.isNullOrEmpty()) {
            throw Exception("Usuário não autenticado!!!")
        }
        val rootFolderId = "root/$currentUserEmail"
        return rootFolderId
    }
}