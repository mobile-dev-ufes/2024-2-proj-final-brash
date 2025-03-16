package com.example.brash.nucleo.data.remoto.services.impl

import android.content.Context
import com.example.brash.nucleo.data.remoto.services.AccountService
import com.example.brash.nucleo.domain.model.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.Firebase
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthEmailException
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import android.util.Log
import com.example.brash.nucleo.utils.FirebaseAuthVerifyException

class AccountServiceImpl () : AccountService {

    override val currentUser: Flow<Usuario?>
        get() = callbackFlow {
            val listener =
                FirebaseAuth.AuthStateListener { auth ->
                    this.trySend(auth.currentUser?.let { Usuario(it.uid) })
                }
            Firebase.auth.addAuthStateListener(listener)
            awaitClose { Firebase.auth.removeAuthStateListener(listener) }
        }

    override val currentUserId: String
        get() = Firebase.auth.currentUser?.uid.orEmpty()

    override fun hasUser(): Boolean {
        return Firebase.auth.currentUser != null
    }

    override fun getUserEmail(): String? {
        return Firebase.auth.currentUser?.email
    }

    override suspend fun signIn(email: String, password: String) {
        try{
            val authResult = Firebase.auth.signInWithEmailAndPassword(email, password).await()

            if (authResult.user?.isEmailVerified == false) {
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

    override suspend fun signOut() {
        Firebase.auth.signOut()
    }

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

    override suspend fun deleteAccount() {
        Firebase.auth.currentUser!!.delete().await()
    }
}