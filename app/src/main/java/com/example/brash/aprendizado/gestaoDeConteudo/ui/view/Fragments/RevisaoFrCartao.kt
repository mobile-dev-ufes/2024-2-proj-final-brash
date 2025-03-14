package com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.brash.R
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.RevisaoCartaoAC
import com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel.RevisaoCartaoVM
import com.example.brash.databinding.GtcRevisaoFrCartaoBinding
import com.example.brash.databinding.NucCadastrarFrExitoBinding

class RevisaoFrCartao : Fragment(R.layout.gtc_revisao_fr_cartao) {

    private var _binding : GtcRevisaoFrCartaoBinding? = null
    private val binding get() = _binding!!

    private lateinit var revisaoCartaoVM : RevisaoCartaoVM

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = GtcRevisaoFrCartaoBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        revisaoCartaoVM = ViewModelProvider(requireActivity()).get(RevisaoCartaoVM::class.java)
        setObservers()
        setOnClickListeners()
    }

    private fun setObservers(){
        revisaoCartaoVM.buttonShowAnswersVisibility.observe(viewLifecycleOwner){
            binding.RevisaoCartaoAcButtonMostrarResposta.visibility = it
        }
        revisaoCartaoVM.linearLayoutButtonsAnswerVisibility.observe(viewLifecycleOwner){
            binding.RevisaoCartaoAcLinearLayoutBotoesResposta.visibility = it
            binding.RevisaoCartaoAcTextViewResposta.visibility = it
            binding.RevisaoCartaoAcViewSeparador.visibility = it
        }
    }

    private fun setOnClickListeners(){
        binding.RevisaoCartaoAcButtonMostrarResposta.setOnClickListener {
            revisaoCartaoVM.showAnswers()
        }
        binding.RevisaoCartaoAcButtonFacil.setOnClickListener {
            revisaoCartaoVM.hideAnswers()
        }
        binding.RevisaoCartaoAcButtonEsqueci.setOnClickListener {
            findNavController().navigate(R.id.action_revisaoFrCartao_to_revisaoFrFinal)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}