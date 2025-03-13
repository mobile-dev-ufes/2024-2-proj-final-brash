package com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Anotacao
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Baralho
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Cartao
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.HomeAcListItem
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.OpcoesDeBuscaHome
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Pasta
import com.example.brash.aprendizado.gestaoDeConteudo.utils.FiltroDeBuscaHome
import com.example.brash.aprendizado.gestaoDeConteudo.utils.OrdemDeBuscaHome
import java.lang.Exception

class ListarAnotacaoVM(application: Application) : AndroidViewModel(application) {

    private var _teste = MutableLiveData<Boolean>()
    val teste get() = _teste

    private var _listAnotacaoMsg = MutableLiveData<Int>()
    private var _anotacaoList = MutableLiveData<List<Anotacao>>()
    val listAnotacaoMsg get() = _listAnotacaoMsg
    val anotacaoList get() = _anotacaoList

    private var _anotacaoEmFoco = MutableLiveData<Anotacao>()
    val anotacaoEmFoco get() = _anotacaoEmFoco

    //private var _opcoesDeBusca = MutableLiveData<OpcoesDeBuscaBaralhoPublico>()
    //val opcoesDeBusca get() = _opcoesDeBusca

    fun getAllCartoes() {

        _anotacaoList.value = listOf(
            Anotacao(nome =  "Alimentos", texto = "teste"),
            Anotacao(nome =  "Frutas", texto = "teste"),
            Anotacao(nome =  "VerdurasVerdurasAlimentosmorango", texto = "teste"),
            Anotacao(nome =  "abacaxi"),
            Anotacao(nome =  "Alimentos"),
            Anotacao(nome =  "Frutas"),
            Anotacao(nome =  "morango"),
            Anotacao(nome =  "uva"),
            Anotacao(nome =  "Frutas", texto = "teste"))

        Log.d("ListaPastaAdapter", "DEFINIÇÃO DAS PASTAS")
    }

    fun setAnotacaoEmFoco(anotacao: Anotacao){
        anotacaoEmFoco.value = anotacao
        Log.d("HomeDialogs", "Defini Baralho em FOCO")
    }

    /*
    fun updateOpcoesDeBusca(ordem: OrdemDeBuscaHome, filtro : FiltroDeBuscaHome){
        _opcoesDeBusca.value = OpcoesDeBuscaHome(ordem, filtro)
        Log.d("HomeDialogs", "Defini Pasta em FOCO")

        Log.d("HomeDialogs", ordem.toString())
        Log.d("HomeDialogs", filtro.toString())
    }*/

}

/*

val dialog = OpcaoDialogFragment()
dialog.show(supportFragmentManager, "OpcaoDialog")
 */