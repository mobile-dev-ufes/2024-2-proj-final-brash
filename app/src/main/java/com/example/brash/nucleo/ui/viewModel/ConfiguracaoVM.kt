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
import com.example.brash.nucleo.utils.Constants
import com.example.brash.nucleo.utils.MyPreferences
import kotlin.math.sign

class ConfiguracaoVM(application: Application) : AndroidViewModel(application) {

    private val auth = FirebaseAuth.getInstance()

    fun signOut(){
        auth.signOut()
    }
}