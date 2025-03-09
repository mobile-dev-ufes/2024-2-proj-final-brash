package com.example.brash.nucleo.ui.view.Fragments
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.brash.R
import com.example.brash.databinding.NucCadastrarFrFormBinding


class CadastrarFrForm : Fragment(R.layout.nuc_cadastrar_fr_form) {

    private var _binding : NucCadastrarFrFormBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = NucCadastrarFrFormBinding.inflate(inflater, container, false)
        binding.CadastrarContaAcButtonCadastrar.text = "teste de fragmento"

        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}