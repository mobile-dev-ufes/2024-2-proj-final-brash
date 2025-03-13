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

import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.RevisaoCartaoAC
import com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel.HomeVM
import com.example.brash.databinding.GtcHomeFrAcoesBaralhoBinding
import com.example.brash.utilsGeral.UtilsGeral

class AcoesBaralhoFrDialog() : DialogFragment() {

    private var _binding: GtcHomeFrAcoesBaralhoBinding? = null
    private val binding get() = _binding!!


    lateinit var homeVM: HomeVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflar o layout com ViewBinding
        _binding = GtcHomeFrAcoesBaralhoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Agora a ViewModel está sendo recuperada corretamente
        homeVM = ViewModelProvider(requireActivity()).get(HomeVM::class.java)

        setOnClickListeners()

    }

    private fun setObservers(){

    }

    private fun setOnClickListeners(){

        binding.HomeFrAcoesBaralhoTextViewVisualizarBaralho.setOnClickListener {
            dismiss()
            Log.d("HomeDialogs", "Tentando mostrar o diálogo visualizarBaralho")
            VisualizarBaralhoFrDialog().show(parentFragmentManager, "VisualizarBaralhoDialog")
        }
        binding.HomeFrAcoesBaralhoTextViewVisualizarCartoes.setOnClickListener {
            dismiss()
            Toast.makeText(requireContext(), "Visualizar Cartões", Toast.LENGTH_SHORT).show()
        }
        binding.HomeFrAcoesBaralhoTextViewVisualizarAnotacoes.setOnClickListener {
            dismiss()
            Toast.makeText(requireContext(), "Visualizar Anotações", Toast.LENGTH_SHORT).show()
        }
        binding.HomeFrAcoesBaralhoTextViewVisualizarRelatorio.setOnClickListener {
            dismiss()
            Toast.makeText(requireContext(), "Visualizar Relatório", Toast.LENGTH_SHORT).show()
        }
        binding.HomeFrAcoesBaralhoTextViewRevisarBaralho.setOnClickListener {
            dismiss()
            intentToRevisaoActivity()
        }
        binding.HomeFrAcoesBaralhoTextViewMoverBaralho.setOnClickListener {
            dismiss()
            //Toast.makeText(requireContext(), "Mover Baralho", Toast.LENGTH_SHORT).show()
            if (!activity?.isFinishing!! && !activity?.isDestroyed!!) {
                Log.d("ListaPastaAdapter", "Tentando mostrar o diálogo")
                MoverBaralhoFrDialog().show(parentFragmentManager, "MoverBaralhoDialog")
            }
        }
        binding.HomeFrAcoesBaralhoTextViewExcluirBaralho.setOnClickListener {
            dismiss()
            
            UtilsGeral.showAlertDialog(requireContext(),"Deseja realmente excluir esse Baralho??",{
                Toast.makeText(requireContext(), "Excluir Baralho", Toast.LENGTH_SHORT).show()
            })
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Evita vazamento de memória
    }

    private fun intentToRevisaoActivity(){
        val intent = Intent(requireContext(), RevisaoCartaoAC::class.java)
        Log.d("HomeDialogs", "Indo para a revisão de baralho")
        startActivity(intent)
    }
}
