package com.example.brash.nucleo.ui.view.Fragments
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.brash.R
import com.example.brash.databinding.NucCadastrarFrCodigoBinding
import com.example.brash.databinding.NucCadastrarFrExitoBinding
import com.example.brash.databinding.NucCadastrarFrFormBinding


class CadastrarFrCodigo : Fragment(R.layout.nuc_cadastrar_fr_codigo) {

    private var _binding : NucCadastrarFrCodigoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = NucCadastrarFrCodigoBinding.inflate(inflater, container, false)
        binding.CadastrarContaAcButtonVerificarCodigo.text = "teste de fragmento verificar codigo"

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.CadastrarContaAcButtonVerificarCodigo.setOnClickListener{
            findNavController().navigate(R.id.action_cadastrarFrCodigo_to_cadastrarFrExito)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}