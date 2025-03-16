package com.example.brash.utilsGeral

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder

import com.example.brash.R
import com.example.brash.nucleo.utils.UtilsFoos

/**
 * Utility class for Brash, containing helper methods for displaying alerts, toasts, and checking connectivity.
 */
class UtilsGeral {
    companion object{
        /**
         * Displays an alert dialog with confirmation and cancellation options.
         *
         * @param context Application context for displaying the dialog.
         * @param message Message to be displayed in the alert.
         * @param onSucess Callback triggered when the user confirms the action.
         */
        fun showAlertDialog(context: Context, message: String, onSucess: () -> Unit) {
            val builder = MaterialAlertDialogBuilder(context)
            builder.setMessage(message)

            builder.setNegativeButton(context.getString(R.string.cancelar)) { dialog, _ ->
                dialog.dismiss()
            }
            builder.setPositiveButton(context.getString(R.string.confirmar_exclusao)) { dialog, _ ->
                onSucess()

                dialog.dismiss()
            }
            val dialog = builder.create()
            dialog.show()
            // Change the positive button color to red
            dialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE)?.setTextColor(ContextCompat.getColor(context, R.color.bright_red))
        }

        /**
         * Displays a toast message.
         *
         * @param context Application context for displaying the toast.
         * @param message Message to be displayed in the toast.
         */
        fun showToast(context : Context, message : String){
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

        /**
         * Checks if internet connectivity is available.
         *
         * @param context Application context for accessing the connectivity service.
         * @return `true` if the internet is available, `false` otherwise.
         */
        fun isInternetAvailable(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            // Get the active network and its capabilities
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