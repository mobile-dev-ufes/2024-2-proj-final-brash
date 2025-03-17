package com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel.ListarCartaoVM
import com.example.brash.databinding.GtcListarCartaoFrCriarCartaoBinding

/**
 * A DialogFragment that allows the user to create a new flashcard ("Cartão").
 * It provides input fields for the question and answer, and buttons for confirming or canceling the creation.
 *
 * @constructor Creates an instance of `CriarCartaoFrDialog`.
 */
class CriarCartaoFrDialog : DialogFragment() {

    private var _binding: GtcListarCartaoFrCriarCartaoBinding? = null
    private val binding get() = _binding!!

    private lateinit var listarCartaoVM: ListarCartaoVM

    /**
     * Inflates the layout for the fragment using ViewBinding.
     *
     * @param inflater The LayoutInflater object used to inflate the view.
     * @param container The container view that the fragment's UI should be attached to.
     * @param savedInstanceState A Bundle containing any saved instance state data.
     * @return The root view of the fragment.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflar o layout com ViewBinding
        _binding = GtcListarCartaoFrCriarCartaoBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Called when the view has been created. It sets up the ViewModel and click listeners.
     *
     * @param view The root view of the fragment's layout.
     * @param savedInstanceState A Bundle containing any saved instance state data.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Agora a ViewModel está sendo recuperada corretamente
        listarCartaoVM = ViewModelProvider(requireActivity())[ListarCartaoVM::class.java]

        setOnClickListeners()
    }

    /**
     * Adjusts the dialog's layout to make it match the parent width and wrap content in height.
     */
    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    /**
     * Set up observers for the ViewModel. Currently unused in this implementation.
     */
    private fun setObservers(){
    }

    /**
     * Sets up the click listeners for the fragment's buttons.
     * Handles canceling the creation or confirming the creation of a new flashcard.
     */
    private fun setOnClickListeners(){
        binding.ListarCartaoFrCriarCartaoButtonCancelar.setOnClickListener {
            dismiss()
        }

        // Create button: attempt to create a new flashcard with the given question and answer
        binding.ListarCartaoFrCriarCartaoButtonCriar.setOnClickListener {
            val pergunta = binding.ListarCartaoFrCriarCartaoInputPergunta.text.toString()
            val resposta = binding.ListarCartaoFrCriarCartaoInputResposta.text.toString()
            listarCartaoVM.criarCartao(pergunta, resposta, {
                dismiss()
            })
        }
    }

    /**
     * Cleans up resources and avoids memory leaks by setting the binding to null when the view is destroyed.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Evita vazamento de memória
    }
}
