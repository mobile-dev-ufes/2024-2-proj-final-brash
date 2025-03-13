package com.example.brash.nucleo.utils

import android.app.Activity
import android.app.LocaleManager
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.LocaleList
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import com.example.brash.R
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

        fun setLocale(context: Context, languageCode: String) {
            val locale = Locale(languageCode)
            Locale.setDefault(locale)

            val config = Configuration()
            config.setLocale(locale)

            context.resources.updateConfiguration(config, context.resources.displayMetrics)
        }

        fun getLocaleLanguage(context : Context) : String{
            val locale = context.resources.configuration.locales[0] // API 24+
            val languageCode = locale.language // Exemplo: "en"
            val countryCode = locale.country   // Exemplo: "US"
            return languageCode
        }

        fun changeLanguage(activity : Activity, language : String){
            UtilsFoos.setLocale(activity, language)
        }

        fun restartApp(activity: Activity) {
            val intent = activity.packageManager.getLaunchIntentForPackage(activity.packageName)
            intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            activity.finish()
            activity.startActivity(intent)
        }

    }



}

enum class IconeImagem(val drawableRes: Int) {
    PADRAO(R.drawable.perfil_conta),
    LUZ(R.drawable.perfil_luz),
    CARRO(R.drawable.perfil_carro),
    FELIZ(R.drawable.perfil_feliz);
}
enum class IconeCor(val colorRes: Int) {
    DEEP_PURPLE(R.color.deep_purple),
    SOFT_PINK(R.color.soft_pink),
    WHITE(R.color.white),
    HOT_PINK(R.color.hot_pink),
    LEAF_GREEN(R.color.leaf_green);
}

