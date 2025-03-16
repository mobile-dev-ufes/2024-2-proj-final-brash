package com.example.brash.nucleo.ui.viewModel

import android.accounts.Account
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.brash.R
import com.example.brash.nucleo.data.remoto.services.AccountService
import com.example.brash.nucleo.utils.UtilsFoos
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class DefinirSenhaVM(
    application : Application,
    private val accountService: AccountService
) : AndroidViewModel(application) {

    private var _currentUserEmail = MutableLiveData<String>()
    val curretUserEmail get() = _currentUserEmail

    private val fireStoreDB = FirebaseFirestore.getInstance()
    private val fireBaseAuth = FirebaseAuth.getInstance()

    private var _errorMessageLD  = MutableLiveData<String>()
    val erroMessageLD get() = _errorMessageLD

    private var _verificationCodeMessageError = MutableLiveData<String>()
    val verificationCodeMessageError get() = _verificationCodeMessageError

    private var _newPasswordMessageError = MutableLiveData<String>()
    val newPasswordMessageError get() = _newPasswordMessageError

    init {
        _verificationCodeMessageError.value = ""
        _newPasswordMessageError.value = ""
    }

    fun setCurrentUserEmail(){
        val email = fireBaseAuth.currentUser!!.email!!
        _currentUserEmail.value = email
    }

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

    fun currentUserEmail(): String?{
        return accountService.getUserEmail()
    }

    fun clearNewPasswordMessageError(){
        _newPasswordMessageError.value = ""
    }

    fun clearVerificationCodeMessageError(){
        _verificationCodeMessageError.value = ""
    }

    private fun getStringApplication(id : Int) : String{
        return getApplication<Application>().getString(id)
    }

    fun signOut(){
        fireBaseAuth.signOut()
    }

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