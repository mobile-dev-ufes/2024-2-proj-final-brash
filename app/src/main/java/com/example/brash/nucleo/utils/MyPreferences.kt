package com.example.brash.nucleo.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.brash.utilsGeral.Constants
import java.util.Locale



fun getDeviceLocale(): String {
    return Locale.getDefault().language
}


fun saveLanguagePreference(context: Context, languageCode: String) {
    val prefs = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
    prefs.edit().putString("selected_language", languageCode).apply()
}

fun getSavedLanguage(context: Context): String {
    val prefs = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
    return prefs.getString("selected_language", getDeviceLocale()) ?: getDeviceLocale()
}

fun setAppLocale(context: Context, languageCode: String) {
    val locale = Locale(languageCode)
    Locale.setDefault(locale)
    val config = context.resources.configuration
    config.setLocale(locale)
    context.createConfigurationContext(config)

    val resources = context.resources
    resources.updateConfiguration(config, resources.displayMetrics)
}
