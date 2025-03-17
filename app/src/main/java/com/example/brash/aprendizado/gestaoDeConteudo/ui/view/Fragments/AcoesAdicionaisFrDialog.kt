package com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.ListarBaralhoPublicoAC
import com.example.brash.databinding.GtcHomeFrAcoesAdicionaisBinding

/**
 * A DialogFragment that provides additional actions for the user, such as creating a deck, creating a folder,
 * or browsing public decks. The actions are displayed as options within the dialog.
 *
 * @constructor Creates an instance of `AcoesAdicionaisFrDialog`.
 */
class AcoesAdicionaisFrDialog : DialogFragment() {

    // ViewBinding to interact with the views in the layout
    private var _binding: GtcHomeFrAcoesAdicionaisBinding? = null
    private val binding get() = _binding!!

    /**
     * Inflates the fragment's layout using ViewBinding and returns the root view.
     *
     * @param inflater The LayoutInflater used to inflate the layout.
     * @param container The container to attach the view to.
     * @param savedInstanceState The saved instance state.
     * @return The root view of the fragment.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout using ViewBinding
        _binding = GtcHomeFrAcoesAdicionaisBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Called after the view is created. Sets up the click listeners for the various actions.
     *
     * @param view The root view of the fragment's layout.
     * @param savedInstanceState The saved instance state.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
    }

    /**
     * Placeholder method for setting observers for the ViewModel (currently unused).
     */
    private fun setObservers(){

    }

    /**
     * Sets up the click listeners for the actions provided in the dialog.
     * Each option performs a specific action, such as creating a deck, creating a folder,
     * or browsing public decks.
     */
    private fun setOnClickListeners(){

        binding.HomeFrOpcoesAdicionaisTextViewCriarBaralho.setOnClickListener {
            dismiss()
            CriarBaralhoFrDialog().show(parentFragmentManager, "CriarBaralhoDialog")
        }

        binding.HomeFrOpcoesAdicionaisTextViewCriarPasta.setOnClickListener {
            dismiss()
            CriarPastaFrDialog().show(parentFragmentManager, "CriarPastaDialog")
        }

        binding.HomeFrOpcoesAdicionaisTextViewProcurarBaralhosPublicos.setOnClickListener {
            dismiss()
            intentToListarBaralhoPublicoAc()
        }
    }

    // When the "Create Deck" option is clicked, dismiss the current dialog and show the "Create Deck" dialog
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Avoid memory leaks
    }

    // When the "Create Folder" option is clicked, dismiss the current dialog and show the "Create Folder" dialog
    private fun intentToListarBaralhoPublicoAc(){
        val intent = Intent(requireContext(), ListarBaralhoPublicoAC::class.java)
        startActivity(intent)
    }
}
