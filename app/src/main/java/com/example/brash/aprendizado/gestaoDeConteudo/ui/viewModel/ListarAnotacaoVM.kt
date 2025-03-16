package com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Anotacao
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Baralho


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

    /**
     * Sets the provided deck (Baralho) as the owner of the notes.
     * This helps in associating the notes with a particular deck.
     *
     * @param baralho The deck that owns the notes.
     */
    fun setBaralhoOwner(baralho: Baralho){
        _baralhoOwner.value = baralho
    }
    //private var _opcoesDeBusca = MutableLiveData<OpcoesDeBuscaBaralhoPublico>()
    //val opcoesDeBusca get() = _opcoesDeBusca

    /**
     * Retrieves all the notes from the data source (mocked for now).
     * Sets the initial list of notes.
     */
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

    /**
     * Sets the provided note as the note currently in focus.
     * This helps in showing the relevant details for the selected note.
     *
     * @param anotacao The note to set in focus.
     */
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

    /**
     * Creates a new note with the specified name and text.
     * This function will eventually be implemented to interact with Firebase for creating the note.
     *
     * @param nome The name of the note.
     * @param texto The text of the note.
     * @param onSuccess Callback to execute when the note is successfully created.
     */
    fun criarAnotacao(nome : String, texto : String, onSuccess : () -> Unit){
        //TODO:: Fazer a criação de anotação do firebase também
        //TODO:: apenas confirmar a criação se o nome for único para o usuário

        onSuccess()
        // request para atualizar dados
        //getAllAnotacoes()
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
    fun editarAnotacao(anotacao: Anotacao, nome : String, texto : String, onSuccess : () -> Unit){
        //TODO:: Fazer a edição de anotação do firebase também
        //TODO:: apenas requisitar se tiver ALGUMA informação diferente
        //TODO:: apenas confirmar a mudança do nome se for único para o usuário, o restante pode sempre atualizar

        onSuccess()
        // request para atualizar dados
        //getAllAnotacoes()
    }

    /**
     * Deletes the specified note.
     * This function will eventually be implemented to interact with Firebase for deleting the note.
     *
     * @param anotacao The note to delete.
     * @param onSuccess Callback to execute when the note is successfully deleted.
     */
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