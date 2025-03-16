package com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import com.example.brash.utilsGeral.AppVM
import com.example.brash.utilsGeral.MyApplication
import androidx.fragment.app.activityViewModels
import kotlinx.coroutines.launch
import java.util.Locale

class RevisaoFrInicio : Fragment(R.layout.gtc_revisao_fr_inicio) {

    private var _binding : GtcRevisaoFrInicioBinding? = null
    private val binding get() = _binding!!

    private val revisaoCartaoVM: RevisaoCartaoVM by activityViewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = GtcRevisaoFrInicioBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //revisaoCartaoVM = ViewModelProvider(requireActivity())[RevisaoCartaoVM::class.java]
        setObservers()
        setOnClickListeners()

        revisaoCartaoVM.baralhoOwner.value?.let {
            revisaoCartaoVM.getAllCartoes()
            binding.RevisaoCartaoAcTextViewNomeBaralho.text = it.nome
        } ?: run {
            Toast.makeText(requireContext(), "Baralho não encontrado para nomear o título da revisão.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setObservers(){
        // Observe variáveis reativas e atualize a UI
        revisaoCartaoVM.cardsToReviewNumber.observe(viewLifecycleOwner) { cardsToReview ->
            binding.RevisaoCartaoAcTextViewCartoesRevisarQuantidade.text = String.format(Locale.getDefault(), "%d", cardsToReview)
        }

        revisaoCartaoVM.newCardsNumber.observe(viewLifecycleOwner) { newCards ->
            binding.RevisaoCartaoAcTextViewCartoesNovosQuantidade.text = String.format(Locale.getDefault(), "%d", newCards)
        }

        revisaoCartaoVM.forgottenCardsNumber.observe(viewLifecycleOwner) { forgottenCards ->
            binding.RevisaoCartaoAcTextViewCartoesEsquecidosQuantidade.text = String.format(Locale.getDefault(), "%d", forgottenCards)
        }
    }


    private fun setOnClickListeners(){

        binding.RevisaoCartaoAcButtonIniciarRevisao.setOnClickListener {

            revisaoCartaoVM.getNext(
                onSucess = {
                    Toast.makeText(requireContext(), "Iniciando a revisão", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_revisaoFrInicio_to_revisaoFrCartao)
                },
                onFailure = {
                    Toast.makeText(context, "Não há cartões a serem revisados para esse baralho.", Toast.LENGTH_SHORT).show()
                }
            )
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    fun auxEsquizofreniaSaick(){
        val pastaRepository = PastaRepository()
        val baralhoRepository = BaralhoRepository()
        val cartaoRepository = CartaoRepository()

        lifecycleScope.launch {
            val baralhoid = "Cmpzp5ySYkWqoSKzzTNq"
            val result = pastaRepository.getFolders()
            result
                .onSuccess { listaPastas ->

                    for(pasta in listaPastas){
                        if(pasta.idPasta == "root"){
                            for(baralho in pasta.baralhos){
                                if(baralho.idBaralho == baralhoid){
                                    val result = baralhoRepository.getCards(baralho)
                                    result
                                        .onSuccess { listaCartoes ->
                                            Log.e("printado cartoes", "$listaCartoes")
                                        }
                                        .onFailure {
                                            Log.e("printando cartoes", "algo deu errado")
                                        }
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

    }
}

