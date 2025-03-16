package com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.brash.aprendizado.gestaoDeConteudo.data.repository.BaralhoRepository
import com.example.brash.aprendizado.gestaoDeConteudo.data.repository.BaralhoRepository2
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Baralho

/**
 * ViewModel for managing and manipulating public decks in the application.
 *
 * This ViewModel is responsible for retrieving and handling the list of public decks, updating the filter for public decks,
 * and interacting with the user interface for displaying relevant deck information.
 *
 * @param application The application context used to initialize the ViewModel.
 */
class ListarBaralhoPublicoVM(application: Application) : AndroidViewModel(application) {

    private var _teste = MutableLiveData<Boolean>()
    val teste get() = _teste

    private var _listBaralhoPublicoMsg = MutableLiveData<Int>()
    private var _baralhoPublicoList = MutableLiveData<List<Baralho>>()
    val listBaralhoPublicoMsg get() = _listBaralhoPublicoMsg

    private var _baralhoPublicoListSort = MutableLiveData<List<Baralho>>(emptyList())
    val baralhoPublicoListSort get() = _baralhoPublicoListSort

    private var _baralhoPublicoEmFoco = MutableLiveData<Baralho>()
    val baralhoPublicoEmFoco get() = _baralhoPublicoEmFoco

    val baralhoRepository2 = BaralhoRepository2()
    /**
     * Retrieves all public decks from the data source (mocked for now).
     * Sets the initial list of public decks and triggers the filter update.
     */
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
        updateFilterBaralhoPublicoList("")
    }

    /**
     * Sets the provided public deck as the deck currently in focus.
     * This helps in showing the relevant details for the selected deck.
     *
     * @param baralho The public deck to set in focus.
     */
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

    /**
     * Imports a public deck by copying it to the user's account.
     * This method simulates the process of importing a deck from the public list.
     *
     * @param baralho The public deck to be imported.
     * @param novoNome The new name to assign to the imported deck.
     * @param onSuccess Callback to execute when the import is successful.
     */
    fun importarBaralhoPublico(baralho: Baralho, novoNome: String, onSuccess : () -> Unit){
        //TODO:: Fazer a requisição do firebase copiar esse baralho para a root do usuário
        onSuccess()

    }

    /**
     * Updates the list of public decks by applying the given filter.
     * If the filter is empty, the original list is shown. If the filter contains text,
     * the list is filtered to show only decks whose names contain the text.
     *
     * @param filtro The filter string to apply on the list of public decks.
     */
    fun updateFilterBaralhoPublicoList(filtro: String){

        if(filtro.isEmpty()){
            _baralhoPublicoListSort.value = _baralhoPublicoList.value!!
        }
        else{
            _baralhoPublicoListSort.value = _baralhoPublicoList.value!!.filter{it.nome.contains(filtro, ignoreCase = true)}
        }

        // Garantindo que _cartaoListSort nunca seja nulo
        if (_baralhoPublicoListSort.value == null) {
            _baralhoPublicoListSort.value = emptyList()
        }

    }


}


/*

val dialog = OpcaoDialogFragment()
dialog.show(supportFragmentManager, "OpcaoDialog")
 */