package com.example.brash.nucleo.ui.view.Fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.brash.databinding.AlertDialogFrExclusaoBinding

/**
 * A DialogFragment that displays a confirmation dialog with a custom message.
 * The dialog provides options for the user to cancel or confirm the action.
 * This dialog is typically used for actions like deletion confirmation.
 *
 * @param message The message to be displayed in the dialog.
 */
class AlertDialogFr(private val message: String) : DialogFragment() {

    private var _binding: AlertDialogFrExclusaoBinding? = null
    private val binding get() = _binding!!

    /**
     * Creates the dialog and inflates the layout for the alert dialog.
     * Sets up the click listeners and any necessary observers.
     *
     * @param savedInstanceState The saved instance state.
     * @return The created dialog instance.
     */
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

    /**
     * Initializes the alert dialog's content with the provided message.
     */
    private fun initAlertDialog(){
        binding.AlertDialogFrExclusaoTextViewMensagem.text = message
    }


    /**
     * Sets up observers for the dialog, if needed.
     * Currently, no observers are set.
     */
    private fun setObservers(){

    }

    /**
     * Sets the click listeners for the buttons in the dialog.
     */
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

    /**
     * Cleans up the binding when the fragment's view is destroyed to prevent memory leaks.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Evita vazamento de memória
    }

    /**
     * Interface for communicating the confirmation action with the Activity or parent context.
     */
    interface OnConfirmListener {
        /**
         * Called when the user confirms the action (e.g., deletion).
         */
        fun onConfirmAlertDialog()
    }

    private var listener: OnConfirmListener? = null

    /**
     * Ensures that the Activity hosting the DialogFragment implements the OnConfirmListener interface.
     *
     * @param context The context to attach to the fragment.
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnConfirmListener) {
            listener = context
        } else {
            throw ClassCastException("$context must implement OnConfirmListener")
        }
    }
}
