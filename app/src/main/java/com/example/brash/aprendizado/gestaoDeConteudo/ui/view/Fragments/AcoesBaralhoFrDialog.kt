package com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Baralho
import com.example.brash.databinding.GtcHomeFrAcoesAdicionaisBinding
import com.example.brash.databinding.GtcHomeFrAcoesBaralhoBinding
import com.example.brash.nucleo.ui.view.Fragments.AlertDialogFr
import com.example.brash.utilsGeral.UtilsGeral

class AcoesBaralhoFrDialog(val baralho: Baralho) : DialogFragment() {

    private var _binding: GtcHomeFrAcoesBaralhoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())

        // Inflar o layout com ViewBinding
        _binding = GtcHomeFrAcoesBaralhoBinding.inflate(layoutInflater)
        builder.setView(binding.root)

        setOnClickListeners()
        setObservers()
        return builder.create()
    }

    private fun setObservers(){

    }

    private fun setOnClickListeners(){

        binding.HomeFrAcoesBaralhoTextViewVisualizarBaralho.setOnClickListener {
            dismiss()
            Toast.makeText(requireContext(), "Visualizar Baralho", Toast.LENGTH_SHORT).show()
            VisualizarBaralhoFrDialog(baralho).show(parentFragmentManager, "OpcaoDialog")
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
        binding.HomeFrAcoesBaralhoTextViewEditarBaralho.setOnClickListener {
            dismiss()
            Toast.makeText(requireContext(), "Editar Baralho", Toast.LENGTH_SHORT).show()
        }
        binding.HomeFrAcoesBaralhoTextViewMoverBaralho.setOnClickListener {
            dismiss()
            Toast.makeText(requireContext(), "Mover Baralho", Toast.LENGTH_SHORT).show()
            MoverBaralhoFrDialog(baralho).show(parentFragmentManager, "OpcaoDialog")
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
}
