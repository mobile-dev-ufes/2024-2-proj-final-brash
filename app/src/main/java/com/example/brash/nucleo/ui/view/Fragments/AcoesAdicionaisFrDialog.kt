package com.example.brash.nucleo.ui.view.Fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.brash.R
import com.example.brash.databinding.GtcHomeAcBinding
import com.example.brash.databinding.GtcHomeFrAcoesAdicionaisBinding

class AcoesAdicionaisFrDialog : DialogFragment() {

    private var _binding: GtcHomeFrAcoesAdicionaisBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())

        // Inflar o layout com ViewBinding
        _binding = GtcHomeFrAcoesAdicionaisBinding.inflate(layoutInflater)
        builder.setView(binding.root)

        setOnClickListeners()
        setObservers()
        return builder.create()
    }

    private fun setObservers(){

    }

    private fun setOnClickListeners(){

        binding.HomeFrTextViewCriarBaralho.setOnClickListener {
            dismiss()
            Toast.makeText(requireContext(), "Criar Baralho", Toast.LENGTH_SHORT).show()
        }

        binding.HomeFrTextViewCriarPasta.setOnClickListener {
            dismiss()
            Toast.makeText(requireContext(), "Criar Pasta", Toast.LENGTH_SHORT).show()
        }

        binding.HomeFrTextViewProcurarBaralhosPublicos.setOnClickListener {
            dismiss()
            Toast.makeText(requireContext(), "Procurar Baralhos Públicos", Toast.LENGTH_SHORT).show()
        }

        binding.HomeFrTextViewPesquisarUsuarios.setOnClickListener{
            dismiss()
            Toast.makeText(requireContext(), "Procurar Usuários", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Evita vazamento de memória
    }
}
