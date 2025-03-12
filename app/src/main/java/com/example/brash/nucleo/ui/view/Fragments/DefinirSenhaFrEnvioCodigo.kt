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
import com.example.brash.nucleo.utils.UtilsFoos
import kotlinx.coroutines.launch


class DefinirSenhaFrEnvioCodigo : Fragment(R.layout.nuc_definir_senha_fr_envio_codigo) {

    private var _binding : NucDefinirSenhaFrEnvioCodigoBinding? = null
    private val binding get() = _binding!!

    // view model

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = NucDefinirSenhaFrEnvioCodigoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // instanciar a view model

        setObservers()
        setOnClickListeners()
    }

    private fun setOnClickListeners(){

    }

    private fun setObservers(){

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}