package com.example.brash.nucleo.ui.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.brash.R
import com.example.brash.network.ClientRetrofit
import com.example.brash.nucleo.data.remoto.entities.EmailRequestEntity
import com.example.brash.nucleo.data.remoto.services.EmailApi
import com.example.brash.nucleo.utils.Constants
import com.example.brash.nucleo.utils.UtilsFoos
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CadastrarContaVM (application: Application) : AndroidViewModel(application) {

    private var _formMessageError =  MutableLiveData<String>()
    val formMessageError get() = _formMessageError

    private var _verificationCodeMessageError = MutableLiveData<String>()
    val verificationCodeMessageError get() = _verificationCodeMessageError

    private val emailApiService = ClientRetrofit.createService(EmailApi::class.java, Constants.EMAIL_API_BASE_URL)

    fun registerNewUser(userName : String, exhibitionName: String, email:String, password: String, onSucess: () -> Unit){

    }

    fun showToastForFragments(message : String){
        UtilsFoos.showToast(getApplication(), message)
    }

    private fun getStringApplication(id : Int) : String{
        return getApplication<Application>().getString(id)
    }

    fun handleRegisterForm(userName : String, exhibitionName: String, email:String, password: String, onSucess: () -> Unit){
        if(userName.isEmpty() or exhibitionName.isEmpty() or email.isEmpty() or password.isEmpty()){
            _formMessageError.value = getStringApplication(R.string.nuc_preencha_todos_campos)
        }else if(!UtilsFoos.isValidEmail(email)){
            _formMessageError.value = getStringApplication(R.string.nuc_digite_email_valido)
        }else{
            onSucess()
        }
    }

    fun sendCodeToEmail(email : String, emailVerificationCode : String, onSucess: () -> Unit){
        UtilsFoos.showToast(getApplication(), "ola mundo")

        val emailRequest = EmailRequestEntity(
            to = email,
            subject = "Código de verificação de criação de conta do aplicativo Brash",
            text = "código é $emailVerificationCode"
        )

        emailApiService.sendEmail(emailRequest).enqueue( object : Callback<Void>{
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if(response.isSuccessful){
                    UtilsFoos.showToast(getApplication(), "Email enviado com sucesso!")
                    onSucess()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                //UtilsFoos.showToast(getApplication(), "Erro no envio do email")
                val errorMsg = getStringApplication(R.string.nuc_msg_erro_envio_email)
                _formMessageError.value = errorMsg
                Log.e("EMAIL API", errorMsg)
            }
        })


    }

}