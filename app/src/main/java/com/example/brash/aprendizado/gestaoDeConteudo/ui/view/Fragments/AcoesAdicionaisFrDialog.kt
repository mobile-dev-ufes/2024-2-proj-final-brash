package com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.ListarBaralhoPublicoAC
import com.example.brash.databinding.GtcHomeFrAcoesAdicionaisBinding

class AcoesAdicionaisFrDialog : DialogFragment() {

    private var _binding: GtcHomeFrAcoesAdicionaisBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflar o layout com ViewBinding
        _binding = GtcHomeFrAcoesAdicionaisBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
    }

    private fun setObservers(){

    }

    private fun setOnClickListeners(){

        binding.HomeFrOpcoesAdicionaisTextViewCriarBaralho.setOnClickListener {
            dismiss()
            Toast.makeText(requireContext(), "Criar Baralho", Toast.LENGTH_SHORT).show()
            CriarBaralhoFrDialog().show(parentFragmentManager, "CriarBaralhoDialog")
        }

        binding.HomeFrOpcoesAdicionaisTextViewCriarPasta.setOnClickListener {
            dismiss()
            Toast.makeText(requireContext(), "Criar Pasta", Toast.LENGTH_SHORT).show()
            CriarPastaFrDialog().show(parentFragmentManager, "CriarPastaDialog")
        }

        binding.HomeFrOpcoesAdicionaisTextViewProcurarBaralhosPublicos.setOnClickListener {
            dismiss()
            Toast.makeText(requireContext(), "Procurar Baralhos Públicos", Toast.LENGTH_SHORT).show()
            intentToListarBaralhoPublicoAc()
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
