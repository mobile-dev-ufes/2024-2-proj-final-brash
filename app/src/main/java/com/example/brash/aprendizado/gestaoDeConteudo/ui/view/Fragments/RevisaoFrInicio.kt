package com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.brash.R
import com.example.brash.aprendizado.gestaoDeConteudo.data.repository.BaralhoRepository
import com.example.brash.aprendizado.gestaoDeConteudo.data.repository.CartaoRepository
import com.example.brash.aprendizado.gestaoDeConteudo.data.repository.PastaRepository
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Baralho
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Cartao
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Pasta
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.HomeAC
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.RevisaoCartaoAC
import com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel.RevisaoCartaoVM
import com.example.brash.databinding.GtcRevisaoFrCartaoBinding
import com.example.brash.databinding.GtcRevisaoFrFinalBinding
import com.example.brash.databinding.GtcRevisaoFrInicioBinding
import com.example.brash.databinding.NucCadastrarFrExitoBinding
import com.example.brash.nucleo.utils.UtilsFoos
import kotlinx.coroutines.launch

class RevisaoFrInicio : Fragment(R.layout.gtc_revisao_fr_inicio) {

    private var _binding : GtcRevisaoFrInicioBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = GtcRevisaoFrInicioBinding.inflate(inflater, container, false)

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

        binding.RevisaoCartaoAcButtonIniciarRevisao.setOnClickListener {

            val pastaRepository = PastaRepository()
            val baralhoRepository = BaralhoRepository()
            val cartaoRepository = CartaoRepository()

            lifecycleScope.launch {
                val id = "RzfUMsMYE6mTTlHKYZsW"
                val result = pastaRepository.getFolders()
                result
                    .onSuccess { listaPastas ->

                        for(pasta in listaPastas){
                            if(pasta.idPasta == "root"){
                                for(baralho in pasta.baralhos){
                                    if(baralho.idBaralho == id){
                                        val cartao = Cartao(
                                            pergunta = "ola5",
                                            resposta = "mundo5"
                                        )
                                        cartaoRepository.createCard(baralho, cartao)
                                    }
                                }
                            }
                        }

                        Log.e("fsfs", "$listaPastas")
                    }
                    .onFailure {
                        Log.e("teste no revisao inicio", "algo deu errado")
                    }


            }


            //Log.e("debug get pastas", "$pastasTudo")
            //findNavController().navigate(R.id.action_revisaoFrInicio_to_revisaoFrCartao)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}