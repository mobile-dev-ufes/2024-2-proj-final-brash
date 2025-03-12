package com.example.brash.nucleo.ui.view.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.brash.R
import com.example.brash.databinding.NucDefinirSenhaFrConfirmacaoCodigoBinding
import com.example.brash.databinding.NucDefinirSenhaFrNovaSenhaBinding

class DefinirSenhaFrNovaSenha : Fragment(R.layout.nuc_definir_senha_fr_nova_senha) {

    private var _binding : NucDefinirSenhaFrNovaSenhaBinding? = null
    private val binding get() = _binding!!

    // view model

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = NucDefinirSenhaFrNovaSenhaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setObservers()
        setOnClickListeners()
    }

    private fun setOnClickListeners(){

        binding.DefinirSenhaAcButtonRedefinirSenha.setOnClickListener {
            findNavController().navigate(R.id.action_definirSenhaFrNovaSenha_to_definirSenhaFrExito)
        }

        binding.DefinirSenhaAcButtonCancelar.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun setObservers(){

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}