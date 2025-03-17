package com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.brash.R
import com.example.brash.aprendizado.gestaoDeConteudo.data.repository.BaralhoRepository
import com.example.brash.aprendizado.gestaoDeConteudo.data.repository.CartaoRepository
import com.example.brash.aprendizado.gestaoDeConteudo.data.repository.PastaRepository
import com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel.RevisaoCartaoVM
import com.example.brash.databinding.GtcRevisaoFrInicioBinding
import androidx.fragment.app.activityViewModels
import kotlinx.coroutines.launch
import java.util.Locale

/**
 * Fragment responsible for displaying the start screen of a card review session.
 *
 * This fragment retrieves and displays the number of cards to review, new cards, and forgotten cards.
 * It also provides buttons to start the review session and navigate to the next screen.
 *
 * @constructor Creates an instance of [RevisaoFrInicio].
 */
class RevisaoFrInicio : Fragment(R.layout.gtc_revisao_fr_inicio) {

    private var _binding : GtcRevisaoFrInicioBinding? = null
    private val binding get() = _binding!!

    private val revisaoCartaoVM: RevisaoCartaoVM by activityViewModels()

    /**
     * Called when the fragment's view is created.
     *
     * This method inflates the layout using ViewBinding and initializes the ViewModel. It also
     * sets up observers for LiveData and click listeners for buttons.
     *
     * @param inflater The LayoutInflater object that can be used to inflate the fragment's view.
     * @param container The parent view that the fragment's UI will be attached to.
     * @param savedInstanceState A [Bundle] containing the fragment's previously saved state.
     * @return The root view of the fragment.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = GtcRevisaoFrInicioBinding.inflate(inflater, container, false)

        return binding.root
    }
    /**
     * Called after the fragment's view has been created.
     *
     * This method retrieves data for the review session, including the number of cards to review,
     * new cards, and forgotten cards. It sets up the necessary observers and button click listeners.
     *
     * @param view The root view of the fragment.
     * @param savedInstanceState A [Bundle] containing the fragment's previously saved state.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //revisaoCartaoVM = ViewModelProvider(requireActivity())[RevisaoCartaoVM::class.java]
        setObservers()
        setOnClickListeners()

        revisaoCartaoVM.baralhoOwner.value?.let {
            revisaoCartaoVM.getAllCartoes()
            binding.RevisaoCartaoAcTextViewNomeBaralho.text = it.nome
        } ?: run {
            //Toast.makeText(requireContext(), "Baralho não encontrado para nomear o título da revisão.", Toast.LENGTH_SHORT).show()
        }
    }
    /**
     * Sets up observers for the LiveData variables in the ViewModel.
     *
     * This method updates the UI with the number of cards to review, new cards, and forgotten cards.
     */
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

    /**
     * Sets up click listeners for the buttons in the UI.
     *
     * This includes the start review button, which begins the review process and navigates to the next fragment.
     * It also includes the button to start the review of all cards.
     */
    private fun setOnClickListeners(){

        binding.RevisaoCartaoAcButtonIniciarRevisao.setOnClickListener {

            revisaoCartaoVM.getNext(
                onSucess = {
                    //Toast.makeText(requireContext(), "Iniciando a revisão", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_revisaoFrInicio_to_revisaoFrCartao)
                },
                onFailure = {
                    //Toast.makeText(context, "Não há cartões a serem revisados para esse baralho.", Toast.LENGTH_SHORT).show()
                }
            )
        }
        /*binding.RevisaoCartaoAcButtonIniciarRevisaoTotal.setOnClickListener {

            revisaoCartaoVM.setAllCartoesToRevisao()
            revisaoCartaoVM.getNext(
                onSucess = {
                    //Toast.makeText(requireContext(), "Iniciando a revisão", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_revisaoFrInicio_to_revisaoFrCartao)
                },
                onFailure = {
                    //Toast.makeText(context, "Não há cartões a serem revisados para esse baralho.", Toast.LENGTH_SHORT).show()
                }
            )
        }*/

    }
    /**
     * Cleans up the fragment's view by setting the binding to null.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
    /**
     * Auxiliary method for testing schizophrenia scenarios.
     *
     * This method simulates retrieving folders and cards from repositories for testing purposes.
     */
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

