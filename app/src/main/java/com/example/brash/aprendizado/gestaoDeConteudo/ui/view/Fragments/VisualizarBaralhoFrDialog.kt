package com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Baralho
import com.example.brash.databinding.GtcHomeFrVisualizarBaralhoBinding

class VisualizarBaralhoFrDialog(val baralho: Baralho) : DialogFragment() {

    private var _binding: GtcHomeFrVisualizarBaralhoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())

        // Inflar o layout com ViewBinding
        _binding = GtcHomeFrVisualizarBaralhoBinding.inflate(layoutInflater)
        builder.setView(binding.root)

        binding.HomeFrVisualizarBaralhoInputTitulo.setText("123456789A123456789B123456789a123456789b")
        binding.HomeFrVisualizarBaralhoInputDescricao.setText("123456789A123456789B123456789A123456789B123456789A123456789B123456789A123456789B123456789A123456789B123456789A123456789B")
        setOnClickListeners()
        setObservers()
        return builder.create()
    }

    private fun setObservers(){
    }

    private fun setOnClickListeners(){

        binding.HomeFrVisualizarBaralhoButtonCancelar.setOnClickListener {
            dismiss()
        }
        binding.HomeFrVisualizarBaralhoButtonConfirmar.setOnClickListener {
            dismiss()
            Toast.makeText(requireContext(), "Baralho Editado", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Evita vazamento de memória
    }
}
