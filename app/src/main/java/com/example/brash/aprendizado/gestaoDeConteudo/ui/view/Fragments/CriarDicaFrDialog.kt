package com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel.ListarDicaVM
import com.example.brash.databinding.GtcListarDicaFrCriarDicaBinding

/**
 * A DialogFragment that allows the user to create a new hint ("Dica").
 * It provides an input field for the hint text and buttons for confirming or canceling the creation.
 *
 * @constructor Creates an instance of `CriarDicaFrDialog`.
 */
class CriarDicaFrDialog : DialogFragment() {

    // ViewBinding for the layout associated with this fragment
    private var _binding: GtcListarDicaFrCriarDicaBinding? = null
    private val binding get() = _binding!!

    // ViewModel used to manage the logic of the hint creation
    private lateinit var listarDicaVM: ListarDicaVM

    /**
     * Called to create and inflate the fragment's layout.
     *
     * @param inflater The LayoutInflater object that can be used to inflate views in the fragment.
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     * @return The root view of the fragment's layout.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout with ViewBinding
        _binding = GtcListarDicaFrCriarDicaBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Called after the fragment's view has been created.
     * Initializes the ViewModel and sets up click listeners for buttons.
     *
     * @param view The view returned by `onCreateView()`.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the ViewModel
        listarDicaVM = ViewModelProvider(requireActivity())[ListarDicaVM::class.java]

        setOnClickListeners()
    }

    /**
     * Called when the dialog starts.
     * This method adjusts the layout of the dialog to match the screen width.
     */
    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    /**
     * Sets observers for LiveData (if necessary, this method can be extended).
     */
    private fun setObservers(){
    }

    /**
     * Sets the click listeners for the "Cancel" and "Create" buttons.
     * The "Cancel" button dismisses the dialog, while the "Create" button calls
     * the ViewModel to create a new hint with the provided text.
     */
    private fun setOnClickListeners(){
        binding.ListarDicaFrCriarDicaButtonCancelar.setOnClickListener {
            dismiss()
        }
        binding.ListarDicaFrCriarDicaButtonCriar.setOnClickListener {
            val hintText = binding.ListarDicaFrCriarDicaInputTexto.text.toString()
            listarDicaVM.criarDica(hintText, {
                dismiss()
            })

        }

    }

    /**
     * Called when the fragment's view is destroyed.
     * This is to prevent memory leaks by clearing the binding reference.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Avoid memory leaks
    }
}
