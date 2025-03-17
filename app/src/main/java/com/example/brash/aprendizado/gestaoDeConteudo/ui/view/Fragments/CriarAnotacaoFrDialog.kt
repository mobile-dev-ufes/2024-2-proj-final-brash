package com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel.ListarAnotacaoVM
import com.example.brash.databinding.GtcListarAnotacaoFrCriarAnotacaoBinding

/**
 * A DialogFragment that allows the user to create a new annotation ("Anotação").
 * It provides input fields for the annotation name and text, and buttons for confirming or canceling the creation.
 *
 * @constructor Creates an instance of `CriarAnotacaoFrDialog`.
 */
class CriarAnotacaoFrDialog : DialogFragment() {

    // Binding to access layout views using ViewBinding
    private var _binding: GtcListarAnotacaoFrCriarAnotacaoBinding? = null
    private val binding get() = _binding!!

    // ViewModel to manage data related to annotations
    private lateinit var listarAnotacaoVM: ListarAnotacaoVM

    /**
     * Inflates the fragment's layout using ViewBinding and returns the root view.
     *
     * @param inflater The LayoutInflater object used to inflate the view.
     * @param container The container to attach the view to.
     * @param savedInstanceState The saved state of the fragment.
     * @return The root view of the fragment.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout using ViewBinding
        _binding = GtcListarAnotacaoFrCriarAnotacaoBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Called after the view is created. It initializes the ViewModel and sets the click listeners.
     *
     * @param view The root view of the fragment's layout.
     * @param savedInstanceState The saved instance state.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the ViewModel correctly
        listarAnotacaoVM = ViewModelProvider(requireActivity())[ListarAnotacaoVM::class.java]

        setOnClickListeners()
    }

    /**
     * Adjusts the dialog window size. The dialog will match the parent width and wrap content for height.
     */
    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    /**
     * Placeholder method for setting up observers for the ViewModel (not used in this implementation).
     */
    private fun setObservers(){
    }

    /**
     * Sets up the click listeners for the cancel and create buttons.
     * Handles actions when these buttons are clicked.
     */
    private fun setOnClickListeners(){
        // Cancel button: dismisses the dialog without doing anything
        binding.ListarAnotacaoFrCriarAnotacaoButtonCancelar.setOnClickListener {
            dismiss()
        }
        // Create button: creates a new annotation with the provided name and text
        binding.ListarAnotacaoFrCriarAnotacaoButtonCriar.setOnClickListener {
            //dismiss()
            //TODO:: Fazer verificação??, se for pode confirmar, requisitar isso ao HomeVM
            val annotationName = binding.ListarAnotacaoFrCriarAnotacaoInputNome.text.toString()
            val annotationText = binding.ListarAnotacaoFrCriarAnotacaoInputTexto.text.toString()

            // Call the ViewModel to create the annotation
            listarAnotacaoVM.criarAnotacao(annotationName, annotationText, {
                dismiss()
            })
        }
    }

    /**
     * Cleans up the binding to avoid memory leaks when the view is destroyed.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Avoid memory leaks
    }
}
