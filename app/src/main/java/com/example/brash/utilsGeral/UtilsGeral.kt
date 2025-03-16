package com.example.brash.utilsGeral

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder

import com.example.brash.R
import com.example.brash.nucleo.utils.UtilsFoos

class UtilsGeral {
    companion object{
        fun showAlertDialog(context: Context, message: String, onSucess: () -> Unit) {
            val builder = MaterialAlertDialogBuilder(context)
            builder.setMessage(message)

            builder.setNegativeButton(context.getString(R.string.cancelar)) { dialog, _ ->
                // Ação ao clicar no botão negativo
                dialog.dismiss()
            }
            builder.setPositiveButton(context.getString(R.string.confirmar_exclusao)) { dialog, _ ->
                // Ação ao clicar no botão positivo
                onSucess()

                dialog.dismiss()
            }
            val dialog = builder.create()
            dialog.show()
            // 🔥 Muda a cor do botão positivo para vermelho
            dialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE)?.setTextColor(ContextCompat.getColor(context, R.color.bright_red))
        } //getResources().getColor(R.color.bright_red)

        fun showToast(context : Context, message : String){
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

        fun isInternetAvailable(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            // Verificar a conectividade de rede para Android 6.0 (API 23) ou superior
            val activeNetwork: Network? = connectivityManager.activeNetwork
            val networkCapabilities: NetworkCapabilities? =
                connectivityManager.getNetworkCapabilities(activeNetwork)

            if(networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == false){
                UtilsFoos.showToast(context, context.getString(R.string.erro_de_conexao))
                return false
            }
            else{
                return true
            }
        }
    }
}