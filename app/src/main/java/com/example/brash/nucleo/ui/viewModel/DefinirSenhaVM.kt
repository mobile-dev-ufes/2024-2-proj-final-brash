package com.example.brash.nucleo.ui.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.brash.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class DefinirSenhaVM(application : Application) : AndroidViewModel(application) {

    private var _currentUserEmail = MutableLiveData<String>()
    val curretUserEmail get() = _currentUserEmail

    private val fireStoreDB = FirebaseFirestore.getInstance()
    private val fireBaseAuth = FirebaseAuth.getInstance()

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

    fun updateUsersPassword(newPassword : String, onSuccess: () -> Unit){

        fireBaseAuth.currentUser!!.
        updatePassword(newPassword)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                _newPasswordMessageError.value = "Não foi possível conectar ao servidor, tente mais tarde."
            }
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

}