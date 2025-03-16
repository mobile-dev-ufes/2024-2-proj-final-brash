package com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel.HomeVM
import com.example.brash.databinding.GtcHomeFrCriarPastaBinding

/**
 * A DialogFragment that allows the user to create a new folder ("Pasta").
 * It provides an input field for the folder's name and buttons for confirming or canceling the creation.
 *
 * @constructor Creates an instance of `CriarPastaFrDialog`.
 */
class CriarPastaFrDialog : DialogFragment() {

    private var _binding: GtcHomeFrCriarPastaBinding? = null
    private val binding get() = _binding!!

    lateinit var homeVM: HomeVM

    /**
     * Inflates the layout for the fragment and initializes the view binding.
     *
     * @param inflater The LayoutInflater object used to inflate the layout.
     * @param container The parent view that the fragment's UI will be attached to.
     * @param savedInstanceState A Bundle containing data saved during a previous instance of the fragment.
     * @return The root view of the fragment's layout.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflar o layout com ViewBinding
        _binding = GtcHomeFrCriarPastaBinding.inflate(inflater, container, false)
        return binding.root
    }
    /**
     * Initializes the ViewModel and sets up the onClick listeners for the dialog.
     *
     * @param view The root view of the fragment.
     * @param savedInstanceState A Bundle containing data saved during a previous instance of the fragment.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Agora a ViewModel está sendo recuperada corretamente
        homeVM = ViewModelProvider(requireActivity())[HomeVM::class.java]
        setOnClickListeners()
    }
    /**
     * Adjusts the dialog size when the fragment starts.
     */
    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }
    /**
     * Placeholder for any future LiveData observers (currently not in use).
     * This method can be expanded to observe changes in LiveData if needed in the future.
     */
    private fun setObservers(){

    }
    /**
     * Sets the click listeners for the "Cancel" and "Create" buttons.
     * The "Create" button triggers the folder creation logic.
     */
    private fun setOnClickListeners(){
        binding.HomeFrCriarPastaButtonCancelar.setOnClickListener {
            dismiss()
        }
        binding.HomeFrCriarPastaButtonCriar.setOnClickListener {

            val nome = binding.HomeFrCriarPastaInput.text.toString()
            homeVM.criarPasta(nome, {
                dismiss()
            })
        }

    }
    /**
     * Cleans up the view binding to prevent memory leaks when the view is destroyed.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Evita vazamento de memória
    }
}
