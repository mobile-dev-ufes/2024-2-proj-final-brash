package com.example.brash.nucleo.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.brash.utilsGeral.Constants

class MyPreferences(context: Context) {

    private val sp: SharedPreferences =
        context.getSharedPreferences(Constants.CHAVE_ACESSO_STR, Context.MODE_PRIVATE)

    fun setString(key: String, str: String) {
        sp.edit().putString(key, str).apply()
    }

    fun getString(key: String): String {
        return sp.getString(key, "") ?: ""
    }

}
