package com.example.brash.nucleo.ui.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.brash.R
import com.example.brash.network.ClientRetrofit
import com.example.brash.nucleo.data.remoto.entities.EmailRequestEntity
import com.example.brash.nucleo.data.remoto.services.AccountService
import com.example.brash.nucleo.data.remoto.services.EmailApi
import com.example.brash.nucleo.utils.UtilsFoos
import com.example.brash.utilsGeral.Constants
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CadastrarContaVM (
    application: Application,
    private val accountService: AccountService
) : AndroidViewModel(application) {

    private var _formMessageError =  MutableLiveData<String>()
    val formMessageError get() = _formMessageError

    private var _verificationCodeMessageError = MutableLiveData<String>()
    val verificationCodeMessageError get() = _verificationCodeMessageError

    private val fireStoreDB = FirebaseFirestore.getInstance()

    fun clearFormMessageError(){
        _formMessageError.value = ""
    }

    fun clearVerificationCodeMessageError(){
        _verificationCodeMessageError.value = ""
    }

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

    fun registerNewUser(userName : String, exhibitionName: String, email:String, password: String, onSuccess: () -> Unit) {
        if(!handleRegisterForm(userName, exhibitionName, email, password)){
            return
        }

        viewModelScope.launch {
            try {
                accountService.signUp(email, password)
                val userMap = hashMapOf(
                    "userName" to userName,
                    "exhibitionName" to exhibitionName,
                    "email" to email
                )
                fireStoreDB.collection("users").document(email)
                    .set(userMap)
                    .addOnSuccessListener {
                        onSuccess()
                    }
                    .addOnFailureListener {
                        _verificationCodeMessageError.value =
                            getStringApplication(R.string.nuc_msg_erro_acesso_banco_dados)
                    }
            } catch (e: Exception) {
                val msg = when (e) {
                    is FirebaseAuthWeakPasswordException -> getStringApplication(R.string.nuc_msg_erro_senha_fraca)
                    is FirebaseAuthInvalidCredentialsException -> getStringApplication(R.string.nuc_digite_email_valido)
                    is FirebaseAuthUserCollisionException -> getStringApplication(R.string.nuc_msg_erro_email_ja_cadastrado)
                    is FirebaseNetworkException -> getStringApplication(R.string.nuc_msg_erro_falha_conectar_internet)
                    else -> getStringApplication(R.string.nuc_msg_erro_acesso_banco_dados)
                }
            }
        }
    }

    private fun getStringApplication(id : Int) : String{
        return getApplication<Application>().getString(id)
    }

    fun handleRegisterForm(userName : String, exhibitionName: String, email:String, password: String) : Boolean{

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