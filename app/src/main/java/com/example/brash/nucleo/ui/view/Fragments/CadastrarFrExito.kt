package com.example.brash.nucleo.ui.view.Fragments
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.brash.R
import com.example.brash.databinding.NucCadastrarFrExitoBinding
import com.example.brash.databinding.NucCadastrarFrFormBinding


class CadastrarFrExito : Fragment(R.layout.nuc_cadastrar_fr_exito) {

    private var _binding : NucCadastrarFrExitoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = NucCadastrarFrExitoBinding.inflate(inflater, container, false)
        binding.CadastrarContaAcButtonFazerLogin.text = "teste de fragmento exito"

        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}