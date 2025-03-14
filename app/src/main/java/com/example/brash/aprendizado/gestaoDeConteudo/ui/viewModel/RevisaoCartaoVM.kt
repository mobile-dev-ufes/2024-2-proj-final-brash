package com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel

import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Baralho

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
