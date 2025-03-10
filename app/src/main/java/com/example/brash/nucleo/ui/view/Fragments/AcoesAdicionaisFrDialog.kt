package com.example.brash.nucleo.ui.view.Fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.brash.R

class AcoesAdicionaisFrDialog: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())

        // Inflar o layout do diálogo
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.gtc_home_fr_acoes_adicionais, null)

        // Encontrando os TextViews
        val criarBaralho = view.findViewById<TextView>(R.id.HomeFrTextViewCriarBaralho)
        val criarPasta = view.findViewById<TextView>(R.id.HomeFrTextViewCriarPasta)
        val procurarBaralhos = view.findViewById<TextView>(R.id.HomeFrTextViewProcurarBaralhosPublicos)
        val pesquisarUsuarios = view.findViewById<TextView>(R.id.HomeFrTextViewPesquisarUsuarios)

        // Adicionando ações de clique
        criarBaralho.setOnClickListener {
            dismiss()
            Toast.makeText(requireContext(), "Criar Baralho", Toast.LENGTH_SHORT).show()
        }

        criarPasta.setOnClickListener {
            dismiss()
            Toast.makeText(requireContext(), "Criar Pasta", Toast.LENGTH_SHORT).show()
        }

        procurarBaralhos.setOnClickListener {
            dismiss()
            Toast.makeText(requireContext(), "Procurar Baralhos Públicos", Toast.LENGTH_SHORT).show()
        }

        builder.setView(view)
        return builder.create()
    }

    private fun setOnClickListeners(){

    }
}