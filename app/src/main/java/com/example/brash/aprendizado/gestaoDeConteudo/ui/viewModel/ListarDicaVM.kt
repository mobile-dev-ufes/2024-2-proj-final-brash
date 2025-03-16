package com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.brash.R
import com.example.brash.aprendizado.gestaoDeConteudo.data.repository.DicaRepository
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Baralho
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Cartao
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Dica
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.HomeAcListItem
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Pasta
import com.example.brash.nucleo.utils.UtilsFoos
import kotlinx.coroutines.launch

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

    private val dicaRepository = DicaRepository()

    private fun getStringApplication(id : Int) : String{
        return getApplication<Application>().getString(id)
    }


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

        sortDicaList()
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

    fun criarDica(texto: String, onSuccess : () -> Unit){
        if(processaInfoDica(texto)) {
            val dica = Dica(texto = texto)
            viewModelScope.launch {
                val result = dicaRepository.createHint(_cartaoOwner.value!!, dica)
                result
                    .onSuccess { id->
                        dica.idDica = id
                        dica.cartao = _cartaoOwner.value!!
                        if (_dicaList.value == null) {
                            _dicaList.value = listOf(dica)
                        } else {
                            _dicaList.value = _dicaList.value!!.plus(dica)
                        }
                        sortDicaList()
                        onSuccess()
                    }
                    .onFailure { e->
                        UtilsFoos.showToast(
                            getApplication(),
                            "Ocorreu algum erro na criação do cartão:: ${e}"
                        )
                        Log.e("criar Pasta debug", "Ocorreu algum erro na criação do cartão:: ${e}")
                    }
            }
        }
    }
    private fun processaInfoDica(texto: String) : Boolean{

        if(texto.isEmpty()){
            UtilsFoos.showToast(getApplication(), getStringApplication(R.string.nuc_preencha_todos_campos))
            return false
        }else if(false){ // verificacao de nome único
            return false
        }
        return true
    }
    fun editarDica(dica: Dica,texto: String, onSuccess : () -> Unit){
        if(processaInfoDica(texto)) {
            viewModelScope.launch {
                val result = dicaRepository.updateHint(dica, texto)
                result
                    .onSuccess {
                        if (_dicaList.value == null) {
                            _dicaList.value = listOf(dica)
                        } else {
                            _dicaList.value = _dicaList.value!!.plus(dica)
                        }
                        sortDicaList()
                        onSuccess()
                    }
                    .onFailure { e->
                        UtilsFoos.showToast(
                            getApplication(),
                            "Ocorreu algum erro na edição da dica:: ${e}"
                        )
                        Log.e("criar Pasta debug", "Ocorreu algum erro na edição da dica:: ${e}")
                    }
            }
        }
    }
    fun excluirDica(dica: Dica, onSuccess : () -> Unit){
        viewModelScope.launch{
            val result = dicaRepository.deleteHint(dica)

            result
                .onSuccess {
                    _dicaList.value = _dicaList.value?.toMutableList()?.apply {
                        removeAll {
                            it  == dica
                        }
                    }
                    val cartaoDono = dica.cartao
                    cartaoDono?.dica?.remove(dica)
                    onSuccess()
                    sortDicaList()
                }
                .onFailure { e->
                    UtilsFoos.showToast(getApplication(), "Ocorreu algum erro na exclusão da dica:: ${e}")
                    Log.e("criar Pasta debug", "Ocorreu algum erro na criação na exclusão da dica:: ${e}")
                }
        }
    }

    fun sortDicaList(){
        _dicaList.value = _dicaList.value?.sortedBy { it.texto }
    }
}

/*

val dialog = OpcaoDialogFragment()
dialog.show(supportFragmentManager, "OpcaoDialog")
 */