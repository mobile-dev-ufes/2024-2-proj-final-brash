package com.example.brash.nucleo.utils

import android.content.Context
import android.widget.Toast
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


    }

}