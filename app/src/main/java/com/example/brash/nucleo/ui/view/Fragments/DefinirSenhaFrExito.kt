package com.example.brash.nucleo.ui.view.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.brash.R
import com.example.brash.databinding.NucDefinirSenhaFrExitoBinding
import com.example.brash.databinding.NucDefinirSenhaFrNovaSenhaBinding

class DefinirSenhaFrExito : Fragment(R.layout.nuc_definir_senha_fr_exito) {

    private var _binding : NucDefinirSenhaFrExitoBinding? = null
    private val binding get() = _binding!!

    // view model

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = NucDefinirSenhaFrExitoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setObservers()
        setOnClickListeners()
    }

    private fun setOnClickListeners(){

    }

    private fun setObservers(){

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}