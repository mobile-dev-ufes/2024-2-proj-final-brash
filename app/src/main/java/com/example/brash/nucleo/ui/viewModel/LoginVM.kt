package com.example.brash.nucleo.ui.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.brash.nucleo.utils.UtilsFoos
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException

import androidx.lifecycle.viewModelScope
import com.example.brash.R
import com.example.brash.nucleo.data.remoto.services.AccountService
import com.example.brash.nucleo.utils.FirebaseAuthVerifyException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


/**
 * ViewModel responsible for managing the login logic and data.
 *
 * It includes methods for handling email and password input, signing in the user,
 * and handling error messages related to the login process.
 *
 * @param application The application context.
 * @param accountService The service responsible for account operations such as sign-in.
 */
class LoginVM(
    application: Application,
    private val accountService: AccountService
) : AndroidViewModel(application) {

    // Email input field
    val email = MutableStateFlow("")

    // Password input field
    val password = MutableStateFlow("")

    // LiveData to observe error messages related to login
    private var _errorMessageLD  = MutableLiveData<String>()
    val erroMessageLD get() = _errorMessageLD

    /**
     * Updates the email input field with a new email.
     *
     * @param newEmail The new email to set.
     */
    fun updateEmail(newEmail: String) {
        email.value = newEmail
    }

    /**
     * Updates the password input field with a new password.
     *
     * @param newPassword The new password to set.
     */
    fun updatePassword(newPassword: String) {
        password.value = newPassword
    }

    /**
     * Handles the sign-in process by validating the provided email and password,
     * and then attempting to sign in using the [accountService].
     *
     * @param email The email entered by the user.
     * @param password The password entered by the user.
     * @param onSucess A callback function to be invoked on successful login.
     */
    fun onSignInClick(email : String, password: String, onSucess: () -> Unit) {
        // Validates the email and password
        if(!handleSignInInfo(email, password)){
            return
        }

        // Attempt to sign in using the account service
        viewModelScope.launch {
            try {
                accountService.signIn(email, password)
                onSucess() // Callback on success
            } catch (e: Exception) {
                Log.e("verify email", "$e")
                val msg = when (e) {
                    is FirebaseAuthVerifyException -> getStringApplication(R.string.nuc_msg_erro_conta_nao_verificada)
                    is FirebaseAuthInvalidUserException -> getStringApplication(R.string.nuc_msg_erro_usuario_nao_existe)
                    is FirebaseAuthInvalidCredentialsException -> getStringApplication(R.string.nuc_msg_erro_usario_ou_senha_incorretos)
                    is FirebaseNetworkException -> getStringApplication(R.string.nuc_msg_erro_falha_conectar_internet)
                    else -> getStringApplication(R.string.nuc_msg_erro_login_default)
                }
                _errorMessageLD.value = msg // Set the error message
            }
        }
    }

    /**
     * Checks if there is already a user stored and invokes the [onSucess] callback if there is one.
     *
     * @param onSucess The callback to invoke if a user is already stored.
     */
    fun userStored(onSucess: () -> Unit){
        if (accountService.hasUser()) {
            onSucess()
        }
    }

    /**
     * Retrieves a string resource from the application context.
     *
     * @param id The resource ID of the string.
     * @return The string value associated with the given resource ID.
     */
    private fun getStringApplication(id : Int) : String{
        return getApplication<Application>().getString(id)
    }

    /**
     * Validates the email and password entered by the user.
     *
     * @param email The email entered by the user.
     * @param password The password entered by the user.
     * @return True if the email and password are valid; otherwise, false.
     */
    private fun handleSignInInfo(email : String, password: String) : Boolean{
        // Check if the email is valid
        if(email.isNotEmpty() and !UtilsFoos.isValidEmail(email)){
            _errorMessageLD.value = getStringApplication(R.string.nuc_digite_email_valido)

            return false
        }
        // Check if email or password is empty
        else if(email.isEmpty() or password.isEmpty()){
            _errorMessageLD.value = getStringApplication(R.string.nuc_preencha_todos_campos)
            return false
        }

        return true
    }
}