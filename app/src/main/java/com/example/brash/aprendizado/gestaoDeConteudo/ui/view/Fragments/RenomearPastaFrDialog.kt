package com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Pasta
import com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel.HomeVM
import com.example.brash.databinding.GtcHomeFrAcoesAdicionaisBinding
import com.example.brash.databinding.GtcHomeFrRenomearPastaBinding
import com.example.brash.databinding.GtcHomeFrVisualizarBaralhoBinding

class RenomearPastaFrDialog() : DialogFragment() {

    private var _binding: GtcHomeFrRenomearPastaBinding? = null
    private val binding get() = _binding!!
    lateinit var homeVM: HomeVM


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflar o layout com ViewBinding
        _binding = GtcHomeFrRenomearPastaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Agora a ViewModel está sendo recuperada corretamente
        homeVM = ViewModelProvider(requireActivity())[HomeVM::class.java]
        Log.d("HomeDialogs", "homeVM iniciado")

        homeVM.pastaEmFoco.value?.let {
            binding.HomeFrRenomearPastaInput.setText(it.nome)
        }

        // Configurar os observadores para LiveData
        setObservers()
        setOnClickListeners()

        Log.d("HomeDialogs", "VisualizarBaralho iniciado")
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    private fun setObservers(){

    }

    private fun setOnClickListeners(){
        binding.HomeFrRenomearPastaButtonCancelar.setOnClickListener {
            dismiss()
        }
        binding.HomeFrRenomearPastaButtonConfirmar.setOnClickListener {
            //TODO:: Fazer verificação de se eh nome único, se for pode confirmar
            dismiss()
            Toast.makeText(requireContext(), "Pasta renomeada", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Evita vazamento de memória
    }
}
