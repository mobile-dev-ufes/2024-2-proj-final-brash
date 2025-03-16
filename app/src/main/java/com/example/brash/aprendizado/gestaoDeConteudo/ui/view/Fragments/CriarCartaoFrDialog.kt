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


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflar o layout com ViewBinding
        _binding = GtcListarCartaoFrCriarCartaoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Agora a ViewModel está sendo recuperada corretamente
        listarCartaoVM = ViewModelProvider(requireActivity())[ListarCartaoVM::class.java]

        setOnClickListeners()
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    private fun setObservers(){
    }
    private fun setOnClickListeners(){
        binding.ListarCartaoFrCriarCartaoButtonCancelar.setOnClickListener {
            dismiss()
        }
        binding.ListarCartaoFrCriarCartaoButtonCriar.setOnClickListener {
            val pergunta = binding.ListarCartaoFrCriarCartaoInputPergunta.text.toString()
            val resposta = binding.ListarCartaoFrCriarCartaoInputResposta.text.toString()
            listarCartaoVM.criarCartao(pergunta, resposta, {
                dismiss()
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Evita vazamento de memória
    }
}
