package com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel

import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.brash.R
import com.example.brash.aprendizado.gestaoDeConteudo.data.repository.BaralhoRepository
import com.example.brash.aprendizado.gestaoDeConteudo.data.repository.BaralhoRepository2
import com.example.brash.aprendizado.gestaoDeConteudo.data.repository.CartaoRepository
import com.example.brash.aprendizado.gestaoDeConteudo.data.repository.CartaoRepository2
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Baralho
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Cartao
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.CategoriaDoAprendizado
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Dica
import com.example.brash.aprendizado.gestaoDeConteudo.domain.useCase.SuperMemo2
import com.example.brash.aprendizado.gestaoDeConteudo.utils.NivelRevisao
import com.example.brash.nucleo.utils.UtilsFoos
import kotlinx.coroutines.launch
import java.time.LocalDateTime

/**
 * ViewModel for handling the review of cards in the context of learning content management.
 * Provides functionality to manage card states, hints, and categories.
 *
 * @param application The application context to access resources.
 */
class RevisaoCartaoVM(application: Application) : AndroidViewModel(application) {

    private var _buttonShowAnswersVisibility = MutableLiveData<Int>()
    val buttonShowAnswersVisibility get() = _buttonShowAnswersVisibility

    private var _linearLayoutButtonsAnswerVisibility = MutableLiveData<Int>()
    val linearLayoutButtonsAnswerVisibility get() = _linearLayoutButtonsAnswerVisibility

    private var _buttonShowHintsVisibility = MutableLiveData<Int>()
    val buttonShowHintsVisibility get() = _buttonShowHintsVisibility

    private var _recycleViewHintsVisibility = MutableLiveData<Int>()
    val recycleViewHintsVisibility get() = _recycleViewHintsVisibility

    private var _baralhoOwner = MutableLiveData<Baralho>()
    val baralhoOwner get() = _baralhoOwner

    private var _newCardsNumber = MutableLiveData<Int>()
    val newCardsNumber get() = _newCardsNumber

    private var _cardsToReviewNumber = MutableLiveData<Int>()
    val cardsToReviewNumber get() = _cardsToReviewNumber

    private var _forgottenCardsNumber = MutableLiveData<Int>()
    val forgottenCardsNumber get() = _forgottenCardsNumber

    private var _cartaoEmFoco = MutableLiveData<Cartao>()
    val cartaoEmFoco get() = _cartaoEmFoco

    private var _cartaoList = MutableLiveData<List<Cartao>>()
    private var _dicaList = MutableLiveData<List<Dica>>()
    val dicaList get() = _dicaList

    private var _cartaoQueue = MutableLiveData<ArrayDeque<Cartao>>()

    private val baralhoRepository = BaralhoRepository2()
    private val cartaoRepository = CartaoRepository2()

    /**
     * Gets the string resource based on the provided resource ID.
     *
     * @param id The resource ID.
     * @return The string corresponding to the provided resource ID.
     */
    private fun getStringApplication(id : Int) : String{
        return getApplication<Application>().getString(id)
    }

    /**
     * Fetches all cards from the current deck and processes them.
     * Updates the card categories and sets cards to review.
     */
    fun getAllCartoes() {

        viewModelScope.launch {
            val result = baralhoRepository.getCards2(_baralhoOwner.value!!)

            result
                .onSuccess {
                        cartoes ->
                    _cartaoList.value = cartoes
                    shuffleCartoes()
                    setCartoesToRevisao()
                    updateCategories()
                }
                .onFailure {
                    UtilsFoos.showToast(getApplication(), getStringApplication(R.string.erro_requisicao_banco_dados_firebase))
                    Log.e("Pasta", "Erro ao carregar pastas do firebase")
                    _cartaoList.value = emptyList()
                }

        }

    }

