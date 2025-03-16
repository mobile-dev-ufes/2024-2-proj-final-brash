package com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel

import android.app.Application
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.brash.R
import com.example.brash.aprendizado.gestaoDeConteudo.data.repository.BaralhoRepository
import com.example.brash.aprendizado.gestaoDeConteudo.data.repository.CartaoRepository
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Baralho
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Cartao
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.CategoriaDoAprendizado
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Dica
import com.example.brash.aprendizado.gestaoDeConteudo.domain.useCase.SuperMemo2
import com.example.brash.aprendizado.gestaoDeConteudo.utils.NivelRevisao
import com.example.brash.nucleo.utils.UtilsFoos
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.LinkedList
import java.util.Queue

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

    private val baralhoRepository = BaralhoRepository()
    private val cartaoRepository = CartaoRepository()

    private fun getStringApplication(id : Int) : String{
        return getApplication<Application>().getString(id)
    }

    fun getAllCartoes() {

        viewModelScope.launch {
            val result = baralhoRepository.getCards(_baralhoOwner.value!!)

            result
                .onSuccess {
                        cartoes ->
                    _cartaoList.value = cartoes
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

    fun getDicasDoCartao(){
        _dicaList.value = listOf(Dica(texto ="Testador"), Dica(texto ="Opara"))

        viewModelScope.launch {
            val result = cartaoRepository.getHints(_cartaoEmFoco.value!!)
            result.onSuccess {
                _dicaList.value = it
            }
            result.onFailure { e->
                UtilsFoos.showToast(getApplication(), getStringApplication(R.string.erro_requisicao_banco_dados_firebase))
                //UtilsFoos.showToast(getApplication(), "Ocorreu algum erro ao obter as dicas do cartão:: ${e}")
            }
        }
    }
    private fun setCartoesToRevisao(){
        _cartaoQueue.value = ArrayDeque(
            (_cartaoList.value ?: emptyList()).filter { cartao ->
                cartao.dataDeRevisao.toLocalDate() == LocalDateTime.now().toLocalDate()
            }
        )
        logCartaoQueue()
    }

    fun setAllCartoesToRevisao(){
        _cartaoQueue.value = ArrayDeque(_cartaoList.value ?: emptyList())

        Log.e("RevisaoCartaoVM", "Lista de todos os cartõew ${_cartaoList.value}")

    }

    private fun updateCategories(){
        var newCardsNumberAux = 0
        var cardsToReviewNumberAux = 0
        var forgottenCardsNumberAux = 0
        val todayDate = LocalDateTime.now()
        _cartaoList.value?.let { cartaoList ->
            for (cartao in cartaoList) {
                // Seu código para processar cada cartao
                if(cartao.categoriaDoAprendizado == CategoriaDoAprendizado.NOVO){
                    newCardsNumberAux += 1
                }
                else if(cartao.categoriaDoAprendizado == CategoriaDoAprendizado.REAPRENDENDO){
                    forgottenCardsNumberAux += 1
                }
                if(todayDate.dayOfYear == cartao.dataDeRevisao.dayOfYear ){
                    cardsToReviewNumberAux += 1
                }
            }
        }
        Log.d("RevisaoCartaoVM","Novos: ${newCardsNumberAux}\nA revisar:${cardsToReviewNumberAux} \nEsquecidos: ${forgottenCardsNumberAux}")
        _newCardsNumber.value = newCardsNumberAux
        _cardsToReviewNumber.value = cardsToReviewNumberAux
        _forgottenCardsNumber.value = forgottenCardsNumberAux


    }

    fun updateCategoriaDoCartaoEmFoco(nivelRevisao: NivelRevisao){
        //TODO:: dar update no cartão do firebase
        _cartaoEmFoco.value?.let { cartao ->
            val newCard = SuperMemo2.reviewCard(cartao, nivelRevisao)
            updateCategories()
            viewModelScope.launch {
                cartaoRepository.updateCardFromReview(cartao,
                    newCard.fatorDeRevisao, newCard.intervaloRevisao, newCard.dataDeRevisao, newCard.categoriaDoAprendizado)
                Log.d("RevisaoCartaoVM","Erro: Fila de cartões está nula.")
            }
            if (nivelRevisao == NivelRevisao.ESQUECI) {
                _cartaoQueue.value?.addLast(cartao) ?: run {
                    // Caso _cartaoQueue seja nulo, você pode lidar com isso aqui
                }
            }
        } ?: run {
            // Caso _cartaoEmFoco seja nulo, você pode lidar com isso aqui
            Log.d("RevisaoCartaoVM","Erro: Cartão não encontrado.")
        }
    }

    private fun logCartaoQueue(){
        var msg = ""
        if(_cartaoQueue.value!=null){
            for(c in _cartaoQueue.value!!){
                msg += c.pergunta +"\n"
            }
        }

        Log.d("RevisaoCartaoVM","Essa eh a lista de cartoes: ${msg}")
    }

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

    fun setCartaoEmFoco(cartao: Cartao){
        cartaoEmFoco.value = cartao
        Log.d("HomeDialogs", "Defini Baralho em FOCO")
    }

    fun setNumberCards(newCardsNumber : Int, cardsToReviewnNumber : Int, forgottenCardsNumber : Int){
        _newCardsNumber.value = newCardsNumber
        _cardsToReviewNumber.value = cardsToReviewnNumber
        _forgottenCardsNumber.value = forgottenCardsNumber
        return
    }

    fun showAnswers(){
        _buttonShowAnswersVisibility.value = View.GONE
        _linearLayoutButtonsAnswerVisibility.value = View.VISIBLE
    }

    fun hideAnswers(){
        _buttonShowAnswersVisibility.value = View.VISIBLE
        _linearLayoutButtonsAnswerVisibility.value = View.GONE
    }

    fun showHints(){
        _buttonShowHintsVisibility.value = View.GONE
        _recycleViewHintsVisibility.value = View.VISIBLE
    }

    fun hideHints(){
        _buttonShowHintsVisibility.value = View.VISIBLE
        _recycleViewHintsVisibility.value = View.GONE
    }

    fun setBaralhoOwner(baralho: Baralho){
        baralhoOwner.value = baralho
        Log.d("RevisaoAC", "Defini Baralho em FOCO")
    }
    fun resetBaralhoOwner(){
        baralhoOwner.value = null
        Log.d("RevisaoAC", "Resetei Baralho em FOCO")
    }



}
