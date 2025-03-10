package com.example.brash.nucleo.ui.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    private val emailApiService = ClientRetrofit.createService(EmailApi::class.java, Constants.EMAIL_API_BASE_URL)

    fun registerNewUser(userName : String, exhibitionName: String, email:String, password: String){

        if(handleRegisterForm(userName, exhibitionName, email, password)){
            UtilsFoos.showToast(getApplication(), "$userName, $exhibitionName, $email, $password")
        }
    }

     fun handleRegisterForm(userName : String, exhibitionName: String, email:String, password: String) : Boolean{

        if(userName.isEmpty() or exhibitionName.isEmpty() or email.isEmpty() or password.isEmpty()){
            _formMessageError.value = "Preencha todos os campos!"
            return false
        }else if(!UtilsFoos.isValidEmail(email)){
            _formMessageError.value = "Digite um email válido!"
            return false
        }
        return true
    }

    fun olaMundo(){
        UtilsFoos.showToast(getApplication(), "ola mundo")

        val emailRequest = EmailRequestEntity(
            to = "matheussaick@gmail.com",
            subject = "Teste do retrofit no app",
            text = "código é 777"
        )

        emailApiService.sendEmail(emailRequest).enqueue( object : Callback<Void>{
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if(response.isSuccessful){
                    UtilsFoos.showToast(getApplication(), "email enviado com sucesso para matheussaick@gmail.com")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                UtilsFoos.showToast(getApplication(), "Erro no envio do email")
                Log.e("EMAIL API", "Falha na requisição do email no servidor")
            }
        }

        )


    }

}