package com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.brash.R
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Dica
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.adapter.ListaDicaAdapter
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.listener.OnDicaListener
import com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel.RevisaoCartaoVM
import com.example.brash.aprendizado.gestaoDeConteudo.utils.NivelRevisao
import com.example.brash.aprendizado.gestaoDeConteudo.utils.showCartoesInfoDialog
import com.example.brash.databinding.GtcRevisaoFrCartaoBinding
import java.util.Locale

/**
 * Fragment responsible for displaying and managing the card review process.
 *
 * This fragment presents the flashcard question and answer, allows users to interact with the card by rating
 * the review difficulty, and shows hints and answers dynamically. It also handles the display of related tips.
 *
 * @constructor Creates an instance of [RevisaoFrCartao].
 */
class RevisaoFrCartao : Fragment(R.layout.gtc_revisao_fr_cartao) {

    private var _binding : GtcRevisaoFrCartaoBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView

    private val revisaoCartaoVM: RevisaoCartaoVM by activityViewModels()
    private lateinit var adapter : ListaDicaAdapter
    /**
     * Called when the fragment's view is created.
     *
     * This method inflates the layout using ViewBinding and initializes the RecyclerView with its adapter.
     * It also fetches the tips for the card and sets up click listeners for user interaction.
     *
     * @param inflater The LayoutInflater object that can be used to inflate the fragment's view.
     * @param container The parent view that the fragment's UI will be attached to.
     * @param savedInstanceState A [Bundle] containing the fragment's previously saved state.
     * @return The root view of the fragment.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = GtcRevisaoFrCartaoBinding.inflate(inflater, container, false)

        return binding.root
    }
    /**
     * Called after the fragment's view has been created.
     *
     * This method initializes the RecyclerView, sets the adapter, and sets up click listeners for the fragment's UI.
     *
     * @param view The root view of the fragment.
     * @param savedInstanceState A [Bundle] containing the fragment's previously saved state.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        // Inicializando o listener diretamente
        val listener = object : OnDicaListener {
            override fun onClick(d: Dica) {
            }
        }

        // Inicializando o adapter com o listener
        adapter = ListaDicaAdapter().apply {
            setListener(listener) // Garanta que o listener seja configurado
        }

        recyclerView = binding.RevisaoCartaoAcRecycleViewDicas
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter // Defina o adapter na RecyclerView


        revisaoCartaoVM.getDicasDoCartao()

        setObservers()
        setOnClickListeners()
    }
    /**
     * Sets up observers for LiveData variables in the ViewModel and updates the UI accordingly.
     *
     * This method observes changes to the card question/answer, tip list, visibility of UI elements,
     * and number of cards to review.
     */
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
        revisaoCartaoVM.dicaList.observe(viewLifecycleOwner){
            Log.d("RevisaoCartaoVM", "Tamanho da lista de dicas: ${it.size}")
            adapter.updateDicaList(it)
        }
        revisaoCartaoVM.buttonShowAnswersVisibility.observe(viewLifecycleOwner){
            binding.RevisaoCartaoAcButtonMostrarResposta.visibility = it
        }
        revisaoCartaoVM.linearLayoutButtonsAnswerVisibility.observe(viewLifecycleOwner){
            binding.RevisaoCartaoAcLinearLayoutBotoesResposta.visibility = it
            binding.RevisaoCartaoAcTextViewResposta.visibility = it
            binding.RevisaoCartaoAcViewSeparador.visibility = it
        }
        revisaoCartaoVM.buttonShowHintsVisibility.observe(viewLifecycleOwner){
            binding.RevisaoCartaoAcButtonMostrarRecycleViewDicas.visibility = it
        }
        revisaoCartaoVM.recycleViewHintsVisibility.observe(viewLifecycleOwner){
            binding.RevisaoCartaoAcRecycleViewDicas.visibility = it
            binding.RevisaoCartaoAcRecycleViewSeparador.visibility = it
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
    /**
     * Sets up click listeners for various buttons in the fragment.
     *
     * This method sets up actions for buttons such as showing the answer, rating the card, and navigating to the next card.
     */
    private fun setOnClickListeners(){
        binding.RevisaoCartaoAcButtonMostrarResposta.setOnClickListener {
            revisaoCartaoVM.showAnswers()
        }
        binding.RevisaoCartaoAcButtonFacil.setOnClickListener {
            revisaoCartaoVM.updateCategoriaDoCartaoEmFoco(NivelRevisao.FACIL)
            revisaoCartaoVM.getNext(
                onSucess = {
                    revisaoCartaoVM.hideAnswers()
                    revisaoCartaoVM.hideHints()
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
                    revisaoCartaoVM.hideHints()
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
                    revisaoCartaoVM.hideHints()
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
                    revisaoCartaoVM.hideHints()
                },
                onFailure = {
                    findNavController().navigate(R.id.action_revisaoFrCartao_to_revisaoFrFinal)
                }
            )
        }
        binding.RevisaoCartaoAcConstraintLayoutQuantidadeCartoes.setOnClickListener{
            showCartoesInfoDialog(requireContext())
        }

        binding.RevisaoCartaoAcButtonMostrarRecycleViewDicas.setOnClickListener{
            revisaoCartaoVM.showHints()
        }
    }
    /**
     * Cleans up the fragment's view by setting the binding to null.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}