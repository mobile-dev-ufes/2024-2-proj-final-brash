package com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Baralho
import com.example.brash.databinding.GtcHomeFrMoverBaralhoBinding
import com.example.brash.databinding.GtcListarBaralhoPublicoAcBinding
import com.example.brash.databinding.GtcListarBaralhoPublicoFrVisualizarBaralhoBinding

class VisualizarBaralhoPublicoFrDialog(val baralho: Baralho) : DialogFragment() {

    private var _binding: GtcListarBaralhoPublicoFrVisualizarBaralhoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())

        // Inflar o layout com ViewBinding
        _binding = GtcListarBaralhoPublicoFrVisualizarBaralhoBinding.inflate(layoutInflater)
        builder.setView(binding.root)

        binding.ListarBaralhoPublicoFrTextViewNomeBaralho.text = baralho.nome
        setOnClickListeners()
        setObservers()
        return builder.create()
    }

    private fun setObservers(){

    }

    private fun setOnClickListeners(){


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Evita vazamento de memória
    }
}
