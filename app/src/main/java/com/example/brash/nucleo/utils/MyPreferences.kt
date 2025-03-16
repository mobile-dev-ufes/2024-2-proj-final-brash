package com.example.brash.nucleo.utils

import android.content.Context
import java.util.Locale


/**
 * Retrieves the current device's locale language.
 *
 * @return The language code of the device (e.g., "en" for English).
 */
fun getDeviceLocale(): String {
    return Locale.getDefault().language
}

/**
 * Saves the selected language preference in shared preferences.
 *
 * @param context The application context.
 * @param languageCode The language code to be saved (e.g., "en" for English).
 */
fun saveLanguagePreference(context: Context, languageCode: String) {
    val prefs = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
    prefs.edit().putString("selected_language", languageCode).apply()
}

/**
 * Retrieves the saved language preference from shared preferences.
 * If no preference is saved, it returns the device's default language.
 *
 * @param context The application context.
 * @return The saved language code, or the device's default language if not saved.
 */
fun getSavedLanguage(context: Context): String {
    val prefs = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
    return prefs.getString("selected_language", getDeviceLocale()) ?: getDeviceLocale()
}

/**
 * Sets the application's locale to the specified language.
 *
 * @param context The application context.
 * @param languageCode The language code to be set (e.g., "en" for English).
 */
fun setAppLocale(context: Context, languageCode: String) {
    val locale = Locale(languageCode)
    Locale.setDefault(locale)
    val config = context.resources.configuration
    config.setLocale(locale)
    context.createConfigurationContext(config)

    val resources = context.resources
    resources.updateConfiguration(config, resources.displayMetrics)
}
