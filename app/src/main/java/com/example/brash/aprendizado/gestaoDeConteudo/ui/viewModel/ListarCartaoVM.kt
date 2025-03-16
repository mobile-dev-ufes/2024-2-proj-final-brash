package com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.brash.R
import com.example.brash.aprendizado.gestaoDeConteudo.data.repository.BaralhoRepository
import com.example.brash.aprendizado.gestaoDeConteudo.data.repository.CartaoRepository
import com.example.brash.aprendizado.gestaoDeConteudo.data.repository.PastaRepository
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Baralho
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Cartao
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.HomeAcListItem
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.OpcoesDeBuscaListarCartao
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Pasta
import com.example.brash.aprendizado.gestaoDeConteudo.utils.FiltroDeBuscaListarCartao
import com.example.brash.nucleo.utils.UtilsFoos
import kotlinx.coroutines.launch
import java.lang.Exception

class ListarCartaoVM(application: Application) : AndroidViewModel(application) {

    private var _teste = MutableLiveData<Boolean>()
    val teste get() = _teste

    private var _listCartaoMsg = MutableLiveData<Int>()
    private var _cartaoList = MutableLiveData<List<Cartao>>()
    val listBaralhoPublicoMsg get() = _listCartaoMsg

    private var _cartaoListSort = MutableLiveData<List<Cartao>>(emptyList())
    val cartaoListSort get() = _cartaoListSort

    private var _cartaoEmFoco = MutableLiveData<Cartao>()
    val cartaoEmFoco get() = _cartaoEmFoco

    private var _baralhoOwner = MutableLiveData<Baralho>()

    private val _textoBusca = MutableLiveData<String>("")
    private var _opcoesDeBusca = MutableLiveData<OpcoesDeBuscaListarCartao>(OpcoesDeBuscaListarCartao())
    val opcoesDeBusca get() = _opcoesDeBusca

    private val cartaoRepository = CartaoRepository()

    private val baralhoRepository = BaralhoRepository()

    fun setBaralhoOwner(baralho: Baralho){
        _baralhoOwner.value = baralho
    }
    //private var _opcoesDeBusca = MutableLiveData<OpcoesDeBuscaBaralhoPublico>()
    //val opcoesDeBusca get() = _opcoesDeBusca

    private fun getStringApplication(id : Int) : String{
        return getApplication<Application>().getString(id)
    }

