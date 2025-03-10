package com.example.brash.nucleo.ui.view.Fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.brash.R

class OpcoesDeBuscaFrDialog: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())

        // Inflar o layout do diálogo
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.gtc_home_fr_opcoes_de_busca, null)

        // Encontrando os TextViews
        val aplicar = view.findViewById<TextView>(R.id.HomeFrOpcoesDeBuscaButtonAplicar)
        val cancelar = view.findViewById<TextView>(R.id.HomeFrOpcoesDeBuscaButtonCancelar)

        val spinnerOrdenar: Spinner = view.findViewById(R.id.HomeFrOpcoesDeBuscaSpinnerOrdenar)
        // Create an ArrayAdapter using the string array and a default spinner layout.
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.nuc_spinner_ordenar,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears.
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner.
            spinnerOrdenar.adapter = adapter
        }

        val spinnerFiltrar: Spinner = view.findViewById(R.id.HomeFrOpcoesDeBuscaSpinnerFiltrar)
        // Create an ArrayAdapter using the string array and a default spinner layout.
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.nuc_spinner_filtrar,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears.
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner.
            spinnerFiltrar.adapter = adapter
        }

        // Adicionando ações de clique
        cancelar.setOnClickListener {
            dismiss()
            Toast.makeText(requireContext(), "Cancelar Configuracoes de Busca", Toast.LENGTH_SHORT).show()
        }

        aplicar.setOnClickListener {
            dismiss()
            Toast.makeText(requireContext(), "Aplicar Configuracoes de Busca", Toast.LENGTH_SHORT).show()
        }

        builder.setView(view)
        return builder.create()
    }

    private fun setOnClickListeners(){}
}