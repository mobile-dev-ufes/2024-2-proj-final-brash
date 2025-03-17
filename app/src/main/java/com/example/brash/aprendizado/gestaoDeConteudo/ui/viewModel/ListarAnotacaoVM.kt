package com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.brash.R
import com.example.brash.aprendizado.gestaoDeConteudo.data.repository.AnotacaoRepository2
import com.example.brash.aprendizado.gestaoDeConteudo.data.repository.BaralhoRepository2
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Anotacao
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Baralho
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Dica
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.HomeAcListItem
import com.example.brash.nucleo.utils.UtilsFoos
import kotlinx.coroutines.launch


/**
 * ViewModel for managing and manipulating notes (Anotacao) in the application.
 *
 * This ViewModel is responsible for retrieving, creating, updating, and deleting notes. It also maintains the note list and the currently focused note.
 * Additionally, it manages the associated deck (Baralho) for each note.
 *
 * @param application The application context used to initialize the ViewModel.
 */
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

    private val baralhoRepository2 = BaralhoRepository2()
    private val anotacaoRepository2 = AnotacaoRepository2()

    /**
     * Sets the provided deck (Baralho) as the owner of the notes.
     * This helps in associating the notes with a particular deck.
     *
     * @param baralho The deck that owns the notes.
     */
    fun setBaralhoOwner(baralho: Baralho) {
        _baralhoOwner.value = baralho
    }
    //private var _opcoesDeBusca = MutableLiveData<OpcoesDeBuscaBaralhoPublico>()
    //val opcoesDeBusca get() = _opcoesDeBusca

    private fun getStringApplication(id: Int): String {
        return getApplication<Application>().getString(id)
    }


    /**
     * Retrieves all the notes from the data source (mocked for now).
     * Sets the initial list of notes.
     */
    fun getAllAnotacoes() {

        /*//TODO:: requisitar do firebase

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

        Log.d("ListaPastaAdapter", "DEFINIÇÃO DAS PASTAS")*/
        viewModelScope.launch {
            val result = baralhoRepository2.getNotes2(_baralhoOwner.value!!)

            result
                .onSuccess { anotacoes ->
                    _anotacaoList.value = anotacoes
                    sortAnotacaoList()
                }
                .onFailure { e ->
                    //UtilsFoos.showToast(getApplication(), e.toString())
                    Log.e("ListarCartaoVM", "${e}")
                    UtilsFoos.showToast(
                        getApplication(),
                        getStringApplication(R.string.erro_requisicao_banco_dados_firebase)
                    )
                    Log.e("Pasta", "Erro ao carregar pastas do firebase")

                    _anotacaoList.value = emptyList()
                }

        }
    }

    /**
     * Sets the provided note as the note currently in focus.
     * This helps in showing the relevant details for the selected note.
     *
     * @param anotacao The note to set in focus.
     */
    fun setAnotacaoEmFoco(anotacao: Anotacao) {
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

    /**
     * Creates a new note with the specified name and text.
     * This function will eventually be implemented to interact with Firebase for creating the note.
     *
     * @param nome The name of the note.
     * @param texto The text of the note.
     * @param onSuccess Callback to execute when the note is successfully created.
     */
    fun criarAnotacao(nome: String, texto: String, onSuccess: () -> Unit) {
        if (processaInfoAnotacao(nome, texto) && verificaAnotacaoNomeUnico(nome)) {
            val anotacao = Anotacao(nome = nome, texto = texto)
            viewModelScope.launch {
                val result = anotacaoRepository2.createNote2(_baralhoOwner.value!!, anotacao)
                result
                    .onSuccess { id ->
                        anotacao.idAnotacao = id
                        anotacao.baralho = _baralhoOwner.value!!
                        if (_anotacaoList.value == null) {
                            _anotacaoList.value = listOf(anotacao)
                        } else {
                            _anotacaoList.value = _anotacaoList.value!!.plus(anotacao)
                        }
                        sortAnotacaoList()
                        onSuccess()
                    }
                    .onFailure { e ->
                        //UtilsFoos.showToast(getApplication(), e.toString())
                        UtilsFoos.showToast(
                            getApplication(),
                            getStringApplication(R.string.erro_requisicao_banco_dados_firebase)
                        )
                        //UtilsFoos.showToast(getApplication(),"Ocorreu algum erro na criação do cartão:: ${e}")
                        Log.e("ListarDicaVM", "Ocorreu algum erro na criação do cartão:: ${e}")
                    }
            }
        }
    }

    private fun processaInfoAnotacao(nome: String, texto: String): Boolean {

        if (nome.isEmpty() || texto.isEmpty()) {
            UtilsFoos.showToast(
                getApplication(),
                getStringApplication(R.string.nuc_preencha_todos_campos)
            )
            return false
        } else if (false) { // verificacao de nome único
            return false
        }
        return true
    }

    private fun verificaAnotacaoNomeUnico(name: String): Boolean {

        for (anotacao in _anotacaoList.value.orEmpty()) {  // Usando orEmpty() para garantir que seja uma lista não-nula
            if (anotacao.nome == name) {
                UtilsFoos.showToast(
                    getApplication(),
                    getStringApplication(R.string.gtc_nome_unico_anotacao)
                )
                return false  // Retorna false caso o nome do baralho seja encontrado
            }

        }
        return true  // Retorna true caso o nome do baralho não seja encontrado em nenhuma pasta

    }

    /**
     * Edits an existing note by updating its name and text.
     * This function will eventually be implemented to interact with Firebase for updating the note.
     *
     * @param anotacao The note to edit.
     * @param nome The new name of the note.
     * @param texto The new text of the note.
     * @param onSuccess Callback to execute when the note is successfully edited.
     */
    fun editarAnotacao(anotacao: Anotacao, nome: String, texto: String, onSuccess: () -> Unit) {
        if (processaInfoAnotacao(nome, texto) && (anotacao.nome == nome || verificaAnotacaoNomeUnico(nome))) {
            viewModelScope.launch {
                val result = anotacaoRepository2.updateNote2(anotacao, nome, texto)
                result
                    .onSuccess {
                        onSuccess()
                        anotacao.nome = nome
                        anotacao.texto = texto
                        sortAnotacaoList()
                    }
                    .onFailure {
                        UtilsFoos.showToast(
                            getApplication(),
                            getStringApplication(R.string.erro_requisicao_banco_dados_firebase)
                        )
                        //UtilsFoos.showToast(getApplication(), "Ocorreu algum erro na edição do baralho: ${e}")
                        Log.e("criar Pasta debug", "Ocorreu algum erro na criação da pasta")
                    }
            }
        }
    }

    /**
     * Deletes the specified note.
     * This function will eventually be implemented to interact with Firebase for deleting the note.
     *
     * @param anotacao The note to delete.
     * @param onSuccess Callback to execute when the note is successfully deleted.
     */
    fun excluirAnotacao(anotacao: Anotacao, onSuccess: () -> Unit) {
        viewModelScope.launch{
            val result = anotacaoRepository2.deleteNote2(anotacao)

            result
                .onSuccess {
                    _anotacaoList.value = _anotacaoList.value?.toMutableList()?.apply {
                        removeAll {
                            it == anotacao
                        }
                    }?: emptyList()
                    onSuccess()
                    sortAnotacaoList()
                }
                .onFailure {
                    UtilsFoos.showToast(getApplication(), getStringApplication(R.string.erro_requisicao_banco_dados_firebase))
                    //UtilsFoos.showToast(getApplication(), "Ocorreu algum erro na edição do baralho")
                    Log.e("criar Pasta debug", "Ocorreu algum erro na criação da pasta")
                }
        }
    }

    private fun sortAnotacaoList() {
        _anotacaoList.value = _anotacaoList.value?.sortedBy { it.nome }
    }
}


/*

val dialog = OpcaoDialogFragment()
dialog.show(supportFragmentManager, "OpcaoDialog")
 */