package com.example.brash.nucleo.data.remoto.services.impl

import com.example.brash.nucleo.data.remoto.services.AccountService
import com.example.brash.nucleo.domain.model.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.Firebase
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import com.example.brash.nucleo.utils.FirebaseAuthVerifyException

/**
 * AccountServiceImpl is an implementation of the AccountService interface that provides user authentication
 * and account management features using Firebase Authentication.
 *
 * This class manages actions like signing in, signing up, signing out, changing passwords,
 * sending email verification, and deleting user accounts.
 */
class AccountServiceImpl () : AccountService {

    /**
     * Observes the currently authenticated user and emits changes through a Flow.
     * If there is a logged-in user, it returns a Usuario instance with the user's UID.
     */
    override val currentUser: Flow<Usuario?>
        get() = callbackFlow {
            val listener =
                FirebaseAuth.AuthStateListener { auth ->
                    this.trySend(auth.currentUser?.let { Usuario(it.uid) })
                }
            Firebase.auth.addAuthStateListener(listener)
            awaitClose { Firebase.auth.removeAuthStateListener(listener) }
        }


    /**
     * Retrieves the currently authenticated user's UID.
     * If no user is logged in, returns an empty string.
     */
    override val currentUserId: String
        get() = Firebase.auth.currentUser?.uid.orEmpty()

    /**
     * Checks if there is an authenticated user.
     * @return True if a user is logged in, false otherwise.
     */
    override fun hasUser(): Boolean {
        return Firebase.auth.currentUser != null
    }

    /**
     * Retrieves the authenticated user's email.
     * @return The email of the logged-in user or null if no user is logged in.
     */
    override fun getUserEmail(): String? {
        return Firebase.auth.currentUser?.email
    }



    /**
     * Authenticates the user using email and password.
     * If the email is not verified, the user is logged out and an exception is thrown.
     * @throws FirebaseAuthInvalidUserException if the user is not found.
     * @throws FirebaseAuthInvalidCredentialsException if the email or password is incorrect.
     * @throws FirebaseNetworkException if there is a network issue.
     * @throws FirebaseAuthVerifyException if the email is not verified.
     * @throws Exception for unknown errors.
     */
    override suspend fun signIn(email: String, password: String) {
        try{
            val authResult = Firebase.auth.signInWithEmailAndPassword(email, password).await()

            if (authResult.user?.isEmailVerified == false) {
                signOut()
                throw FirebaseAuthVerifyException("EMAILEXP", "Email nao verificado.")
            }

        } catch (e: FirebaseAuthInvalidUserException) {
            throw FirebaseAuthInvalidUserException(e.errorCode, "Usuário não encontrado")
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            throw FirebaseAuthInvalidCredentialsException(e.errorCode, "Usuário ou senha incorretos")
        } catch (e: FirebaseNetworkException) {
            throw FirebaseNetworkException("Falha ao conectar à internet")
        } catch (e: FirebaseAuthVerifyException) {
            throw FirebaseAuthVerifyException("EMAILEXP", "Email nao verificado.")
        } catch (e: Exception) {
            throw Exception("Erro desconhecido ao fazer login")
        }
    }


    /**
     * Registers a new user using email and password.
     * After successful registration, an email verification is sent.
     * @throws FirebaseAuthWeakPasswordException if the password is too weak.
     * @throws FirebaseAuthInvalidCredentialsException if the email is invalid.
     * @throws FirebaseAuthUserCollisionException if the email is already registered.
     * @throws FirebaseNetworkException if there is a network issue.
     * @throws Exception for unknown errors.
     */
    override suspend fun signUp(email: String, password: String) {
        try {
            // Criação do usuário no Firebase Authentication
            val authResult = Firebase.auth.createUserWithEmailAndPassword(email, password).await()

            // Enviar o e-mail de verificação para o usuário recém-criado
            authResult.user?.sendEmailVerification()?.await()
        } catch (e: FirebaseAuthWeakPasswordException) {
            throw FirebaseAuthWeakPasswordException(e.errorCode, "Senha muito fraca", "")
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            throw FirebaseAuthInvalidCredentialsException(e.errorCode, "E-mail inválido")
        } catch (e: FirebaseAuthUserCollisionException) {
            throw FirebaseAuthUserCollisionException(e.errorCode, "E-mail já cadastrado")
        } catch (e: FirebaseNetworkException) {
            throw FirebaseNetworkException("Falha ao conectar à internet")
        } catch (e: Exception) {
            throw Exception("Erro desconhecido ao registrar")
        }
    }


    /**
     * Signs out the currently authenticated user.
     */
    override suspend fun signOut() {
        Firebase.auth.signOut()
    }


    /**
     * Sends a password reset email to the given email address.
     * @throws FirebaseAuthInvalidUserException if the user is not found.
     * @throws FirebaseNetworkException if there is a network issue.
     * @throws Exception for unknown errors.
     */
    override suspend fun changePassword(email: String) {
        try {
            Firebase.auth.sendPasswordResetEmail(email).await()
        } catch (e: FirebaseAuthInvalidUserException) {
            throw FirebaseAuthInvalidUserException(e.errorCode, "Usuário não encontrado")
        } catch (e: FirebaseNetworkException) {
            throw FirebaseNetworkException("Falha ao conectar à internet")
        } catch (e: Exception) {
            throw Exception("Erro desconhecido ao enviar email de redefinição de senha")
        }
    }


    /**
     * Deletes the currently authenticated user's account.
     */
    override suspend fun deleteAccount() {
        Firebase.auth.currentUser!!.delete().await()
    }
}