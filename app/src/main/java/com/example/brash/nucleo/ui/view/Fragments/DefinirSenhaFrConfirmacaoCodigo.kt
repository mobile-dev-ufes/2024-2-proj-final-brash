package com.example.brash.nucleo.ui.view.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.brash.R
import com.example.brash.databinding.NucDefinirSenhaFrConfirmacaoCodigoBinding
import com.example.brash.databinding.NucDefinirSenhaFrEnvioCodigoBinding
import com.example.brash.nucleo.ui.viewModel.DefinirSenhaVM
import com.example.brash.nucleo.utils.UtilsFoos

class DefinirSenhaFrConfirmacaoCodigo : Fragment(R.layout.nuc_definir_senha_fr_confirmacao_codigo) {

    private var _binding : NucDefinirSenhaFrConfirmacaoCodigoBinding? = null
    private val binding get() = _binding!!
//
//    private val args: DefinirSenhaFrConfirmacaoCodigoArgs by navArgs()

    private lateinit var definirSenhaVM : DefinirSenhaVM

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = NucDefinirSenhaFrConfirmacaoCodigoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        definirSenhaVM = ViewModelProvider(requireActivity())[DefinirSenhaVM::class.java]
        setObservers()
        setOnClickListeners()
    }

    private fun setOnClickListeners(){

        binding.DefinirSenhaAcButtonVerificarCodigo.setOnClickListener {
            val typedVerificationCode = binding.DefinirSenhaAcTextInputEditTextCodigo.text.toString()
//            if(definirSenhaVM.checkVerificationCode(typedVerificationCode, args.verificationCode)){
//                findNavController().navigate(R.id.action_definirSenhaFrConfirmacaoCodigo_to_definirSenhaFrNovaSenha)
//            }
        }

        binding.DefinirSenhaAcButtonCancelar.setOnClickListener {
            finishActivity()
        }
    }

    private fun finishActivity(){
        requireActivity().finish()
    }

    private fun setObservers(){
        definirSenhaVM.verificationCodeMessageError.observe(viewLifecycleOwner){
            binding.DefinirSenhaAcTextViewMensagemErroCodigo.text = it
            binding.DefinirSenhaAcTextViewMensagemErroCodigo.visibility = View.VISIBLE
        }
    }

    override fun onPause() {
        super.onPause()
        definirSenhaVM.clearVerificationCodeMessageError()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}