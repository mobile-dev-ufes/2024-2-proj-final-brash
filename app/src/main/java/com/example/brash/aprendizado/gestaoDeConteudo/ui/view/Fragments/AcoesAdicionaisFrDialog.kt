package com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.ListarBaralhoPublicoAC
import com.example.brash.databinding.GtcHomeFrAcoesAdicionaisBinding
import com.example.brash.nucleo.ui.view.PerfilAC

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

        binding.HomeFrOpcoesAdicionaisTextViewCriarBaralho.setOnClickListener {
            dismiss()
            Toast.makeText(requireContext(), "Criar Baralho", Toast.LENGTH_SHORT).show()
        }

        binding.HomeFrOpcoesAdicionaisTextViewCriarPasta.setOnClickListener {
            dismiss()
            Toast.makeText(requireContext(), "Criar Pasta", Toast.LENGTH_SHORT).show()
        }

        binding.HomeFrOpcoesAdicionaisTextViewProcurarBaralhosPublicos.setOnClickListener {
            dismiss()
            Toast.makeText(requireContext(), "Procurar Baralhos Públicos", Toast.LENGTH_SHORT).show()
            intentToListarBaralhoPublicoAc()
        }

        binding.HomeFrOpcoesAdicionaisTextViewPesquisarUsuarios.setOnClickListener{
            dismiss()
            Toast.makeText(requireContext(), "Procurar Usuários", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Evita vazamento de memória
    }

    private fun intentToListarBaralhoPublicoAc(){
        val intent = Intent(requireContext(), ListarBaralhoPublicoAC::class.java)
        startActivity(intent)
    }
}
