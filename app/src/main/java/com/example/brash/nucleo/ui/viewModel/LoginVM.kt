package com.example.brash.nucleo.ui.viewModel

import android.app.Application
import android.util.Log
import android.widget.Toast
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
import com.google.firebase.auth.FirebaseAuthEmailException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoginVM(
    application: Application,
    private val accountService: AccountService
) : AndroidViewModel(application) {

    val email = MutableStateFlow("")
    val password = MutableStateFlow("")

    private var _errorMessageLD  = MutableLiveData<String>()
    val erroMessageLD get() = _errorMessageLD

    fun updateEmail(newEmail: String) {
        email.value = newEmail
    }

    fun updatePassword(newPassword: String) {
        password.value = newPassword
    }

    fun onSignInClick(email : String, password: String, onSucess: () -> Unit) {
        if(!handleSignInInfo(email, password)){
            return
        }

        viewModelScope.launch {
            try {
                accountService.signIn(email, password)
                onSucess()
            } catch (e: Exception) {
                Log.e("verify email", "$e")
                val msg = when (e) {
                    is FirebaseAuthVerifyException -> getStringApplication(R.string.nuc_msg_erro_conta_nao_verificada)
                    is FirebaseAuthInvalidUserException -> getStringApplication(R.string.nuc_msg_erro_usuario_nao_existe)
                    is FirebaseAuthInvalidCredentialsException -> getStringApplication(R.string.nuc_msg_erro_usario_ou_senha_incorretos)
                    is FirebaseNetworkException -> getStringApplication(R.string.nuc_msg_erro_falha_conectar_internet)
                    else -> getStringApplication(R.string.nuc_msg_erro_login_default)
                }
                _errorMessageLD.value = msg
            }
        }
    }

    fun userStored(onSucess: () -> Unit){
        if (accountService.hasUser()) {
            onSucess()
        }
    }

    private fun getStringApplication(id : Int) : String{
        return getApplication<Application>().getString(id)
    }

    private fun handleSignInInfo(email : String, password: String) : Boolean{

        if(email.isNotEmpty() and !UtilsFoos.isValidEmail(email)){
            _errorMessageLD.value = getStringApplication(R.string.nuc_digite_email_valido)

            return false
        }
        else if(email.isEmpty() or password.isEmpty()){
            _errorMessageLD.value = getStringApplication(R.string.nuc_preencha_todos_campos)
            return false
        }

        return true
    }
}