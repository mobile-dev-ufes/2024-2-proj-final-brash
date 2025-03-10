package com.example.brash.nucleo.utils

import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.LocaleList
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import java.util.Locale
import kotlin.random.Random

class UtilsFoos {
    companion object{

        fun showToast(context : Context, message : String){
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

        fun isValidEmail(email: String): Boolean {
            val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()
            return email.matches(emailRegex)
        }

        fun emailVerificationCodeGenerator(size: Int = 6): String {
            val validCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
            return (1..size)
                .map { validCharacters.random() }
                .joinToString("")
        }


        fun getSelectedLanguage(context: Context): String {
            val currentLocale: Locale? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                val locales = context.getSystemService(LocaleManager::class.java)?.applicationLocales
                if (locales != null && !locales.isEmpty) locales.get(0) else null
            } else {
                Locale.getDefault()
            }

            return currentLocale?.language ?: "pt" // Fallback para PT caso seja null
        }

    }



}