package com.example.brash.nucleo.ui.view.Fragments
import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.brash.R
import com.example.brash.databinding.NucCadastrarFrFormBinding
import com.example.brash.nucleo.data.remoto.services.AccountService
import com.example.brash.nucleo.ui.view.Fragments.CadastrarFrFormDirections
import com.example.brash.nucleo.ui.viewModel.CadastrarContaVM
import com.example.brash.nucleo.ui.viewModel.LoginVM
import com.example.brash.nucleo.ui.viewModel.viewModelFactory
import com.example.brash.nucleo.utils.UtilsFoos
import com.example.brash.utilsGeral.MyApplication
import kotlinx.coroutines.launch


class CadastrarFrForm : Fragment(R.layout.nuc_cadastrar_fr_form) {

    private var _binding : NucCadastrarFrFormBinding? = null
    private val binding get() = _binding!!

    private lateinit var cadastrarContaVM: CadastrarContaVM
//    private val args: CadastrarFrFormArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = NucCadastrarFrFormBinding.inflate(inflater, container, false)
        //binding.CadastrarContaAcButtonCadastrar.text = "teste de fragmento"

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val application = requireActivity().application

//        cadastrarContaVM = ViewModelProvider(requireActivity()).get(CadastrarContaVM::class.java)
        cadastrarContaVM = ViewModelProvider(requireActivity(), viewModelFactory {
            CadastrarContaVM(application, MyApplication.appModule.accountService)
        }).get(CadastrarContaVM::class.java)


        setObservers()
        setOnClickListeners()
    }

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

    private fun setObservers(){
        cadastrarContaVM.formMessageError.observe(viewLifecycleOwner){
            binding.CadastrarContaAcTextViewMensagemErroForm.text = it
            binding.CadastrarContaAcTextViewMensagemErroForm.visibility = View.VISIBLE
        }
    }

    override fun onPause() {
        super.onPause()
        cadastrarContaVM.clearFormMessageError()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    private fun actionToExito(){
        val action = CadastrarFrFormDirections.actionCadastrarFrFormToCadastrarFrExito()
        findNavController().navigate(action)
    }
}