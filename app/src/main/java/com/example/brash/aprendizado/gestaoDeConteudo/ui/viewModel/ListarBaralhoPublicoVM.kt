package com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Anotacao
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Baralho
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.HomeAcListItem
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.OpcoesDeBuscaHome
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Pasta
import com.example.brash.aprendizado.gestaoDeConteudo.utils.FiltroDeBuscaHome
import com.example.brash.aprendizado.gestaoDeConteudo.utils.OrdemDeBuscaHome
import java.lang.Exception

class ListarBaralhoPublicoVM(application: Application) : AndroidViewModel(application) {

    private var _teste = MutableLiveData<Boolean>()
    val teste get() = _teste

    private var _listBaralhoPublicoMsg = MutableLiveData<Int>()
    private var _baralhoPublicoList = MutableLiveData<List<Baralho>>()
    val listBaralhoPublicoMsg get() = _listBaralhoPublicoMsg
    val baralhoPublicoList get() = _baralhoPublicoList

    private var _baralhoPublicoEmFoco = MutableLiveData<Baralho>()
    val baralhoPublicoEmFoco get() = _baralhoPublicoEmFoco

    //private var _opcoesDeBusca = MutableLiveData<OpcoesDeBuscaBaralhoPublico>()
    //val opcoesDeBusca get() = _opcoesDeBusca

    fun getAllBaralhosPublicos() {

        _baralhoPublicoList.value = listOf(
            Baralho(nome =  "Alimentos"),
            Baralho(nome =  "Frutas"),
            Baralho(nome =  "VerdurasVerdurasAlimentosmorango"),
            Baralho(nome =  "abacaxi"),
            Baralho(nome =  "Alimentos"),
            Baralho(nome =  "Frutas"),
            Baralho(nome =  "morango"),
            Baralho(nome =  "uva"),
            Baralho(nome =  "Frutas"))

        Log.d("ListaPastaAdapter", "DEFINIÇÃO DAS PASTAS")
    }

    fun setBaralhoPublicoEmFoco(baralho: Baralho){
        baralhoPublicoEmFoco.value = baralho
        Log.d("HomeDialogs", "Defini Baralho em FOCO")
    }

    /*
    fun updateOpcoesDeBusca(ordem: OrdemDeBuscaHome, filtro : FiltroDeBuscaHome){
        _opcoesDeBusca.value = OpcoesDeBuscaHome(ordem, filtro)
        Log.d("HomeDialogs", "Defini Pasta em FOCO")

        Log.d("HomeDialogs", ordem.toString())
        Log.d("HomeDialogs", filtro.toString())
    }*/

    fun importarBaralhoPublico(baralho: Baralho){
        //TODO:: Fazer a requisição do firebase copiar esse baralho para a root do usuário

    }

}


/*

val dialog = OpcaoDialogFragment()
dialog.show(supportFragmentManager, "OpcaoDialog")
 */