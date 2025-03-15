package com.example.brash.nucleo.ui.viewModel

import android.app.Activity
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
import com.example.brash.R
import com.example.brash.utilsGeral.Constants
import com.example.brash.nucleo.utils.MyPreferences
import kotlin.math.sign

class LoginVM(application: Application) : AndroidViewModel(application) {

    private var sp : MyPreferences = MyPreferences(application)

    private val auth = FirebaseAuth.getInstance()

    private var _errorMessageLD  = MutableLiveData<String>()
    val erroMessageLD get() = _errorMessageLD


    fun signIn(email : String, password: String, onSucess: () -> Unit){

        if(!handleSignInInfo(email, password)) return

        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            //UtilsFoos.showToast(getApplication(), "Sucesso no Login!")
            onSucess()

        }.addOnFailureListener {

            var msg = getStringApplication(R.string.nuc_msg_erro_login_default)
            when (it) {
                is FirebaseAuthInvalidUserException -> {
                    msg = getStringApplication(R.string.nuc_msg_erro_usuario_nao_existe)
                }

                is FirebaseAuthInvalidCredentialsException -> {
                    msg = getStringApplication(R.string.nuc_msg_erro_usario_ou_senha_incorretos)
                }

                is FirebaseNetworkException -> {
                    msg = getStringApplication(R.string.nuc_msg_erro_falha_conectar_internet)
                }
            }
            //UtilsFoos.showToast(getApplication(), msg)

            _errorMessageLD.value = msg

        }
    }
    fun signOut(){
        auth.signOut()
    }

    private fun getStringApplication(id : Int) : String{
        return getApplication<Application>().getString(id)
    }

    private fun handleSignInInfo(email : String, password: String) : Boolean{

        if(email.isNotEmpty() and !UtilsFoos.isValidEmail(email)){
            //UtilsFoos.showToast(getApplication(), "Digite um Email válido!")
            _errorMessageLD.value = getStringApplication(R.string.nuc_digite_email_valido)

            return false
        }
        else if(email.isEmpty() or password.isEmpty()){
            _errorMessageLD.value = getStringApplication(R.string.nuc_preencha_todos_campos)
            //UtilsFoos.showToast(getApplication(), "Preencha todos os campos!")
            return false
        }

        return true
    }


    fun userStored(onSucess: () -> Unit){
        if (auth.currentUser != null) {
            onSucess()
        }
    }

    fun changeLanguage(activity : Activity, language : String){
        UtilsFoos.setLocale(activity, language)
    }
}