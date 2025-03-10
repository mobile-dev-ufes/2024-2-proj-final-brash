package com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.brash.databinding.GtcHomeFrAcoesAdicionaisBinding
import com.example.brash.databinding.GtcHomeFrAcoesPastaBinding

class AcoesPastaFrDialog : DialogFragment() {

    private var _binding: GtcHomeFrAcoesPastaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())

        // Inflar o layout com ViewBinding
        _binding = GtcHomeFrAcoesPastaBinding.inflate(layoutInflater)
        builder.setView(binding.root)

        setOnClickListeners()
        setObservers()
        return builder.create()
    }

    private fun setObservers(){

    }

    private fun setOnClickListeners(){

        binding.HomeFrAcoesPastaTextViewRenomearPasta.setOnClickListener {
            dismiss()
            Toast.makeText(requireContext(), "Renomera Pasta", Toast.LENGTH_SHORT).show()
        }
        binding.HomeFrAcoesPastaTextViewExcluirPasta.setOnClickListener {
            dismiss()
            Toast.makeText(requireContext(), "Excluir Pasta", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Evita vazamento de memória
    }
}
