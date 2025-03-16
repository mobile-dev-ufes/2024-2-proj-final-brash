package com.example.brash.nucleo.ui.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.auth.FirebaseAuth


/**
 * ViewModel for managing user settings, including sign out and language change.
 *
 * This ViewModel provides methods for signing out the current user and changing
 * the app's language. The language change functionality is currently under development.
 *
 * @param application The application context.
 */
class ConfiguracaoVM(application: Application) : AndroidViewModel(application) {

    private val auth = FirebaseAuth.getInstance()

    /**
     * Signs out the current user from Firebase.
     *
     * This method will log out the current authenticated user from Firebase.
     */
    fun signOut(){
        auth.signOut()
    }

    /**
     * Changes the app's language based on the provided language code.
     *
     * This function is intended to change the language of the app.
     * The implementation is currently a placeholder and needs to be updated.
     *
     * @param languageCode The language code to switch to, such as "en" for English or "pt" for Portuguese.
     * @param onSucess A callback to be invoked on successful language change.
     */
    fun trocarIdioma(languageCode: String, onSucess: () -> Unit){

        // TODO:: Implement language change functionality

        // Placeholder for language change logic.
        // Once implemented, this code should update the locale configuration and apply it to the app.

        onSucess()  // Call the success callback after implementing the language change.

    }
}