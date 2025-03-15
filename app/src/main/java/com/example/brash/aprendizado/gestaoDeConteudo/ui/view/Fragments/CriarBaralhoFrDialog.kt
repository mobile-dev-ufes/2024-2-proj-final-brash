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
import com.example.brash.databinding.GtcHomeFrAcoesAdicionaisBinding
import com.example.brash.databinding.GtcHomeFrCriarBaralhoBinding
import com.example.brash.databinding.GtcHomeFrCriarPastaBinding

class CriarBaralhoFrDialog : DialogFragment() {

    private var _binding: GtcHomeFrCriarBaralhoBinding? = null
    private val binding get() = _binding!!

    lateinit var homeVM: HomeVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflar o layout com ViewBinding
        _binding = GtcHomeFrCriarBaralhoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Agora a ViewModel está sendo recuperada corretamente
        homeVM = ViewModelProvider(requireActivity())[HomeVM::class.java]

        setOnClickListeners()
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    private fun setObservers(){
    }

    private fun setOnClickListeners(){
        binding.HomeFrCriarBaralhoButtonCancelar.setOnClickListener {
            dismiss()
        }
        binding.HomeFrCriarBaralhoButtonCriar.setOnClickListener {
            //TODO:: Fazer verificação de se eh nome único, se for pode confirmar, requisitar isso ao HomeVM
            val deckName = binding.HomeFrCriarBaralhoInputTitulo.text.toString()
            val deckDescription = binding.HomeFrCriarBaralhoInputDescricao.text.toString()
            homeVM.criarBaralho(deckName, deckDescription) {
                dismiss()
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Evita vazamento de memória
    }
}
