package com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.brash.R
import com.example.brash.aprendizado.gestaoDeConteudo.data.repository.CartaoRepository
import com.example.brash.aprendizado.gestaoDeConteudo.data.repository.CartaoRepository2
import com.example.brash.aprendizado.gestaoDeConteudo.data.repository.DicaRepository
import com.example.brash.aprendizado.gestaoDeConteudo.data.repository.DicaRepository2
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Cartao
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Dica
import com.example.brash.nucleo.utils.UtilsFoos
import kotlinx.coroutines.launch

/**
 * ViewModel responsible for managing the list of hints (Dicas) related to a card (Cartao).
 * Provides methods for fetching, creating, editing, and deleting hints.
 *
 * @param application The application context used to access resources and the repository.
 */
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

    private val dicaRepository = DicaRepository2()
    private val cartaoRepository = CartaoRepository2()

    /**
     * Gets the string resource based on the provided resource ID.
     *
     * @param id The resource ID.
     * @return The string corresponding to the provided resource ID.
     */
    private fun getStringApplication(id : Int) : String{
        return getApplication<Application>().getString(id)
    }

    /**
     * Sets the card (Cartao) that owns the hints.
     *
     * @param cartao The card to be set as the owner.
     */
    fun setCartaoOwner(cartao: Cartao){
        _cartaoOwner.value = cartao
    }

    /**
     * Fetches all hints related to the current card (Cartao) from the repository.
     * Updates the list of hints and sorts them.
     */
    fun getAllDicas() {
        viewModelScope.launch {
            val result = cartaoRepository.getHints2(_cartaoOwner.value!!)

            result
                .onSuccess {
                        dicas ->
                    // A operação foi bem-sucedida, faça algo com as pastas (folders)

                    _dicaList.value = dicas
                    sortDicaList()
                }
                .onFailure { e->
                    UtilsFoos.showToast(getApplication(), e.toString())
                    Log.e("ListarDicaVM", "${e}")
                    UtilsFoos.showToast(getApplication(), getStringApplication(R.string.erro_requisicao_banco_dados_firebase))
                    Log.e("Pasta", "Erro ao carregar pastas do firebase")

                    _dicaList.value = emptyList()
                }

        }
    }

    /**
     * Sets the focused hint (Dica) to display its details.
     *
     * @param dica The hint to be set as the focused one.
     */
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

    /**
     * Creates a new hint with the given text and saves it to the repository.
     * Updates the list of hints upon success.
     *
     * @param texto The text of the new hint.
     * @param onSuccess The callback to be executed after the hint is successfully created.
     */
    fun criarDica(texto: String, onSuccess : () -> Unit){
        if(processaInfoDica(texto)) {
            val dica = Dica(texto = texto)
            viewModelScope.launch {
                val result = dicaRepository.createHint2(_cartaoOwner.value!!, dica)
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
                        UtilsFoos.showToast(getApplication(), e.toString())
                        UtilsFoos.showToast(getApplication(), getStringApplication(R.string.erro_requisicao_banco_dados_firebase))
                        //UtilsFoos.showToast(getApplication(),"Ocorreu algum erro na criação do cartão:: ${e}")
                        Log.e("ListarDicaVM", "Ocorreu algum erro na criação do cartão:: ${e}")
                    }
            }
        }
    }
    /**
     * Processes the hint text to ensure it meets the validation criteria.
     *
     * @param texto The hint text to be validated.
     * @return True if the text is valid, false otherwise.
     */
    private fun processaInfoDica(texto: String) : Boolean{

        if(texto.isEmpty()){
            UtilsFoos.showToast(getApplication(), getStringApplication(R.string.nuc_preencha_todos_campos))
            return false
        }else if(false){ // verificacao de nome único
            return false
        }
        return true
    }
    /**
     * Edits an existing hint with the new text and updates it in the repository.
     * Updates the list of hints upon success.
     *
     * @param dica The hint to be edited.
     * @param texto The new text for the hint.
     * @param onSuccess The callback to be executed after the hint is successfully updated.
     */
    fun editarDica(dica: Dica,texto: String, onSuccess : () -> Unit){
        if(processaInfoDica(texto)) {
            viewModelScope.launch {
                val result = dicaRepository.updateHint2(dica, texto)
                result
                    .onSuccess {
                        dica.texto = texto
                        sortDicaList()
                        onSuccess()
                    }
                    .onFailure { e->
                        UtilsFoos.showToast(getApplication(), getStringApplication(R.string.erro_requisicao_banco_dados_firebase))
                        //UtilsFoos.showToast(getApplication(),"Ocorreu algum erro na edição da dica:: ${e}")
                        Log.e("criar Pasta debug", "Ocorreu algum erro na edição da dica:: ${e}")
                    }
            }
        }
    }

    /**
     * Deletes a hint from the repository and updates the list of hints.
     * If the list becomes empty, it sets the list to an empty list.
     *
     * @param dica The hint to be deleted.
     * @param onSuccess The callback to be executed after the hint is successfully deleted.
     */
    fun excluirDica(dica: Dica, onSuccess : () -> Unit){
        viewModelScope.launch{
            val result = dicaRepository.deleteHint2(dica)

            result
                .onSuccess {
                    _dicaList.value?.toMutableList()?.let { listaMutable ->
                        listaMutable.remove(dica)
                        // Se a lista estiver vazia após a remoção, podemos definir o LiveData como uma lista vazia.
                        _dicaList.value = if (listaMutable.isEmpty()) emptyList() else listaMutable
                    }

                    val cartaoDono = dica.cartao
                    cartaoDono?.dica?.remove(dica)
                    onSuccess()
                    sortDicaList()
                }
                .onFailure { e->
                    UtilsFoos.showToast(getApplication(), getStringApplication(R.string.erro_requisicao_banco_dados_firebase))
                    //
                    // UtilsFoos.showToast(getApplication(), "Ocorreu algum erro na exclusão da dica:: ${e}")
                    Log.e("criar Pasta debug", "Ocorreu algum erro na criação na exclusão da dica:: ${e}")
                }
        }
    }

    /**
     * Sorts the list of hints alphabetically by the hint text.
     */
    fun sortDicaList(){
        _dicaList.value = _dicaList.value?.sortedBy { it.texto }
    }
}

/*

val dialog = OpcaoDialogFragment()
dialog.show(supportFragmentManager, "OpcaoDialog")
 */