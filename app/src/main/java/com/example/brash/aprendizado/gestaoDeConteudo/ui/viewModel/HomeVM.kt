package com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Baralho
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.HomeAcListItem
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.OpcoesDeBuscaHome
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Pasta
import com.example.brash.aprendizado.gestaoDeConteudo.utils.FiltroDeBuscaHome
import com.example.brash.aprendizado.gestaoDeConteudo.utils.OrdemDeBuscaHome
import java.lang.Exception

class HomeVM(application: Application) : AndroidViewModel(application) {

    private var _teste = MutableLiveData<Boolean>()
    val teste get() = _teste

    private var _listPastaMsg = MutableLiveData<Int>()
    private var _pastaList = MutableLiveData<List<Pasta>>()
    val listPastaMsg get() = _listPastaMsg
    val pastaList get() = _pastaList

    private var _listHomeAcListItemMsg = MutableLiveData<Int>()
    private var _homeAcListItemList = MutableLiveData<List<HomeAcListItem>>()
    val listHomeAcListItemMsg get() = _listHomeAcListItemMsg
    val homeAcListItemList get()  = _homeAcListItemList

    private var _pastaEmFoco = MutableLiveData<Pasta>()
    private var _baralhoEmFoco = MutableLiveData<Baralho>()

    val pastaEmFoco get() = _pastaEmFoco
    val baralhoEmFoco get() = _baralhoEmFoco

    private var _opcoesDeBusca = MutableLiveData<OpcoesDeBuscaHome>()
    val opcoesDeBusca get() = _opcoesDeBusca

    val _pastaEmMover = MutableLiveData<Pasta?>()
    val pastaEmMover get() = _pastaEmMover


    fun getAllPastas() {

        _pastaList.value = listOf(
            Pasta(nome =  "Alimentos"),
            Pasta(nome =  "Frutas", idPasta = 1),
            Pasta(nome =  "VerdurasVerdurasAlimentosmorango"),
            Pasta(nome =  "abacaxi"),
            Pasta(nome =  "Alimentos"),
            Pasta(nome =  "Frutas"),
            Pasta(nome =  "morango"),
            Pasta(nome =  "uva"),
            Pasta(nome =  "Frutas"))

        Log.d("ListaPastaAdapter", "DEFINIÇÃO DAS PASTAS")
    }

    fun getAllHomeAcListItem(){


        val p = Pasta(nome =  "Eletrônicos", idPasta =1 )

        val listaBaralho = mutableListOf(
            Baralho(nome = "Celular", pasta = p),
            Baralho(nome = "Notebook Teste do tamanhoa aaaaaaabbbbbb", pasta = p),
            Baralho(nome = "Fone de ouvido", pasta = p)
        )

        p.baralhos = listaBaralho

        var listaHomeAcListItem = listOf<HomeAcListItem>(
            HomeAcListItem.HomeAcPastaItem(pasta = Pasta(nome = "Roupas ")),
            HomeAcListItem.HomeAcPastaItem(isExpanded = true, pasta = p),
            HomeAcListItem.HomeAcPastaItem(pasta = Pasta(nome =  "Alimentos")),
            HomeAcListItem.HomeAcPastaItem(pasta = Pasta(nome =  "Frutas")),
            HomeAcListItem.HomeAcPastaItem(pasta = Pasta(nome =  "Verduras")),
            HomeAcListItem.HomeAcPastaItem(pasta = Pasta(nome =  "abacaxi")),
            HomeAcListItem.HomeAcPastaItem(pasta = Pasta(nome =  "4")),
            HomeAcListItem.HomeAcPastaItem(pasta = Pasta(nome =  "5")),
            HomeAcListItem.HomeAcPastaItem(pasta = Pasta(nome =  "6")),
            HomeAcListItem.HomeAcBaralhoItem(Baralho(nome = "Fone de ouvido"))
        )


        _homeAcListItemList.value = listaHomeAcListItem
    }

    fun setPastaEmFoco(pasta: Pasta){
        pastaEmFoco.value = pasta
        Log.d("HomeDialogs", "Defini Pasta em FOCO")
    }
    fun resetPastaEmFoco(){
        pastaEmFoco.value = null
        Log.d("HomeDialogs", "Resetei Pasta em FOCO")
    }

    fun setBaralhoEmFoco(baralho: Baralho){
        baralhoEmFoco.value = baralho
        Log.d("HomeDialogs", "Defini Baralho em FOCO")
    }
    fun resetBaralhoEmFoco(){
        baralhoEmFoco.value = null
        Log.d("HomeDialogs", "Resetei Baralho em FOCO")
    }

    fun updateOpcoesDeBusca(ordem: OrdemDeBuscaHome, filtro : FiltroDeBuscaHome){
        _opcoesDeBusca.value = OpcoesDeBuscaHome(ordem, filtro)
        Log.d("HomeDialogs", "Defini Pasta em FOCO")

        Log.d("HomeDialogs", ordem.toString())
        Log.d("HomeDialogs", filtro.toString())
    }

    fun setPastaEmMover(pasta: Pasta){
        _pastaEmMover.value = pasta
    }
    fun resetPastaEmMover(){
        _pastaEmMover.value = null
    }
}


/*

val dialog = OpcaoDialogFragment()
dialog.show(supportFragmentManager, "OpcaoDialog")
 */