package com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Baralho
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Pasta
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.ListarCartaoAC
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.adapter.ListaPastaAdapter
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.listener.OnPastaListener
import com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel.HomeVM
import com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel.ListarCartaoVM
import com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel.ListarDicaVM
import com.example.brash.databinding.GtcHomeFrAcoesAdicionaisBinding
import com.example.brash.databinding.GtcHomeFrAcoesBaralhoBinding
import com.example.brash.databinding.GtcHomeFrMoverBaralhoBinding
import com.example.brash.databinding.GtcListarCartaoFrAcoesCartaoBinding
import com.example.brash.databinding.GtcListarDicaFrAcoesDicaBinding
import com.example.brash.nucleo.ui.view.Fragments.AlertDialogFr
import com.example.brash.nucleo.ui.view.PerfilAC
import com.example.brash.utilsGeral.UtilsGeral

class AcoesDicaFrDialog() : DialogFragment() {

    private var _binding: GtcListarDicaFrAcoesDicaBinding? = null
    private val binding get() = _binding!!


    private lateinit var listarDicaVM: ListarDicaVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflar o layout com ViewBinding
        _binding = GtcListarDicaFrAcoesDicaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Agora a ViewModel está sendo recuperada corretamente
        listarDicaVM = ViewModelProvider(requireActivity())[ListarDicaVM::class.java]

        setOnClickListeners()

    }

    private fun setObservers(){

    }

    private fun setOnClickListeners(){

        binding.ListarDicaFrAcoesDicaTextViewVisualizarDica.setOnClickListener {
            dismiss()
            if (!activity?.isFinishing!! && !activity?.isDestroyed!!) {
                Log.d("HomeDialogs", "Tentando mostrar o diálogo visualizarDica")
                VisualizarDicaFrDialog().show(parentFragmentManager, "VisualizarDicaDialog")
            }
        }
        binding.ListarDicaFrAcoesDicaTextViewExcluirDica.setOnClickListener {
            UtilsGeral.showAlertDialog(requireContext(),"Deseja realmente excluir essa Dica??",{
                listarDicaVM.dicaEmFoco.value?.let { dica ->
                    listarDicaVM.excluirDica(dica){
                        dismiss()
                    }
                } ?: run {
                    //Toast.makeText(context, "Nenhuma Dica selecionada", Toast.LENGTH_SHORT).show()
                    dismiss()
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Evita vazamento de memória
    }
}
