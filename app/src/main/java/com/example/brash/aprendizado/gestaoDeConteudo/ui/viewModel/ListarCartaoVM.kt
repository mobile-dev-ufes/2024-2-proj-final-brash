package com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Baralho
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Cartao
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.HomeAcListItem
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.OpcoesDeBuscaHome
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Pasta
import com.example.brash.aprendizado.gestaoDeConteudo.utils.FiltroDeBuscaHome
import com.example.brash.aprendizado.gestaoDeConteudo.utils.OrdemDeBuscaHome
import java.lang.Exception

class ListarCartaoVM(application: Application) : AndroidViewModel(application) {

    private var _teste = MutableLiveData<Boolean>()
    val teste get() = _teste

    private var _listCartaoMsg = MutableLiveData<Int>()
    private var _cartaoList = MutableLiveData<List<Cartao>>()
    val listBaralhoPublicoMsg get() = _listCartaoMsg
    val cartaoList get() = _cartaoList

    private var _cartaoEmFoco = MutableLiveData<Cartao>()
    val cartaoEmFoco get() = _cartaoEmFoco

    private var _baralhoOwner = MutableLiveData<Baralho>()

    fun setBaralhoOwner(baralho: Baralho){
        _baralhoOwner.value = baralho
    }
    //private var _opcoesDeBusca = MutableLiveData<OpcoesDeBuscaBaralhoPublico>()
    //val opcoesDeBusca get() = _opcoesDeBusca

    fun getAllCartoes() {

        _cartaoList.value = listOf(
            Cartao(pergunta =  "Alimentos", resposta = "teste"),
            Cartao(pergunta =  "Frutas", resposta = "teste"),
            Cartao(pergunta =  "VerdurasVerdurasAlimentosmorango", resposta = "teste"),
            Cartao(pergunta =  "abacaxi"),
            Cartao(pergunta =  "Alimentos"),
            Cartao(pergunta =  "Frutas"),
            Cartao(pergunta =  "morango"),
            Cartao(pergunta =  "uva"),
            Cartao(pergunta =  "Frutas", resposta = "teste"))

        Log.d("ListaPastaAdapter", "DEFINIÇÃO DAS PASTAS")
    }

    fun setCartaoEmFoco(cartao: Cartao){
        cartaoEmFoco.value = cartao
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