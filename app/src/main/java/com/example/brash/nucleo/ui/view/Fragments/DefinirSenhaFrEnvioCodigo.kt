package com.example.brash.nucleo.ui.view.Fragments
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.brash.R
import com.example.brash.databinding.NucCadastrarFrFormBinding
import com.example.brash.databinding.NucDefinirSenhaFrEnvioCodigoBinding
import com.example.brash.nucleo.ui.viewModel.CadastrarContaVM
import com.example.brash.nucleo.ui.viewModel.DefinirSenhaVM
import com.example.brash.nucleo.utils.UtilsFoos
import kotlinx.coroutines.launch


class DefinirSenhaFrEnvioCodigo : Fragment(R.layout.nuc_definir_senha_fr_envio_codigo) {

    private var _binding : NucDefinirSenhaFrEnvioCodigoBinding? = null
    private val binding get() = _binding!!

    private lateinit var definirSenhaVM : DefinirSenhaVM

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = NucDefinirSenhaFrEnvioCodigoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        setObservers()
        setOnClickListeners()
    }

    private fun initViewModel(){
        definirSenhaVM = ViewModelProvider(requireActivity())[DefinirSenhaVM::class.java]
        definirSenhaVM.setCurrentUserEmail()
    }

    private fun setOnClickListeners(){
        binding.DefinirSenhaAcButtonCadastrar.setOnClickListener {
            // lógica de enviar código
            val verificationCode = "777"
            val action = DefinirSenhaFrEnvioCodigoDirections.actionDefinirSenhaFrEnvioCodigoToDefinirSenhaFrConfirmacaoCodigo(verificationCode)
            findNavController().navigate(action)
        }
        binding.DefinirSenhaAcButtonCancelar.setOnClickListener {
            finishActivity()
        }
    }

    private fun finishActivity(){
        requireActivity().finish()
    }

    private fun setObservers(){
        definirSenhaVM.curretUserEmail.observe(viewLifecycleOwner){
            binding.DefinirSenhaAcTextInputEditTextEmail.setText(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}