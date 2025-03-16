package com.example.brash.nucleo.ui.view.Fragments
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.brash.R
import com.example.brash.databinding.NucDefinirSenhaFrEnvioCodigoBinding
import com.example.brash.nucleo.ui.viewModel.DefinirSenhaVM

/**
 * Fragment to handle sending a password reset code to the user's email.
 *
 * This fragment allows the user to input their email, trigger a password reset
 * by sending a verification code to the provided email, and navigate to the
 * next screen for code verification.
 */
class DefinirSenhaFrEnvioCodigo : Fragment(R.layout.nuc_definir_senha_fr_envio_codigo) {

    private var _binding : NucDefinirSenhaFrEnvioCodigoBinding? = null
    private val binding get() = _binding!!

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

        _binding = NucDefinirSenhaFrEnvioCodigoBinding.inflate(inflater, container, false)
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

        initViewModel()
        setObservers()
        setOnClickListeners()
    }

    /**
     * Initializes the ViewModel and sets the current user email.
     */
    private fun initViewModel(){
        definirSenhaVM = ViewModelProvider(requireActivity())[DefinirSenhaVM::class.java]
        definirSenhaVM.setCurrentUserEmail()
    }

    /**
     * Sets click listeners for the buttons in the fragment.
     */
    private fun setOnClickListeners(){
        binding.DefinirSenhaAcButtonEnviar.setOnClickListener {
            // lógica de enviar código
//            val verificationCode = "777"
//            val action = DefinirSenhaFrEnvioCodigoDirections.actionDefinirSenhaFrEnvioCodigoToDefinirSenhaFrConfirmacaoCodigo(verificationCode)
//            findNavController().navigate(action)
            val email = binding.DefinirSenhaAcTextInputEditTextEmail.text.toString()

            definirSenhaVM.updateUsersPassword(email, {
                actionToExito()
            })
        }
        binding.DefinirSenhaAcButtonCancelar.setOnClickListener {
            Toast.makeText(requireActivity(), "teste", Toast.LENGTH_SHORT).show()
            requireActivity().finish()
        }
    }

    private fun finishActivity(){
        requireActivity().finish()
    }

    /**
     * Sets observers to watch for changes in the ViewModel's data.
     */
    private fun setObservers(){
        definirSenhaVM.curretUserEmail.observe(viewLifecycleOwner){
            binding.DefinirSenhaAcTextInputEditTextEmail.setText(it)
        }
    }

    /**
     * Cleans up the binding to prevent memory leaks when the fragment's view is destroyed.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    /**
     * Navigates to the "Exito" screen after successfully sending the reset code.
     */
    private fun actionToExito(){
        val action = DefinirSenhaFrEnvioCodigoDirections.actionDefinirSenhaFrEnvioCodigoToDefinirSenhaFrConfirmacaoCodigo()
        findNavController().navigate(action)
    }
}