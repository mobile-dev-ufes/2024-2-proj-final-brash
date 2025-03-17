package com.example.brash.nucleo.data.remoto.services

import com.example.brash.nucleo.domain.model.Usuario
import com.example.brash.nucleo.utils.FirebaseAuthVerifyException
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import kotlinx.coroutines.flow.Flow

/**
 * AccountService interface defines the methods and properties for user account management.
 *
 * This service includes operations for signing in, signing up, signing out, changing passwords,
 * deleting accounts, and fetching user information such as the current user and their ID.
 */
interface AccountService {
    /**
     * A flow that emits the current user wrapped in a Usuario object, or null if no user is logged in.
     * This flow listens to Firebase Auth's state changes to provide real-time updates of the current user.
     */
    val currentUser: Flow<Usuario?>

    /**
     * The ID of the currently authenticated user.
     * @return A string representing the current user's UID, or an empty string if no user is logged in.
     */
    val currentUserId: String

    /**
     * Checks whether there is a user currently logged in.
     * @return true if a user is logged in, false otherwise.
     */
    fun hasUser(): Boolean

    /**
     * Retrieves the email address of the currently logged-in user.
     * @return The email of the user, or null if no user is logged in.
     */
    fun getUserEmail(): String?

    /**
     * Signs in a user using their email and password.
     * If the email is not verified, the user is signed out and a FirebaseAuthVerifyException is thrown.
     *
     * @param email The user's email.
     * @param password The user's password.
     * @throws FirebaseAuthInvalidUserException If the user is not found.
     * @throws FirebaseAuthInvalidCredentialsException If the credentials are invalid.
     * @throws FirebaseNetworkException If there is a network issue.
     * @throws FirebaseAuthVerifyException If the email is not verified.
     * @throws Exception For unknown errors.
     */
    suspend fun signIn(email: String, password: String)

    /**
     * Signs up a user with the provided email and password.
     * This will also send an email verification to the user after successful registration.
     *
     * @param email The user's email.
     * @param password The user's password.
     * @throws FirebaseAuthWeakPasswordException If the password is too weak.
     * @throws FirebaseAuthInvalidCredentialsException If the email is invalid.
     * @throws FirebaseAuthUserCollisionException If the email is already registered.
     * @throws FirebaseNetworkException If there is a network issue.
     * @throws Exception For unknown errors.
     */
    suspend fun signUp(email: String, password: String)

    /**
     * Signs out the currently logged-in user.
     * This will log the user out and clear the session.
     */
    suspend fun signOut()

    /**
     * Sends a password reset email to the provided email address.
     *
     * @param email The email address to send the password reset email to.
     * @throws FirebaseAuthInvalidUserException If the user is not found.
     * @throws FirebaseNetworkException If there is a network issue.
     * @throws Exception For unknown errors.
     */
    suspend fun changePassword(email: String)

    /**
     * Deletes the currently logged-in user's account.
     * This will permanently delete the account from Firebase Authentication.
     */
    suspend fun deleteAccount()
}