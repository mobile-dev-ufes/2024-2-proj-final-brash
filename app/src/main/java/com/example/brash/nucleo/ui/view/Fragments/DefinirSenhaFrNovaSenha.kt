package com.example.brash.nucleo.ui.view.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.brash.R
import com.example.brash.databinding.NucDefinirSenhaFrConfirmacaoCodigoBinding
import com.example.brash.databinding.NucDefinirSenhaFrNovaSenhaBinding
import com.example.brash.nucleo.ui.viewModel.DefinirSenhaVM

class DefinirSenhaFrNovaSenha : Fragment(R.layout.nuc_definir_senha_fr_nova_senha) {

    private var _binding : NucDefinirSenhaFrNovaSenhaBinding? = null
    private val binding get() = _binding!!

    private lateinit var definirSenhaVM: DefinirSenhaVM

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = NucDefinirSenhaFrNovaSenhaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        definirSenhaVM = ViewModelProvider(requireActivity()).get(DefinirSenhaVM::class.java)
        setObservers()
        setOnClickListeners()
    }

    private fun setOnClickListeners(){

        binding.DefinirSenhaAcButtonRedefinirSenha.setOnClickListener {
            val newPassword = binding.DefinirSenhaAcTextInputEditTextNovaSenha.text.toString()
            val newPasswordRetyped = binding.DefinirSenhaAcTextInputEditTextRepeticaoSenha.text.toString()

            if(definirSenhaVM.checkNewPassword(newPassword, newPasswordRetyped)){
                definirSenhaVM.updateUsersPassword(newPassword){
                    findNavController().navigate(R.id.action_definirSenhaFrNovaSenha_to_definirSenhaFrExito)
                }
            }
        }

        binding.DefinirSenhaAcButtonCancelar.setOnClickListener {
            finishActivity()
        }

    }

    private fun finishActivity(){
        requireActivity().finish()
    }

    override fun onPause() {
        super.onPause()
        definirSenhaVM.clearNewPasswordMessageError()
    }

    private fun setObservers(){
        definirSenhaVM.newPasswordMessageError.observe(viewLifecycleOwner) {
            binding.DefinirSenhaAcTextViewMensagemErroNovaSenha.text = it
            binding.DefinirSenhaAcTextViewMensagemErroNovaSenha.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}