    fun getAllCartoes() {
        //TODO:: requisitar do firebase

        /*_cartaoList.value = listOf(
            Cartao(pergunta =  "Alimentos", resposta = "teste"),
            Cartao(pergunta =  "Frutas", resposta = "Cartoes sao legais"),
            Cartao(pergunta =  "VerdurasVerdurasAlimentosmorango", resposta = "Um exemplo"),
            Cartao(pergunta =  "abacaxi"),
            Cartao(pergunta =  "Alimentos"),
            Cartao(pergunta =  "Frutas"),
            Cartao(pergunta =  "morango"),
            Cartao(pergunta =  "uva"),
            Cartao(pergunta =  "Frutas", resposta = "teste"))

        Log.d("ListaPastaAdapter", "DEFINIÇÃO DAS PASTAS")

        updateFilterCartaoList("")*/

        viewModelScope.launch {
            val result = baralhoRepository.getCards(_baralhoOwner.value!!)

            result
                .onSuccess {
                        cartoes ->
                    // A operação foi bem-sucedida, faça algo com as pastas (folders)
                    Log.d("Cartao", "Cartoes carregados: $cartoes")

                    _cartaoList.value = cartoes
                    updateFilterCartaoList(_textoBusca.value!!)
                }
                .onFailure {
                    Log.e("Pasta", "Erro ao carregar pastas do firebase")

                    _cartaoList.value = emptyList()
                }

        }

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

    fun criarCartao(pergunta: String, resposta: String, onSuccess : () -> Unit){

        if(processaInfoCartao(pergunta, resposta)) {
            val cartao = Cartao(pergunta = pergunta, resposta = resposta)
            viewModelScope.launch {
                val result = cartaoRepository.createCard(_baralhoOwner.value!!, cartao)
                result
                    .onSuccess {
                        if (_cartaoList.value == null) {
                            _cartaoList.value = listOf(cartao)
                        } else {
                            _cartaoList.value = _cartaoList.value!!.plus(cartao)
                        }

                        updateFilterCartaoList(_textoBusca.value ?: "")
                        onSuccess()
                    }
                    .onFailure {
                        UtilsFoos.showToast(
                            getApplication(),
                            "Ocorreu algum erro na criação da pasta"
                        )
                        Log.e("criar Pasta debug", "Ocorreu algum erro na criação da pasta")
                    }
            }
        }
        //getAllCartoes()
    }
    private fun processaInfoCartao(pergunta: String, resposta: String) : Boolean{

        if(pergunta.isEmpty() || resposta.isEmpty()){
            UtilsFoos.showToast(getApplication(), getStringApplication(R.string.nuc_preencha_todos_campos))
            return false
        }else if(false){ // verificacao de nome único
            return false
        }
        return true
    }
    fun editarCartao(cartao: Cartao,pergunta: String, resposta: String, onSuccess : () -> Unit){
        //TODO:: Fazer a edição de cartão do firebase também
        //TODO:: apenas requisitar se tiver ALGUMA informação diferente
        // request para atualizar dados
        viewModelScope.launch{/*
            val result = cartaoRepository.updateCard(cartao, pergunta, resposta)

            result
                .onSuccess {
                    cartao.pergunta = pergunta
                    cartao.resposta = resposta
                    updateFilterCartaoList(_textoBusca.value?: "")
                    onSuccess()
                }
                .onFailure {
                    UtilsFoos.showToast(getApplication(), "Ocorreu algum erro na edição do baralho")
                    Log.e("criar Pasta debug", "Ocorreu algum erro na criação da pasta")
                }*/
        }
        //getAllCartoes()
    }
    fun excluirCartao(cartao: Cartao, onSuccess : () -> Unit){
        viewModelScope.launch{
            val result = cartaoRepository.deleteCard(cartao)

            result
                .onSuccess {
                    _cartaoList.value = _cartaoList.value?.toMutableList()?.apply {
                        removeAll {
                            it  == cartao
                        }
                    }
                    updateFilterCartaoList(_textoBusca.value?: "")
                    onSuccess()
                }
                .onFailure {
                    UtilsFoos.showToast(getApplication(), "Ocorreu algum erro na exclusão do cartão")
                    Log.e("criar Pasta debug", "Ocorreu algum erro na criação da pasta")
                }
        }
    }

    fun updateOpcoesDeBusca(filtro : FiltroDeBuscaListarCartao){
        _opcoesDeBusca.value = OpcoesDeBuscaListarCartao( filtro)
        Log.d("HomeDialogs", "Defini Pasta em FOCO")

        Log.d("HomeDialogs", filtro.toString())
    }

    fun updateFilterCartaoList(busca: String){
        _textoBusca.value = busca

        if(_cartaoList.value == null){
            _cartaoListSort.value = emptyList()
        }
        else if(busca.isEmpty()){
            _cartaoListSort.value = _cartaoList.value
        }
        else if(_opcoesDeBusca.value!!.filtrar == FiltroDeBuscaListarCartao.PERGUNTA){
            _cartaoListSort.value = _cartaoList.value!!.filter{it.pergunta.contains(busca, ignoreCase = true)}
        }
        else{
            _cartaoListSort.value = _cartaoList.value!!.filter{it.resposta.contains(busca, ignoreCase = true)}
        }

        // Garantindo que _cartaoListSort nunca seja nulo
        if (_cartaoListSort.value == null) {
            _cartaoListSort.value = emptyList()
        }
    }
}

/*

val dialog = OpcaoDialogFragment()
dialog.show(supportFragmentManager, "OpcaoDialog")
 */