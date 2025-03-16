package com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel

import android.app.Application
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.brash.aprendizado.gestaoDeConteudo.data.repository.BaralhoRepository
import com.example.brash.aprendizado.gestaoDeConteudo.data.repository.CartaoRepository
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Baralho
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Cartao
import kotlinx.coroutines.launch
import java.util.LinkedList
import java.util.Queue

class RevisaoCartaoVM(application: Application) : AndroidViewModel(application) {

    private var _buttonShowAnswersVisibility = MutableLiveData<Int>()
    val buttonShowAnswersVisibility get() = _buttonShowAnswersVisibility

    private var _linearLayoutButtonsAnswerVisibility = MutableLiveData<Int>()
    val linearLayoutButtonsAnswerVisibility get() = _linearLayoutButtonsAnswerVisibility

    private var _baralhoOwner = MutableLiveData<Baralho>()
    val baralhoOwner get() = _baralhoOwner

    private var _newCardsNumber = MutableLiveData<Int>()
    val newCardsNumber get() = _newCardsNumber

    private var _cardsToReviewNumber = MutableLiveData<Int>()
    val cardsToReviewNumber get() = _cardsToReviewNumber

    private var _forgottenCardsNumber = MutableLiveData<Int>()
    val forgottenCardsNumber get() = _forgottenCardsNumber

    private var _cartaoEmFoco = MutableLiveData<Cartao>()
    private val cartaoEmFoco get() = _cartaoEmFoco

    private var _cartaoList = MutableLiveData<List<Cartao>>()

    private var _cartaoQueue = MutableLiveData<ArrayDeque<Cartao>>()

    private val baralhoRepository = BaralhoRepository()
    private val cartaoRepository = CartaoRepository()


    fun getAllCartoes() {

        viewModelScope.launch {
            val result = baralhoRepository.getCards(_baralhoOwner.value!!)

            result
                .onSuccess {
                        cartoes ->
                    _cartaoList.value = cartoes
                    setCartoesToRevisao()
                }
                .onFailure {
                    Log.e("Pasta", "Erro ao carregar pastas do firebase")
                    _cartaoList.value = emptyList()
                }

        }

    }


    private fun setCartoesToRevisao(){
        _cartaoQueue.value = ArrayDeque(_cartaoList.value ?: emptyList())

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
                onSucess()
            } else {
                onFailure()
            }
        } ?: onFailure()
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

    fun setBaralhoOwner(baralho: Baralho){
        baralhoOwner.value = baralho
        Log.d("RevisaoAC", "Defini Baralho em FOCO")
    }
    fun resetBaralhoOwner(){
        baralhoOwner.value = null
        Log.d("RevisaoAC", "Resetei Baralho em FOCO")
    }



}
