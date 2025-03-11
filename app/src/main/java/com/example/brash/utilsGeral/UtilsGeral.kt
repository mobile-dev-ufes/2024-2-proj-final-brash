package com.example.brash.utilsGeral

import android.content.Context
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder

import com.example.brash.R

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
    }
}