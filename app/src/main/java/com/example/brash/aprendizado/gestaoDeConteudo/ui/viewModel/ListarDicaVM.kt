package com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Baralho
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Cartao
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Dica

class ListarDicaVM(application: Application) : AndroidViewModel(application) {

    private var _teste = MutableLiveData<Boolean>()
    val teste get() = _teste

    private var _listDicaMsg = MutableLiveData<Int>()
    private var _dicaList = MutableLiveData<List<Dica>>()
    val listDicaMsg get() = _listDicaMsg
    val dicaList get() = _dicaList

    private var _dicaEmFoco = MutableLiveData<Dica>()
    val dicaEmFoco get() = _dicaEmFoco

    //private var _opcoesDeBusca = MutableLiveData<OpcoesDeBuscaBaralhoPublico>()
    //val opcoesDeBusca get() = _opcoesDeBusca
    private var _cartaoOwner = MutableLiveData<Cartao>()

    fun setCartaoOwner(cartao: Cartao){
        _cartaoOwner.value = cartao
    }

    fun getAllDicas() {
        //TODO:: requisitar do firebase

        _dicaList.value = listOf(
            Dica(texto =  "Alimentos"),
            Dica(texto =  "Frutas"),
            Dica(texto =  "VerdurasVerdurasAlimentosmorango"),
            Dica(texto =  "abacaxi"),
            Dica(texto =  "Alimentos"),
            Dica(texto =  "Frutas"),
            Dica(texto =  "morango"),
            Dica(texto =  "uva"),
            Dica(texto =  "Frutas"))

    }

    fun setDicaEmFoco(dica: Dica){
        dicaEmFoco.value = dica
        Log.d("HomeDialogs", "Defini Dica em FOCO")
    }

    /*
    fun updateOpcoesDeBusca(ordem: OrdemDeBuscaHome, filtro : FiltroDeBuscaHome){
        _opcoesDeBusca.value = OpcoesDeBuscaHome(ordem, filtro)
        Log.d("HomeDialogs", "Defini Pasta em FOCO")

        Log.d("HomeDialogs", ordem.toString())
        Log.d("HomeDialogs", filtro.toString())
    }*/

    fun criarDica(dica: Dica){
        //TODO:: Fazer a criação de dica do firebase também

        // request para atualizar dados
        getAllDicas()
    }
    fun editarDica(dica: Dica){
        //TODO:: Fazer a edição de dica do firebase também
        //TODO:: apenas requisitar se tiver ALGUMA informação diferente

        // request para atualizar dados
        getAllDicas()
    }
    fun excluirDica(dica: Dica){
        //TODO:: Fazer a exclusão de dica do firebase também

        // request para atualizar dados
        getAllDicas()
    }
}

/*

val dialog = OpcaoDialogFragment()
dialog.show(supportFragmentManager, "OpcaoDialog")
 */