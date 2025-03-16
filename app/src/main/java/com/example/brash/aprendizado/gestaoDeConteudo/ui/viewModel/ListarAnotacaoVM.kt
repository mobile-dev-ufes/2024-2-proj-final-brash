package com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Anotacao
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Baralho

class ListarAnotacaoVM(application: Application) : AndroidViewModel(application) {

    private var _teste = MutableLiveData<Boolean>()
    val teste get() = _teste

    private var _listAnotacaoMsg = MutableLiveData<Int>()
    private var _anotacaoList = MutableLiveData<List<Anotacao>>()
    val listAnotacaoMsg get() = _listAnotacaoMsg
    val anotacaoList get() = _anotacaoList

    private var _anotacaoEmFoco = MutableLiveData<Anotacao>()
    val anotacaoEmFoco get() = _anotacaoEmFoco

    private var _baralhoOwner = MutableLiveData<Baralho>()

    fun setBaralhoOwner(baralho: Baralho){
        _baralhoOwner.value = baralho
    }
    //private var _opcoesDeBusca = MutableLiveData<OpcoesDeBuscaBaralhoPublico>()
    //val opcoesDeBusca get() = _opcoesDeBusca

    fun getAllAnotacoes() {

        //TODO:: requisitar do firebase

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

    fun criarAnotacao(nome : String, texto : String, onSuccess : () -> Unit){
        //TODO:: Fazer a criação de anotação do firebase também
        //TODO:: apenas confirmar a criação se o nome for único para o usuário

        onSuccess()
        // request para atualizar dados
        //getAllAnotacoes()
    }
    fun editarAnotacao(anotacao: Anotacao, nome : String, texto : String, onSuccess : () -> Unit){
        //TODO:: Fazer a edição de anotação do firebase também
        //TODO:: apenas requisitar se tiver ALGUMA informação diferente
        //TODO:: apenas confirmar a mudança do nome se for único para o usuário, o restante pode sempre atualizar

        onSuccess()
        // request para atualizar dados
        //getAllAnotacoes()
    }
    fun excluirAnotacao(anotacao: Anotacao, onSuccess : () -> Unit){
        //TODO:: Fazer a exclusão de anotação do firebase também

        onSuccess()
        // request para atualizar dados
        //getAllAnotacoes()
    }

}

/*

val dialog = OpcaoDialogFragment()
dialog.show(supportFragmentManager, "OpcaoDialog")
 */