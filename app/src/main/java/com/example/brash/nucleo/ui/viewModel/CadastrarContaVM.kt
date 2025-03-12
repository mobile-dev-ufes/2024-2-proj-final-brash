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
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CadastrarContaVM (application: Application) : AndroidViewModel(application) {

    private var _formMessageError =  MutableLiveData<String>()
    val formMessageError get() = _formMessageError

    private var _verificationCodeMessageError = MutableLiveData<String>()
    val verificationCodeMessageError get() = _verificationCodeMessageError

    private val emailApiService = ClientRetrofit.createService(EmailApi::class.java, Constants.EMAIL_API_BASE_URL)

    private val fireStoreDB = FirebaseFirestore.getInstance()
    private val fireBaseAuth = FirebaseAuth.getInstance()


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

        fireBaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
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
            }
        }.addOnFailureListener {
            // os outros erros já foram cobertos anteriormente
            if(it is FirebaseNetworkException){
                _verificationCodeMessageError.value = getStringApplication(R.string.nuc_msg_erro_falha_conectar_internet)
            }
        }
    }

    fun showToastForFragments(message : String){
        UtilsFoos.showToast(getApplication(), message)
    }

    private fun getStringApplication(id : Int) : String{
        return getApplication<Application>().getString(id)
    }

    suspend fun handleRegisterForm(userName : String, exhibitionName: String, email:String, password: String, onSuccess: () -> Unit){


        if (userName.isEmpty() || exhibitionName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            _formMessageError.value = getStringApplication(R.string.nuc_preencha_todos_campos)
            return
        } else if (!UtilsFoos.isValidEmail(email)) {
            _formMessageError.value = getStringApplication(R.string.nuc_digite_email_valido)
            return
        } else if (password.length < 6) {
            _formMessageError.value = getStringApplication(R.string.nuc_msg_erro_senha_fraca)
            return
        }

        val checkEmailExists: suspend (String) -> Boolean = { email ->
            val snapshot = FirebaseFirestore.getInstance()
                .collection("users")
                .whereEqualTo("email", email)
                .get()
                .await()
            !snapshot.isEmpty
        }

        val checkUserNameExistis: suspend (String) -> Boolean = {email ->
            val snapshot = FirebaseFirestore.getInstance()
                .collection("users")
                .whereEqualTo("userName", userName)
                .get()
                .await()
            !snapshot.isEmpty
        }

        try {
            coroutineScope {
                val emailExists = checkEmailExists(email)
                val userNameExists = checkUserNameExistis(userName)
                if (emailExists) {
                    _formMessageError.value = getStringApplication(R.string.nuc_msg_erro_email_ja_cadastrado)
                    return@coroutineScope
                }
                if (userNameExists) {
                    _formMessageError.value = getStringApplication(R.string.nuc_msg_erro_nome_usuario_ja_cadastrado)
                    return@coroutineScope
                }
                onSuccess()
            }
        } catch (e: Exception) {
            _formMessageError.value = getStringApplication(R.string.nuc_msg_erro_acesso_banco_dados)
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
                    //UtilsFoos.showToast(getApplication(), "Email enviado com sucesso!")
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