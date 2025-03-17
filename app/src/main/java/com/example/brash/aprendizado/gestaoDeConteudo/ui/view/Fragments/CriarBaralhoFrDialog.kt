package com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel.HomeVM
import com.example.brash.databinding.GtcHomeFrCriarBaralhoBinding

/**
 * A DialogFragment that allows the user to create a new flashcard deck ("Baralho").
 * It provides input fields for the deck title and description, and buttons for confirming or canceling the creation.
 *
 * @constructor Creates an instance of `CriarBaralhoFrDialog`.
 */
class CriarBaralhoFrDialog : DialogFragment() {

    // Binding to access the layout views using ViewBinding
    private var _binding: GtcHomeFrCriarBaralhoBinding? = null
    private val binding get() = _binding!!

    // ViewModel for managing data related to home
    lateinit var homeVM: HomeVM

    /**
     * Inflates the fragment's layout using ViewBinding and returns the root view.
     *
     * @param inflater The LayoutInflater object used to inflate the view.
     * @param container The container that the fragment's UI should be attached to.
     * @param savedInstanceState A Bundle containing any saved instance state data.
     * @return The root view of the fragment.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout using ViewBinding
        _binding = GtcHomeFrCriarBaralhoBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Called after the view is created. Sets up the ViewModel and click listeners.
     *
     * @param view The root view of the fragment's layout.
     * @param savedInstanceState A Bundle containing any saved instance state data.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve the ViewModel correctly
        homeVM = ViewModelProvider(requireActivity())[HomeVM::class.java]

        setOnClickListeners()
    }

    /**
     * Adjusts the dialog's layout parameters. Makes the dialog match the parent width
     * and wrap content in height when the dialog starts.
     */
    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    /**
     * Placeholder method for setting observers for the ViewModel (not used in this implementation).
     */
    private fun setObservers(){
    }

    /**
     * Sets up the click listeners for the cancel and create buttons in the dialog.
     * Handles the actions when the buttons are clicked.
     */
    private fun setOnClickListeners(){
        // Cancel button: dismiss the dialog without performing any action
        binding.HomeFrCriarBaralhoButtonCancelar.setOnClickListener {
            dismiss()
        }
        // Create button: create a new deck with the provided title and description
        binding.HomeFrCriarBaralhoButtonCriar.setOnClickListener {
            //TODO:: Fazer verificação de se eh nome único, se for pode confirmar, requisitar isso ao HomeVM
            val deckName = binding.HomeFrCriarBaralhoInputTitulo.text.toString()
            val deckDescription = binding.HomeFrCriarBaralhoInputDescricao.text.toString()

            // Call the ViewModel to create the deck
            homeVM.criarBaralho(deckName, deckDescription, {
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
