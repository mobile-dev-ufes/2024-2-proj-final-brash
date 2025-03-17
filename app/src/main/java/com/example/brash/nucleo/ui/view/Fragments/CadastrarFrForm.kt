package com.example.brash.nucleo.ui.view.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.brash.R
import com.example.brash.databinding.NucCadastrarFrFormBinding
import com.example.brash.nucleo.ui.viewModel.CadastrarContaVM
import com.example.brash.nucleo.ui.viewModel.viewModelFactory
import com.example.brash.utilsGeral.MyApplication

/**
 * Fragment for registering a new user by providing their personal details.
 *
 * This fragment allows the user to input their username, exhibition name, email, and password
 * to create a new account. After successful registration, the user is navigated to a success screen.
 */
class CadastrarFrForm : Fragment(R.layout.nuc_cadastrar_fr_form) {

    private var _binding : NucCadastrarFrFormBinding? = null
    private val binding get() = _binding!!

    private lateinit var cadastrarContaVM: CadastrarContaVM
//    private val args: CadastrarFrFormArgs by navArgs()

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

        _binding = NucCadastrarFrFormBinding.inflate(inflater, container, false)
        //binding.CadastrarContaAcButtonCadastrar.text = "teste de fragmento"

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

        val application = requireActivity().application

        cadastrarContaVM = ViewModelProvider(requireActivity(), viewModelFactory {
            CadastrarContaVM(application, MyApplication.appModule.accountService)
        }).get(CadastrarContaVM::class.java)


        setObservers()
        setOnClickListeners()
    }

    /**
     * Sets the click listeners for the buttons in the fragment.
     */
    private fun setOnClickListeners() {
        binding.CadastrarContaAcButtonCadastrar.setOnClickListener {
            val userName = binding.CadastrarContaAcTextInputEditTextNome.text.toString()
            val exhibitionName =
                binding.CadastrarContaAcTextInputEditTextNomeExibicao.text.toString()
            val email = binding.CadastrarContaAcTextInputEditTextEmail.text.toString()
            val password = binding.CadastrarContaAcTextInputEditTextSenha.text.toString()

            cadastrarContaVM.registerNewUser(userName, exhibitionName, email, password, {
                actionToExito()
            })
        }
        binding.CadastrarContaAcButtonCancelar.setOnClickListener {
            requireActivity().finish()
        }
    }

    /**
     * Sets observers to watch for changes in the ViewModel's data.
     */
    private fun setObservers(){
        cadastrarContaVM.formMessageError.observe(viewLifecycleOwner){
            binding.CadastrarContaAcTextViewMensagemErroForm.text = it
            binding.CadastrarContaAcTextViewMensagemErroForm.visibility = View.VISIBLE
        }
    }

    /**
     * Clears the error message when the fragment is paused.
     */
    override fun onPause() {
        super.onPause()
        cadastrarContaVM.clearFormMessageError()
    }

    /**
     * Cleans up the binding when the fragment's view is destroyed to prevent memory leaks.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    /**
     * Navigates to the success screen after successful user registration.
     */
    private fun actionToExito(){
        val action = CadastrarFrFormDirections.actionCadastrarFrFormToCadastrarFrExito()
        findNavController().navigate(action)
    }
}