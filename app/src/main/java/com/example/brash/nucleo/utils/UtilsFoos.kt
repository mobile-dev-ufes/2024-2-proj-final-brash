package com.example.brash.nucleo.utils

import android.app.Activity
import android.app.LocaleManager
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.widget.Toast
import com.example.brash.R
import java.util.Locale

/**
 * Utility class providing common helper functions for **nucleo** subsistem.
 */
class UtilsFoos {
    companion object{

        /**
         * Displays a toast message.
         *
         * @param context The application context.
         * @param message The message to be displayed.
         */
        fun showToast(context : Context, message : String){
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

        /**
         * Validates an email format.
         *
         * @param email The email to be validated.
         * @return `true` if the email format is valid, `false` otherwise.
         */
        fun isValidEmail(email: String): Boolean {
            val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()
            return email.matches(emailRegex)
        }

        /**
         * Generates a random email verification code.
         *
         * @param size The length of the generated code (default is 6).
         * @return A randomly generated verification code.
         */
        fun emailVerificationCodeGenerator(size: Int = 6): String {
            val validCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
            return (1..size)
                .map { validCharacters.random() }
                .joinToString("")
        }

        /**
         * Retrieves the currently selected language in the application.
         *
         * @param context The application context.
         * @return The language code (e.g., "en" for English, "pt" for Portuguese).
         */
        fun getSelectedLanguage(context: Context): String {
            val currentLocale: Locale? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                val locales = context.getSystemService(LocaleManager::class.java)?.applicationLocales
                if (locales != null && !locales.isEmpty) locales.get(0) else null
            } else {
                Locale.getDefault()
            }

            return currentLocale?.language ?: "pt" // Fallback para PT caso seja null
        }

        /**
         * Sets the application's locale to the specified language.
         *
         * @param context The application context.
         * @param languageCode The language code (e.g., "en" for English).
         */
        fun setLocale(context: Context, languageCode: String) {
            val locale = Locale(languageCode)
            Locale.setDefault(locale)

            val config = Configuration()
            config.setLocale(locale)

            context.resources.updateConfiguration(config, context.resources.displayMetrics)
        }

        /**
         * Retrieves the current locale language of the device.
         *
         * @param context The application context.
         * @return The language code (e.g., "en" for English).
         */
        fun getLocaleLanguage(context : Context) : String{
            val locale = context.resources.configuration.locales[0] // API 24+
            val languageCode = locale.language // Exemplo: "en"
            val countryCode = locale.country   // Exemplo: "US"
            return languageCode
        }

        /**
         * Changes the application's language.
         *
         * @param activity The current activity.
         * @param language The language code to be set.
         */
        fun changeLanguage(activity : Activity, language : String){
            UtilsFoos.setLocale(activity, language)
        }

        /**
         * Restarts the application.
         *
         * @param activity The current activity.
         */
        fun restartApp(activity: Activity) {
            val intent = activity.packageManager.getLaunchIntentForPackage(activity.packageName)
            intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            activity.finish()
            activity.startActivity(intent)
        }

    }



}

/**
 * Enum representing different user profile icons.
 *
 * @property drawableRes The resource ID of the icon.
 */
enum class IconeImagem(val drawableRes: Int) {
    PADRAO(R.drawable.perfil_conta),
    LUZ(R.drawable.perfil_luz),
    CARRO(R.drawable.perfil_carro),
    FELIZ(R.drawable.perfil_feliz);
}

/**
 * Enum representing different user profile icon colors.
 *
 * @property colorRes The resource ID of the color.
 */
enum class IconeCor(val colorRes: Int) {
    DEEP_PURPLE(R.color.deep_purple),
    SOFT_PINK(R.color.soft_pink),
    WHITE(R.color.white),
    HOT_PINK(R.color.hot_pink),
    LEAF_GREEN(R.color.leaf_green);
}

