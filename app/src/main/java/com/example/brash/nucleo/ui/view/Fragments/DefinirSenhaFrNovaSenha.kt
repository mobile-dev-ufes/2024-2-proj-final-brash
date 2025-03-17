package com.example.brash.nucleo.ui.view.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.brash.R
import com.example.brash.databinding.NucDefinirSenhaFrNovaSenhaBinding
import com.example.brash.nucleo.ui.viewModel.DefinirSenhaVM

//NOT USED

/**
 * Fragment for allowing the user to set a new password during the password reset process.
 *
 * This fragment displays the form to enter a new password and confirm it by re-typing.
 * It also handles the logic of validating the new password and navigating to the success screen.
 */
class DefinirSenhaFrNovaSenha : Fragment(R.layout.nuc_definir_senha_fr_nova_senha) {

    private var _binding : NucDefinirSenhaFrNovaSenhaBinding? = null
    private val binding get() = _binding!!

    private lateinit var definirSenhaVM: DefinirSenhaVM
    /**
     * Inflates the layout for the fragment and returns the root view.
     *
     * @param inflater The LayoutInflater to inflate the fragment's layout.
     * @param container The container that holds the fragment's view.
     * @param savedInstanceState A Bundle containing any saved instance state.
     * @return The root view of the inflated layout.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = NucDefinirSenhaFrNovaSenhaBinding.inflate(inflater, container, false)
        return binding.root
    }
    /**
     * Called when the view has been created. Initializes the ViewModel, sets up observers,
     * and sets click listeners for buttons.
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
     * Sets the click listeners for the buttons in the fragment.
     */
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
    /**
     * Closes the activity when the "Cancelar" button is clicked.
     */
    private fun finishActivity(){
        requireActivity().finish()
    }
    /**
     * Called when the fragment is paused. Clears any error messages related to the new password.
     */
    override fun onPause() {
        super.onPause()
        definirSenhaVM.clearNewPasswordMessageError()
    }
    /**
     * Sets up observers for the ViewModel to handle error messages related to the new password.
     */
    private fun setObservers(){
        definirSenhaVM.newPasswordMessageError.observe(viewLifecycleOwner) {
            binding.DefinirSenhaAcTextViewMensagemErroNovaSenha.text = it
            binding.DefinirSenhaAcTextViewMensagemErroNovaSenha.visibility = View.VISIBLE
        }
    }
    /**
     * Called when the fragment's view is destroyed. Cleans up the binding to avoid memory leaks.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}