package com.example.brash.nucleo.ui.viewModel

import android.app.Application
import android.app.LocaleManager
import android.content.res.Configuration
import android.os.Build
import android.os.LocaleList
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.auth.FirebaseAuth
import java.util.Locale


class ConfiguracaoVM(application: Application) : AndroidViewModel(application) {

    private val auth = FirebaseAuth.getInstance()

    fun signOut(){
        auth.signOut()
    }

    fun trocarIdioma(languageCode: String, onSucess: () -> Unit){

        //TODO::CONSERTAR AQUI
        onSucess()

    }
}