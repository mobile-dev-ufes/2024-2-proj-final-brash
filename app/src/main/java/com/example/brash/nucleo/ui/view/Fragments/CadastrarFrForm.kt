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
import com.example.brash.nucleo.utils.UtilsFoos


class CadastrarFrForm : Fragment(R.layout.nuc_cadastrar_fr_form) {

    private var _binding : NucCadastrarFrFormBinding? = null
    private val binding get() = _binding!!

    private lateinit var cadastrarContaVM: CadastrarContaVM

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = NucCadastrarFrFormBinding.inflate(inflater, container, false)
        //binding.CadastrarContaAcButtonCadastrar.text = "teste de fragmento"

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cadastrarContaVM = ViewModelProvider(requireActivity()).get(CadastrarContaVM::class.java)

        setObservers()
        setOnClickListeners()
    }

    private fun setOnClickListeners(){

        binding.CadastrarContaAcButtonCadastrar.setOnClickListener{
            val userName = binding.CadastrarContaAcTextInputEditTextNome.text.toString()
            val exhibitionName = binding.CadastrarContaAcTextInputEditTextNomeExibicao.text.toString()
            val email = binding.CadastrarContaAcTextInputEditTextEmail.text.toString()
            val password = binding.CadastrarContaAcTextInputEditTextSenha.text.toString()

            cadastrarContaVM.handleRegisterForm(userName, exhibitionName, email, password, {
                val emailVerificationCode = "777"
                // desativando serviço de email
                //val emailVerificationCode = UtilsFoos.emailVerificationCodeGenerator(size=6)
                //cadastrarContaVM.sendCodeToEmail(email, emailVerificationCode, {
                    //actionToCadastrarFrCodigo(userName, exhibitionName, email, password, emailVerificationCode)
                //})
                actionToCadastrarFrCodigo(userName, exhibitionName, email, password, emailVerificationCode)
            })

        }
    }

    private fun actionToCadastrarFrCodigo(userName : String, exhibitionName : String, email : String, password : String, emailVerificationCode : String){
        val action = CadastrarFrFormDirections.actionCadastrarFrFormToCadastrarFrCodigo(userName, exhibitionName, email, password, emailVerificationCode)
        findNavController().navigate(action)
    }

    private fun setObservers(){
        cadastrarContaVM.formMessageError.observe(viewLifecycleOwner){
            binding.CadastrarContaAcTextViewMensagemErroForm.text = it
            binding.CadastrarContaAcTextViewMensagemErroForm.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}