    fun shuffleCartoes(){
        _cartaoList.value = _cartaoList.value?.shuffled()
    }
    /**
     * Fetches the hints for the current focused card.
     */
    fun getDicasDoCartao(){
        _dicaList.value = listOf(Dica(texto ="Testador"), Dica(texto ="Opara"))

        viewModelScope.launch {
            val result = cartaoRepository.getHints2(_cartaoEmFoco.value!!)
            result.onSuccess {
                _dicaList.value = it
            }
            result.onFailure { e->
                UtilsFoos.showToast(getApplication(), getStringApplication(R.string.erro_requisicao_banco_dados_firebase))
                //UtilsFoos.showToast(getApplication(), "Ocorreu algum erro ao obter as dicas do cartão:: ${e}")
            }
        }
    }

    /**
     * Sets the cards that need to be reviewed based on the current date.
     */
    private fun setCartoesToRevisao(){
        _cartaoQueue.value = ArrayDeque(
            (_cartaoList.value ?: emptyList()).filter { cartao ->
                cartao.dataDeRevisao.toLocalDate() == LocalDateTime.now().toLocalDate()
            }
        )
        updateCategories()
        logCartaoQueue()
    }

    /**
     * Sets all cards to be reviewed.
     */
    fun setAllCartoesToRevisao(){
        _cartaoQueue.value = ArrayDeque(_cartaoList.value ?: emptyList())

        Log.e("RevisaoCartaoVM", "Lista de todos os cartõew ${_cartaoList.value}")

    }

    /**
     * Updates the number of new cards, cards to review, and forgotten cards based on the current list of cards.
     */
    private fun updateCategories(){
        var newCardsNumberAux = 0
        var cardsToReviewNumberAux = 0
        var forgottenCardsNumberAux = 0

        /*_cartaoList.value?.let { cartaoList ->
            for (cartao in cartaoList) {

                // AZUL
                if(cartao.categoriaDoAprendizado == CategoriaDoAprendizado.NOVO){
                    newCardsNumberAux += 1
                }
                // VERDE
                else if(todayDate.dayOfYear == cartao.dataDeRevisao.dayOfYear ){
                    cardsToReviewNumberAux += 1
                }
                // VERMELHO
                else if(cartao.categoriaDoAprendizado == CategoriaDoAprendizado.REAPRENDENDO
                    || cartao.categoriaDoAprendizado == CategoriaDoAprendizado.APRENDENDO){
                    forgottenCardsNumberAux += 1
                }

            }
        }*/

        _cartaoQueue.value?.let{ cartoes ->
            for(cartao in cartoes){
                // AZUL
                if(cartao.categoriaDoAprendizado == CategoriaDoAprendizado.NOVO){
                    newCardsNumberAux += 1
                }
                // VERDE
                else if(cartao.categoriaDoAprendizado == CategoriaDoAprendizado.RECENTE
                    || cartao.categoriaDoAprendizado == CategoriaDoAprendizado.MADURO ){
                    cardsToReviewNumberAux += 1
                }
                // VERMELHO
                else if(cartao.categoriaDoAprendizado == CategoriaDoAprendizado.REAPRENDENDO
                    || cartao.categoriaDoAprendizado == CategoriaDoAprendizado.APRENDENDO){
                    forgottenCardsNumberAux += 1
                }
            }

        }
        Log.d("RevisaoCartaoVM","Novos: ${newCardsNumberAux}\nA revisar:${cardsToReviewNumberAux} \nEsquecidos: ${forgottenCardsNumberAux}")
        _newCardsNumber.value = newCardsNumberAux
        _cardsToReviewNumber.value = cardsToReviewNumberAux
        _forgottenCardsNumber.value = forgottenCardsNumberAux


    }

    /**
     * Updates the category of the focused card based on the review level.
     * It also updates the card data in the repository.
     *
     * @param nivelRevisao The review level (easy, good, hard, or forgot).
     */
    fun updateCategoriaDoCartaoEmFoco(nivelRevisao: NivelRevisao){
        //TODO:: dar update no cartão do firebase
        _cartaoEmFoco.value?.let { cartao ->
            val newCard = SuperMemo2.reviewCard(cartao, nivelRevisao)

            viewModelScope.launch {
                cartaoRepository.updateCardFromReview2(cartao,
                    newCard.fatorDeRevisao, newCard.intervaloRevisao, newCard.dataDeRevisao, newCard.categoriaDoAprendizado)
                Log.d("RevisaoCartaoVM","Erro: Fila de cartões está nula.")
            }
            if (newCard.dataDeRevisao.dayOfYear == LocalDateTime.now().dayOfYear) {
                _cartaoQueue.value?.addLast(cartao) ?: run {
                    // Caso _cartaoQueue seja nulo, você pode lidar com isso aqui
                }
            }
            //TODO:: melhorar eficiência, modificar apenas um item
            updateCategories()
        } ?: run {
            // Caso _cartaoEmFoco seja nulo, você pode lidar com isso aqui
            Log.d("RevisaoCartaoVM","Erro: Cartão não encontrado.")
        }
    }

