package com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.brash.R
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.HomeAC
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.RevisaoCartaoAC
import com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel.RevisaoCartaoVM
import com.example.brash.databinding.GtcRevisaoFrCartaoBinding
import com.example.brash.databinding.GtcRevisaoFrFinalBinding
import com.example.brash.databinding.NucCadastrarFrExitoBinding

class RevisaoFrFinal : Fragment(R.layout.gtc_revisao_fr_final) {

    private var _binding : GtcRevisaoFrFinalBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = GtcRevisaoFrFinalBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setObservers()
        setOnClickListeners()
    }

    private fun setObservers(){

    }

    private fun setOnClickListeners(){
        binding.RevisaoCartaoAcButtonVoltarHome.setOnClickListener {
            requireActivity().finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}