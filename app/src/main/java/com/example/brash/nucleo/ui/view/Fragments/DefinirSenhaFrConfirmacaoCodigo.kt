package com.example.brash.nucleo.ui.view.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.brash.R
import com.example.brash.databinding.NucDefinirSenhaFrConfirmacaoCodigoBinding
import com.example.brash.databinding.NucDefinirSenhaFrEnvioCodigoBinding
import com.example.brash.nucleo.utils.UtilsFoos

class DefinirSenhaFrConfirmacaoCodigo : Fragment(R.layout.nuc_definir_senha_fr_confirmacao_codigo) {

    private var _binding : NucDefinirSenhaFrConfirmacaoCodigoBinding? = null
    private val binding get() = _binding!!

    // view model

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = NucDefinirSenhaFrConfirmacaoCodigoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setObservers()
        setOnClickListeners()
    }

    private fun setOnClickListeners(){

        binding.DefinirSenhaAcButtonVerificarCodigo.setOnClickListener {
            UtilsFoos.showToast(requireActivity(), "Confirmando código")
            findNavController().navigate(R.id.action_definirSenhaFrConfirmacaoCodigo_to_definirSenhaFrNovaSenha)
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