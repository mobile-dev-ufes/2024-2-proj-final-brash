package com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.brash.R
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.CategoriaDoAprendizado
import com.example.brash.aprendizado.gestaoDeConteudo.domain.useCase.SuperMemo2
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.RevisaoCartaoAC
import com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel.RevisaoCartaoVM
import com.example.brash.aprendizado.gestaoDeConteudo.utils.NivelRevisao
import com.example.brash.aprendizado.gestaoDeConteudo.utils.nucleoUtils
import com.example.brash.aprendizado.gestaoDeConteudo.utils.showCartoesInfoDialog
import com.example.brash.databinding.GtcRevisaoFrCartaoBinding
import com.example.brash.databinding.NucCadastrarFrExitoBinding
import java.util.Locale

class RevisaoFrCartao : Fragment(R.layout.gtc_revisao_fr_cartao) {

    private var _binding : GtcRevisaoFrCartaoBinding? = null
    private val binding get() = _binding!!

    private val revisaoCartaoVM: RevisaoCartaoVM by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = GtcRevisaoFrCartaoBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setObservers()
        setOnClickListeners()
    }

    private fun setObservers(){

        revisaoCartaoVM.cartaoEmFoco.observe(viewLifecycleOwner){
            // Iniciar animação de fade-out nas TextViews
            val fadeOutPergunta =
                ObjectAnimator.ofFloat(binding.RevisaoCartaoAcTextViewPergunta, "alpha", 1f, 0f)

            fadeOutPergunta.duration = 500  // Tempo para fade-out

            // Iniciar animação de fade-out
            fadeOutPergunta.start()

            // Após o fade-out, atualizar os textos e iniciar o fade-in
            fadeOutPergunta.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    // Atualiza os textos depois da animação de fade-out
                    binding.RevisaoCartaoAcTextViewPergunta.text = it.pergunta
                    binding.RevisaoCartaoAcTextViewResposta.text = it.resposta

                    // Inicia animação de fade-in
                    val fadeInPergunta = ObjectAnimator.ofFloat(
                        binding.RevisaoCartaoAcTextViewPergunta,
                        "alpha",
                        0f,
                        1f
                    )

                    fadeInPergunta.duration = 500

                    fadeInPergunta.start()
                }
            })
        }

        revisaoCartaoVM.buttonShowAnswersVisibility.observe(viewLifecycleOwner){
            binding.RevisaoCartaoAcButtonMostrarResposta.visibility = it
        }
        revisaoCartaoVM.linearLayoutButtonsAnswerVisibility.observe(viewLifecycleOwner){
            binding.RevisaoCartaoAcLinearLayoutBotoesResposta.visibility = it
            binding.RevisaoCartaoAcTextViewResposta.visibility = it
            binding.RevisaoCartaoAcViewSeparador.visibility = it
        }

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
        binding.RevisaoCartaoAcButtonMostrarResposta.setOnClickListener {
            revisaoCartaoVM.showAnswers()
        }
        binding.RevisaoCartaoAcButtonFacil.setOnClickListener {
            revisaoCartaoVM.updateCategoriaDoCartaoEmFoco(NivelRevisao.FACIL)
            revisaoCartaoVM.getNext(
                onSucess = {
                    revisaoCartaoVM.hideAnswers()
                },
                onFailure = {
                    findNavController().navigate(R.id.action_revisaoFrCartao_to_revisaoFrFinal)
                }
            )
        }
        binding.RevisaoCartaoAcButtonBom.setOnClickListener {
            revisaoCartaoVM.updateCategoriaDoCartaoEmFoco(NivelRevisao.BOM)
            revisaoCartaoVM.getNext(
                onSucess = {
                    revisaoCartaoVM.hideAnswers()
                },
                onFailure = {
                    findNavController().navigate(R.id.action_revisaoFrCartao_to_revisaoFrFinal)
                }
            )
        }
        binding.RevisaoCartaoAcButtonDificil.setOnClickListener {
            revisaoCartaoVM.updateCategoriaDoCartaoEmFoco(NivelRevisao.DIFICIL)
            revisaoCartaoVM.getNext(
                onSucess = {
                    revisaoCartaoVM.hideAnswers()
                },
                onFailure = {
                    findNavController().navigate(R.id.action_revisaoFrCartao_to_revisaoFrFinal)
                }
            )
        }
        binding.RevisaoCartaoAcButtonEsqueci.setOnClickListener {
            revisaoCartaoVM.updateCategoriaDoCartaoEmFoco(NivelRevisao.ESQUECI)
            revisaoCartaoVM.getNext(
                onSucess = {
                    revisaoCartaoVM.hideAnswers()
                },
                onFailure = {
                    findNavController().navigate(R.id.action_revisaoFrCartao_to_revisaoFrFinal)
                }
            )
        }
        binding.RevisaoCartaoAcConstraintLayoutQuantidadeCartoes.setOnClickListener{
            showCartoesInfoDialog(requireContext())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}