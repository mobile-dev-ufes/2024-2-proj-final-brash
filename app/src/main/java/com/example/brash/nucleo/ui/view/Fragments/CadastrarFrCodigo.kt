package com.example.brash.nucleo.ui.view.Fragments
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.brash.R
import com.example.brash.databinding.NucCadastrarFrCodigoBinding
import com.example.brash.databinding.NucCadastrarFrExitoBinding
import com.example.brash.databinding.NucCadastrarFrFormBinding
import com.example.brash.nucleo.ui.view.LoginAC
import com.example.brash.nucleo.ui.viewModel.CadastrarContaVM
import com.example.brash.nucleo.utils.UtilsFoos


class CadastrarFrCodigo : Fragment(R.layout.nuc_cadastrar_fr_codigo) {

    private var _binding : NucCadastrarFrCodigoBinding? = null
    private val binding get() = _binding!!

    private lateinit var cadastrarContaVM: CadastrarContaVM

    private val args: CadastrarFrCodigoArgs by navArgs() // safe args (userName, exhibitonName, email, password)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = NucCadastrarFrCodigoBinding.inflate(inflater, container, false)
        //binding.CadastrarContaAcButtonVerificarCodigo.text = "teste de fragmento verificar codigo"

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cadastrarContaVM = ViewModelProvider(requireActivity()).get(CadastrarContaVM::class.java)
        setObservers()
        setOnClickListeners()
    }


    private fun setObservers(){

    }

    private fun setOnClickListeners(){

        binding.CadastrarContaAcButtonVerificarCodigo.setOnClickListener{
            if(binding.CadastrarContaAcTextInputEditTextCodigo.text.toString() == args.emailVerificationCode){
                actionToCadastrarFrExito()
                //TODO! criar conta do usuário
            }
        }

    }

    private fun actionToCadastrarFrExito(){
        val action = CadastrarFrCodigoDirections.actionCadastrarFrCodigoToCadastrarFrExito()
        findNavController().navigate(action)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}