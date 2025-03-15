package com.example.brash.nucleo.ui.view.Fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.example.brash.R
import com.example.brash.databinding.AlertDialogFrExclusaoBinding

class AlertDialogFr(private val message: String) : DialogFragment() {

    private var _binding: AlertDialogFrExclusaoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())

        // Inflar o layout com ViewBinding
        _binding = AlertDialogFrExclusaoBinding.inflate(layoutInflater)

        builder.setView(binding.root)

        initAlertDialog()

        setOnClickListeners()
        setObservers()
        return builder.create()
    }

    private fun initAlertDialog(){
        binding.AlertDialogFrExclusaoTextViewMensagem.text = message
    }

    private fun setObservers(){

    }

    private fun setOnClickListeners(){

        binding.AlertDialogFrExclusaoButtonCancelar.setOnClickListener {
            dismiss()
            //Toast.makeText(requireContext(), "Cancelar Exclusao", Toast.LENGTH_SHORT).show()
        }

        binding.AlertDialogFrExclusaoButtonConfirmar.setOnClickListener {
            dismiss()
            listener?.onConfirmAlertDialog()
            //Toast.makeText(requireContext(), "Confirmar Exclusao", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Evita vazamento de memória
    }

    // Interface para comunicação com a Activity
    interface OnConfirmListener {
        fun onConfirmAlertDialog()
    }

    private var listener: OnConfirmListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Certifique-se de que a Activity implementa a interface
        if (context is OnConfirmListener) {
            listener = context
        } else {
            throw ClassCastException("$context must implement OnConfirmListener")
        }
    }
}
