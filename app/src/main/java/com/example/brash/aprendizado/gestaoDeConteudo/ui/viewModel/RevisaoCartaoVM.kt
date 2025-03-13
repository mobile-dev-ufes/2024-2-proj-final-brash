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

    private var _baralhoEmFoco = MutableLiveData<Baralho>()
    val baralhoEmFoco get() = _baralhoEmFoco

    fun showAnswers(){
        _buttonShowAnswersVisibility.value = View.GONE
        _linearLayoutButtonsAnswerVisibility.value = View.VISIBLE
    }

    fun hideAnswers(){
        _buttonShowAnswersVisibility.value = View.VISIBLE
        _linearLayoutButtonsAnswerVisibility.value = View.GONE
    }

    fun setBaralhoEmFoco(baralho: Baralho){
        baralhoEmFoco.value = baralho
        Log.d("RevisaoAC", "Defini Baralho em FOCO")
    }
    fun resetBaralhoEmFoco(){
        baralhoEmFoco.value = null
        Log.d("RevisaoAC", "Resetei Baralho em FOCO")
    }



}
