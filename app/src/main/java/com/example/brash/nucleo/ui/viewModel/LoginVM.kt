package com.example.brash.nucleo.ui.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.brash.nucleo.utils.UtilsFoos
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

class LoginVM(application: Application) : AndroidViewModel(application) {

    private val auth = FirebaseAuth.getInstance()

    private var _errorMessageLD  = MutableLiveData<String>()
    val erroMessageLD get() = _errorMessageLD


    fun signIn(email : String, password: String, onSucess: () -> Unit){

        if(!handleSignInInfo(email, password)) return

        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {

            UtilsFoos.showToast(getApplication(), "Sucesso no Login!")
            onSucess()

        }.addOnFailureListener {

            var msg = "Houve algum problema no login, tente novamente"
            if (it is FirebaseAuthInvalidUserException) {
                msg = "Este usuário não existe"
            } else if (it is FirebaseAuthInvalidCredentialsException) {
                msg = "Usuário ou senha incorretos"
            } else if (it is FirebaseNetworkException) {
                msg = "Falha ao conectar com a internet"
            }
            //UtilsFoos.showToast(getApplication(), msg)

            _errorMessageLD.value = msg

        }
    }

    private fun handleSignInInfo(email : String, password: String) : Boolean{

        if(email.isNotEmpty() and !UtilsFoos.isValidEmail(email)){
            //UtilsFoos.showToast(getApplication(), "Digite um Email válido!")
            _errorMessageLD.value = "Digite um Email válido!"

            return false
        }
        else if(email.isEmpty() or password.isEmpty()){
            _errorMessageLD.value = "Preencha todos os campos!"
            //UtilsFoos.showToast(getApplication(), "Preencha todos os campos!")
            return false
        }

        return true
    }

}