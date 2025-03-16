package com.example.brash.nucleo.ui.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.brash.R
import com.example.brash.nucleo.data.remoto.services.AccountService
import com.example.brash.nucleo.utils.UtilsFoos
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

/**
 * ViewModel for managing password reset process and validation.
 *
 * This ViewModel includes logic to handle password reset flow, including verification
 * code checks, new password validation, and updating the password in Firebase.
 *
 * @param application The application context.
 * @param accountService The service for account-related actions, such as password update.
 */
class DefinirSenhaVM(
    application : Application,
    private val accountService: AccountService
) : AndroidViewModel(application) {

    // LiveData for storing the current user's email
    private var _currentUserEmail = MutableLiveData<String>()
    val curretUserEmail get() = _currentUserEmail

    // Firebase Firestore and FirebaseAuth instances
    private val fireStoreDB = FirebaseFirestore.getInstance()
    private val fireBaseAuth = FirebaseAuth.getInstance()

    // LiveData for error messages during password reset process
    private var _errorMessageLD  = MutableLiveData<String>()
    val erroMessageLD get() = _errorMessageLD

    // LiveData for error messages related to verification code
    private var _verificationCodeMessageError = MutableLiveData<String>()
    val verificationCodeMessageError get() = _verificationCodeMessageError

    // LiveData for error messages related to new password
    private var _newPasswordMessageError = MutableLiveData<String>()
    val newPasswordMessageError get() = _newPasswordMessageError

    init {
        _verificationCodeMessageError.value = ""
        _newPasswordMessageError.value = ""
    }

    /**
     * Sets the current user's email address.
     */
    fun setCurrentUserEmail(){
        val email = fireBaseAuth.currentUser!!.email!!
        _currentUserEmail.value = email
    }

    /**
     * Checks if the entered verification code matches the expected one.
     *
     * @param typedVerificationCode The verification code typed by the user.
     * @param verificationCode The expected verification code.
     * @return True if the verification codes match; otherwise, false.
     */
    fun checkVerificationCode(typedVerificationCode : String, verificationCode : String) : Boolean{
        if(typedVerificationCode.isEmpty()){
            _verificationCodeMessageError.value = getStringApplication(R.string.nuc_preencha_todos_campos)
            return false
        }
        else if(typedVerificationCode != verificationCode) {
            _verificationCodeMessageError.value = getStringApplication(R.string.nuc_msg_erro_codigo_invalido)
            return false
        }
        return true
    }

    /**
     * Validates the new password and its retyped version.
     *
     * @param newPassword The new password entered by the user.
     * @param newPasswordRetyped The retyped new password.
     * @return True if the passwords are valid and match; otherwise, false.
     */
    fun checkNewPassword(newPassword : String, newPasswordRetyped : String) : Boolean{
        if(newPassword.isEmpty() || newPasswordRetyped.isEmpty()){
            _newPasswordMessageError.value = getStringApplication(R.string.nuc_preencha_todos_campos)
            return false
        }
        else if(newPassword != newPasswordRetyped){
            _newPasswordMessageError.value = "As senhas não são iguais!"
            return false
        }else if(newPassword.length < 6){
            _newPasswordMessageError.value = "A senha deve conter pelo menos 6 digitos!"
            return false
        }
        return true
    }

    /**
     * Updates the user's password in Firebase.
     *
     * @param email The email address of the user.
     * @param onSuccess A callback to be invoked on successful password change.
     */
    fun updateUsersPassword(email : String, onSuccess: () -> Unit){
        if(!handleChangePasswordInfo(email)){
            return
        }
        viewModelScope.launch {
            try {
                accountService.changePassword(email)
                onSuccess()
            } catch (e: Exception) {
                val msg = when (e) {
                    is FirebaseAuthInvalidUserException -> getStringApplication(R.string.nuc_msg_erro_usuario_nao_existe)
                    is FirebaseNetworkException -> getStringApplication(R.string.nuc_msg_erro_falha_conectar_internet)
                    else -> getStringApplication(R.string.nuc_msg_erro_acesso_banco_dados)
                }
                _errorMessageLD.value = msg
            }
        }
//        fireBaseAuth.currentUser!!.
//        updatePassword(newPassword)
//            .addOnSuccessListener {
//                onSuccess()
//            }
//            .addOnFailureListener {
//                _newPasswordMessageError.value = "Não foi possível conectar ao servidor, tente mais tarde."
//            }
    }

    /**
     * Retrieves the current user's email stored in the account service.
     *
     * @return The current user's email, or null if not found.
     */
    fun currentUserEmail(): String?{
        return accountService.getUserEmail()
    }

    /**
     * Clears the new password error message.
     */
    fun clearNewPasswordMessageError(){
        _newPasswordMessageError.value = ""
    }

    /**
     * Clears the verification code error message.
     */
    fun clearVerificationCodeMessageError(){
        _verificationCodeMessageError.value = ""
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
     * Signs out the current user from Firebase.
     */
    fun signOut(){
        fireBaseAuth.signOut()
    }

    /**
     * Validates the information for changing the password.
     *
     * @param email The email address entered by the user.
     * @return True if the email is valid and not empty; otherwise, false.
     */
    private fun handleChangePasswordInfo(email : String) : Boolean{

        if(email.isNotEmpty() and !UtilsFoos.isValidEmail(email)){
            _errorMessageLD.value = getStringApplication(R.string.nuc_digite_email_valido)

            return false
        }
        else if(email.isEmpty()){
            _errorMessageLD.value = getStringApplication(R.string.nuc_preencha_todos_campos)
            return false
        }

        return true
    }
}