    /**
     * Logs the content of the card queue for debugging purposes.
     */
    private fun logCartaoQueue(){
        var msg = ""
        if(_cartaoQueue.value!=null){
            for(c in _cartaoQueue.value!!){
                msg += c.pergunta +"\n"
            }
        }

        Log.d("RevisaoCartaoVM","Essa eh a lista de cartoes: ${msg}")
    }

    /**
     * Gets the next card in the review queue.
     *
     * @param onSucess Callback function to handle success.
     * @param onFailure Callback function to handle failure.
     */
    fun getNext(onSucess: () -> Unit, onFailure:() -> Unit){
        _cartaoQueue.value?.let { queue ->
            if (queue.isNotEmpty()) {
                _cartaoEmFoco.value = queue.removeFirst()
                Log.d("RevisaoCartaoVM","\n\nEsse eh o cartao a ser revisado: ${_cartaoEmFoco.value!!.pergunta}")

                getDicasDoCartao()

                onSucess()
            } else {
                Log.d("RevisaoCartaoVM","\n\nFila da revisão está vazia")
                onFailure()
            }
        } ?: {
            Log.d("RevisaoCartaoVM","\n\n_cartaoQueue.value não está setado")
            onFailure()
        }
    }

    /**
     * Sets the card to be focused on in the review process.
     *
     * @param cartao The card to be focused on.
     */
    fun setCartaoEmFoco(cartao: Cartao){
        cartaoEmFoco.value = cartao
        Log.d("HomeDialogs", "Defini Baralho em FOCO")
    }

    /**
     * Updates the number of new, cards to review, and forgotten cards.
     *
     * @param newCardsNumber The updated number of new cards.
     * @param cardsToReviewnNumber The updated number of cards to review.
     * @param forgottenCardsNumber The updated number of forgotten cards.
     */
    fun setNumberCards(newCardsNumber : Int, cardsToReviewnNumber : Int, forgottenCardsNumber : Int){
        _newCardsNumber.value = newCardsNumber
        _cardsToReviewNumber.value = cardsToReviewnNumber
        _forgottenCardsNumber.value = forgottenCardsNumber
        return
    }

    /**
     * Shows the answers section in the UI.
     */
    fun showAnswers(){
        _buttonShowAnswersVisibility.value = View.GONE
        _linearLayoutButtonsAnswerVisibility.value = View.VISIBLE
    }

    /**
     * Hides the answers section in the UI.
     */
    fun hideAnswers(){
        _buttonShowAnswersVisibility.value = View.VISIBLE
        _linearLayoutButtonsAnswerVisibility.value = View.GONE
    }

    /**
     * Shows the hints section in the UI.
     */
    fun showHints(){
        _buttonShowHintsVisibility.value = View.GONE
        _recycleViewHintsVisibility.value = View.VISIBLE
    }

    /**
     * Hides the hints section in the UI.
     */
    fun hideHints(){
        _buttonShowHintsVisibility.value = View.VISIBLE
        _recycleViewHintsVisibility.value = View.GONE
    }

    /**
     * Sets the deck (Baralho) that owns the cards being reviewed.
     *
     * @param baralho The deck to be set as the owner.
     */
    fun setBaralhoOwner(baralho: Baralho){
        baralhoOwner.value = baralho
        Log.d("RevisaoAC", "Defini Baralho em FOCO")
    }

    /**
     * Resets the deck (Baralho) owner.
     */
    fun resetBaralhoOwner(){
        baralhoOwner.value = null
        Log.d("RevisaoAC", "Resetei Baralho em FOCO")
    }



}
