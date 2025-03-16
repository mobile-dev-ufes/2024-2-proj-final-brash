package com.example.brash.nucleo.ui.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.brash.R
import com.example.brash.nucleo.data.remoto.services.AccountService
import com.example.brash.nucleo.data.repository.UsuarioRepository
import com.example.brash.nucleo.utils.IconeCor
import com.example.brash.nucleo.utils.IconeImagem
import com.example.brash.nucleo.utils.UtilsFoos
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch


/**
 * ViewModel for managing user account registration.
 *
 * This ViewModel handles the registration of new users by validating the input,
 * interacting with Firebase for user authentication, and storing user details in Firestore.
 *
 * @param application The application context.
 * @param accountService The service for handling Firebase authentication operations.
 */
class CadastrarContaVM (
    application: Application,
    private val accountService: AccountService
) : AndroidViewModel(application) {

    private var _formMessageError =  MutableLiveData<String>()
    val formMessageError get() = _formMessageError

    private var _verificationCodeMessageError = MutableLiveData<String>()
    val verificationCodeMessageError get() = _verificationCodeMessageError

    private val usuarioRepository = UsuarioRepository()

    private val fireStoreDB = FirebaseFirestore.getInstance()

    /**
     * Clears any existing form error messages.
     */
    fun clearFormMessageError(){
        _formMessageError.value = ""
    }

    /**
     * Clears any existing verification code error messages.
     */
    fun clearVerificationCodeMessageError(){
        _verificationCodeMessageError.value = ""
    }

    /**
     * Validates the verification code entered by the user.
     *
     * @param typedVerificationCode The verification code entered by the user.
     * @param verificationCode The expected verification code.
     * @return True if the codes match and the field is not empty, false otherwise.
     */
    fun checkVerificationCode(typedVerificationCode : String, verificationCode : String) : Boolean{

        if(typedVerificationCode.isEmpty()){
            _verificationCodeMessageError.value = getStringApplication(R.string.nuc_preencha_todos_campos)
            return false
        }

        if(typedVerificationCode != verificationCode) {
            _verificationCodeMessageError.value = getStringApplication(R.string.nuc_msg_erro_codigo_invalido)
            return false
        }
        return true
    }

    /**
     * Registers a new user in Firebase and stores their information in Firestore.
     *
     * @param userName The username of the new user.
     * @param exhibitionName The exhibition name of the new user.
     * @param email The email address of the new user.
     * @param password The password for the new user.
     * @param onSuccess A callback to be invoked if the registration is successful.
     */
    fun registerNewUser(userName : String, exhibitionName: String, email:String, password: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            if(!handleRegisterForm(userName, exhibitionName, email, password)){
                return@launch
            }

            try {
                // Register user in Firebase
                accountService.signUp(email, password)

                usuarioRepository.createUser(userName, exhibitionName, email).onSuccess {
                    onSuccess()
                }.onFailure {
                    _formMessageError.value = getStringApplication(R.string.nuc_msg_erro_acesso_banco_dados)
                }

                accountService.signOut()
            } catch (e: Exception) {
                val msg = when (e) {
                    is FirebaseAuthWeakPasswordException -> getStringApplication(R.string.nuc_msg_erro_senha_fraca)
                    is FirebaseAuthInvalidCredentialsException -> getStringApplication(R.string.nuc_digite_email_valido)
                    is FirebaseAuthUserCollisionException -> getStringApplication(R.string.nuc_msg_erro_email_ja_cadastrado)
                    is FirebaseNetworkException -> getStringApplication(R.string.nuc_msg_erro_falha_conectar_internet)
                    else -> getStringApplication(R.string.nuc_msg_erro_acesso_banco_dados)
                }
                _formMessageError.value = msg
            }
        }
    }

    /**
     * Helper function to retrieve string resources from the application context.
     *
     * @param id The resource ID of the string.
     * @return The string value associated with the resource ID.
     */
    private fun getStringApplication(id : Int) : String{
        return getApplication<Application>().getString(id)
    }

    /**
     * Validates the registration form input.
     *
     * @param userName The username input by the user.
     * @param exhibitionName The exhibition name input by the user.
     * @param email The email input by the user.
     * @param password The password input by the user.
     * @return True if all fields are valid, false otherwise.
     */
    private suspend fun handleRegisterForm(userName : String, exhibitionName: String, email:String, password: String) : Boolean{

        val userExists = usuarioRepository.checkExistsUserName(userName)
        userExists.onSuccess { exists ->
            if (exists) {
                _formMessageError.value = getStringApplication(R.string.nuc_msg_erro_nome_usuario_ja_cadastrado)
                return false
            }
        }.onFailure {
            _formMessageError.value = getStringApplication(R.string.nuc_msg_erro_acesso_banco_dados)
            return false
        }

        if (userName.isEmpty() || exhibitionName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            _formMessageError.value = getStringApplication(R.string.nuc_preencha_todos_campos)
            return false
        } else if (!UtilsFoos.isValidEmail(email)) {
            _formMessageError.value = getStringApplication(R.string.nuc_digite_email_valido)
            return false
        }
        return true
    }
}