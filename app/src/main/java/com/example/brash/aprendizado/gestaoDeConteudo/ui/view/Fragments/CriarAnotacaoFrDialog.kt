package com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel.HomeVM
import com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel.ListarAnotacaoVM
import com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel.ListarCartaoVM
import com.example.brash.databinding.GtcHomeFrAcoesAdicionaisBinding
import com.example.brash.databinding.GtcHomeFrCriarBaralhoBinding
import com.example.brash.databinding.GtcHomeFrCriarPastaBinding
import com.example.brash.databinding.GtcListarAnotacaoFrCriarAnotacaoBinding
import com.example.brash.databinding.GtcListarCartaoFrCriarCartaoBinding

class CriarAnotacaoFrDialog : DialogFragment() {

    private var _binding: GtcListarAnotacaoFrCriarAnotacaoBinding? = null
    private val binding get() = _binding!!

    private lateinit var listarAnotacaoVM: ListarAnotacaoVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflar o layout com ViewBinding
        _binding = GtcListarAnotacaoFrCriarAnotacaoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Agora a ViewModel está sendo recuperada corretamente
        listarAnotacaoVM = ViewModelProvider(requireActivity())[ListarAnotacaoVM::class.java]

        setOnClickListeners()
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    private fun setObservers(){
    }

    private fun setOnClickListeners(){
        binding.ListarAnotacaoFrCriarAnotacaoButtonCancelar.setOnClickListener {
            dismiss()
        }
        binding.ListarAnotacaoFrCriarAnotacaoButtonCriar.setOnClickListener {
            //dismiss()
            //TODO:: Fazer verificação??, se for pode confirmar, requisitar isso ao HomeVM
            val annotationName = binding.ListarAnotacaoFrCriarAnotacaoInputNome.text.toString()
            val annotationText = binding.ListarAnotacaoFrCriarAnotacaoInputTexto.text.toString()
            listarAnotacaoVM.criarAnotacao(annotationName, annotationText) {
                dismiss()
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Evita vazamento de memória
    }
}
