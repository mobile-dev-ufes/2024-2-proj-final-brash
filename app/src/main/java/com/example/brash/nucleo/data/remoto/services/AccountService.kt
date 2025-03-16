package com.example.brash.nucleo.data.remoto.services

import com.example.brash.nucleo.domain.model.Usuario
import kotlinx.coroutines.flow.Flow

interface AccountService {
    val currentUser: Flow<Usuario?>
    val currentUserId: String
    fun hasUser(): Boolean
    fun getUserEmail(): String?
    suspend fun signIn(email: String, password: String)
    suspend fun signUp(email: String, password: String)
    suspend fun signOut()
    suspend fun changePassword(email: String)
    suspend fun deleteAccount()
}