package com.example.brash.nucleo.ui.view.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.brash.R
import com.example.brash.databinding.NucDefinirSenhaFrConfirmacaoCodigoBinding
import com.example.brash.nucleo.ui.viewModel.DefinirSenhaVM

/**
 * Fragment to handle verifying the password reset code entered by the user.
 *
 * This fragment allows the user to input a verification code that was sent to their email.
 * If the code is correct, the user is navigated to the next step of resetting their password.
 */
class DefinirSenhaFrConfirmacaoCodigo : Fragment(R.layout.nuc_definir_senha_fr_confirmacao_codigo) {

    private var _binding : NucDefinirSenhaFrConfirmacaoCodigoBinding? = null
    private val binding get() = _binding!!
//
//    private val args: DefinirSenhaFrConfirmacaoCodigoArgs by navArgs()

    private lateinit var definirSenhaVM : DefinirSenhaVM

    /**
     * Inflates the layout for the fragment and sets up the binding.
     *
     * @param inflater The LayoutInflater to inflate the fragment's layout.
     * @param container The container that holds the fragment's view.
     * @param savedInstanceState A Bundle containing any saved instance state.
     * @return The root view of the inflated layout.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = NucDefinirSenhaFrConfirmacaoCodigoBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Called when the view has been created. Initializes the ViewModel,
     * sets up observers, and sets click listeners for the UI elements.
     *
     * @param view The root view of the fragment.
     * @param savedInstanceState The saved instance state.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        definirSenhaVM = ViewModelProvider(requireActivity())[DefinirSenhaVM::class.java]
        setObservers()
        setOnClickListeners()
    }

    /**
     * Sets click listeners for the buttons in the fragment.
     */
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

    /**
     * Finishes the activity and navigates back.
     */
    private fun finishActivity(){
        requireActivity().finish()
    }

    /**
     * Sets observers to watch for changes in the ViewModel's data.
     */
    private fun setObservers(){
        definirSenhaVM.verificationCodeMessageError.observe(viewLifecycleOwner){
            binding.DefinirSenhaAcTextViewMensagemErroCodigo.text = it
            binding.DefinirSenhaAcTextViewMensagemErroCodigo.visibility = View.VISIBLE
        }
    }

    /**
     * Clears the error message for the verification code when the fragment is paused.
     */
    override fun onPause() {
        super.onPause()
        definirSenhaVM.clearVerificationCodeMessageError()
    }

    /**
     * Cleans up the binding when the fragment's view is destroyed to prevent memory leaks.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}