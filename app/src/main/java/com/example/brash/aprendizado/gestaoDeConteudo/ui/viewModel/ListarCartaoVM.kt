package com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.brash.R
import com.example.brash.aprendizado.gestaoDeConteudo.data.repository.BaralhoRepository
import com.example.brash.aprendizado.gestaoDeConteudo.data.repository.BaralhoRepository2
import com.example.brash.aprendizado.gestaoDeConteudo.data.repository.CartaoRepository
import com.example.brash.aprendizado.gestaoDeConteudo.data.repository.CartaoRepository2
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Baralho
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Cartao
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.OpcoesDeBuscaListarCartao
import com.example.brash.aprendizado.gestaoDeConteudo.utils.FiltroDeBuscaListarCartao
import com.example.brash.nucleo.utils.UtilsFoos
import kotlinx.coroutines.launch

/**
 * ViewModel responsible for managing the state and business logic related to Cartão (Card) entities.
 * It handles operations such as retrieving, creating, updating, deleting, and filtering cards
 * within a specific "Baralho" (Deck).
 *
 * @param application The application instance used for accessing resources and context.
 */
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

    private val cartaoRepository = CartaoRepository2()

    private val baralhoRepository = BaralhoRepository2()

    /**
     * Sets the baralho (deck) that owns the list of cards.
     *
     * @param baralho The deck that contains the cards to be managed.
     */
    fun setBaralhoOwner(baralho: Baralho){
        _baralhoOwner.value = baralho
    }
    //private var _opcoesDeBusca = MutableLiveData<OpcoesDeBuscaBaralhoPublico>()
    //val opcoesDeBusca get() = _opcoesDeBusca

    private fun getStringApplication(id : Int) : String{
        return getApplication<Application>().getString(id)
    }

    /**
     * Retrieves all cards associated with the currently set baralho (deck).
     * The cards are fetched from the Firebase repository and filtered based on the search text.
     */
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
            val result = baralhoRepository.getCards2(_baralhoOwner.value!!)

            result
                .onSuccess {
                        cartoes ->
                    _cartaoList.value = cartoes
                    updateFilterCartaoList(_textoBusca.value!!)
                }
                .onFailure {e->
                    UtilsFoos.showToast(getApplication(), e.toString())
                    Log.e("ListarCartaoVM", "${e}")
                    UtilsFoos.showToast(getApplication(), getStringApplication(R.string.erro_requisicao_banco_dados_firebase))
                    Log.e("Pasta", "Erro ao carregar pastas do firebase")

                    _cartaoList.value = emptyList()
                }

        }

    }

    /**
     * Sets the card that is currently in focus.
     *
     * @param cartao The card to set as the current focus.
     */
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

    /**
     * Creates a new card with the specified question and answer.
     *
     * @param pergunta The question text for the card.
     * @param resposta The answer text for the card.
     * @param onSuccess A lambda function to be invoked upon successful creation.
     */
    fun criarCartao(pergunta: String, resposta: String, onSuccess : () -> Unit){

        if(processaInfoCartao(pergunta, resposta)) {
            val cartao = Cartao(pergunta = pergunta, resposta = resposta)
            viewModelScope.launch {
                val result = cartaoRepository.createCard2(_baralhoOwner.value!!, cartao)
                result
                    .onSuccess { id->
                        cartao.idCartao = id
                        cartao.baralho = _baralhoOwner.value!!
                        if (_cartaoList.value == null) {
                            _cartaoList.value = listOf(cartao)
                        } else {
                            _cartaoList.value = _cartaoList.value!!.plus(cartao)
                        }
                        updateFilterCartaoList(_textoBusca.value ?: "")
                        onSuccess()
                    }
                    .onFailure {e->
                        UtilsFoos.showToast(getApplication(), getStringApplication(R.string.erro_requisicao_banco_dados_firebase))
                        //UtilsFoos.showToast(getApplication(),"Ocorreu algum erro na criação do cartão:: ${e}")
                        Log.e("criar Pasta debug", "Ocorreu algum erro na criação do cartão:: ${e}")
                    }
            }
        }
        //getAllCartoes()
    }

    /**
     * Processes the card information to ensure it is valid before creating or editing a card.
     *
     * @param pergunta The question text for the card.
     * @param resposta The answer text for the card.
     * @return A boolean indicating whether the provided information is valid.
     */
    private fun processaInfoCartao(pergunta: String, resposta: String) : Boolean{

        if(pergunta.isEmpty() || resposta.isEmpty()){
            UtilsFoos.showToast(getApplication(), getStringApplication(R.string.nuc_preencha_todos_campos))
            return false
        }else if(false){ // verificacao de nome único
            return false
        }
        return true
    }

    /**
     * Edits an existing card with new question and answer text.
     *
     * @param cartao The card to edit.
     * @param pergunta The updated question text for the card.
     * @param resposta The updated answer text for the card.
     * @param onSuccess A lambda function to be invoked upon successful update.
     */
    fun editarCartao(cartao: Cartao,pergunta: String, resposta: String, onSuccess : () -> Unit){
        //TODO:: Fazer a edição de cartão do firebase também
        //TODO:: apenas requisitar se tiver ALGUMA informação diferente
        // request para atualizar dados
        if(processaInfoCartao(pergunta, resposta)) {
            viewModelScope.launch{
                val result = cartaoRepository.updateCardQA2(cartao, pergunta, resposta)

                result
                    .onSuccess {
                        cartao.pergunta = pergunta
                        cartao.resposta = resposta
                        updateFilterCartaoList(_textoBusca.value?: "")
                        onSuccess()
                    }
                    .onFailure {e->
                        UtilsFoos.showToast(getApplication(), getStringApplication(R.string.erro_requisicao_banco_dados_firebase))
                        //UtilsFoos.showToast(getApplication(), "Ocorreu algum erro na edição do cartão:: ${e}")
                        Log.e("criar Pasta debug", "Ocorreu algum erro na criação da pasta:: ${e}")
                    }
            }
        }
        //getAllCartoes()
    }

    /**
     * Deletes a specified card.
     *
     * @param cartao The card to delete.
     * @param onSuccess A lambda function to be invoked upon successful deletion.
     */
    fun excluirCartao(cartao: Cartao, onSuccess : () -> Unit){
        viewModelScope.launch{
            val result = cartaoRepository.deleteCard2(cartao)

            result
                .onSuccess {
                    _cartaoList.value = _cartaoList.value?.toMutableList()?.apply  {
                        val index = indexOf(cartao)
                        if (index != -1) {
                            removeAt(index) // Remove pelo índice
                        }
                    } ?: emptyList()
                    updateFilterCartaoList(_textoBusca.value?: "")
                    onSuccess()
                }
                .onFailure {
                    UtilsFoos.showToast(getApplication(), getStringApplication(R.string.erro_requisicao_banco_dados_firebase))
                    //UtilsFoos.showToast(getApplication(), "Ocorreu algum erro na exclusão do cartão")
                    Log.e("criar Pasta debug", "Ocorreu algum erro na criação da pasta")
                }
        }
    }

    /**
     * Updates the search filter options for the card list.
     *
     * @param filtro The filter options to apply to the card list.
     */
    fun updateOpcoesDeBusca(filtro : FiltroDeBuscaListarCartao){
        _opcoesDeBusca.value = OpcoesDeBuscaListarCartao( filtro)
        Log.d("HomeDialogs", "Defini Pasta em FOCO")

        Log.d("HomeDialogs", filtro.toString())
    }

    /**
     * Filters and sorts the list of cards based on the current search query and filter options.
     *
     * @param busca The search query to filter the list of cards.
     */
    fun updateFilterCartaoList(busca: String){
        _textoBusca.value = busca

        if(_cartaoList.value == null){
            _cartaoListSort.value = emptyList()
        }
        else if (_opcoesDeBusca.value!!.filtrar == FiltroDeBuscaListarCartao.PERGUNTA) {
            if(busca.isEmpty()){
                _cartaoListSort.value = _cartaoList.value!!
                    .sortedBy { it.pergunta } // Ordena pela pergunta
            }
            else{
                _cartaoListSort.value = _cartaoList.value!!
                    .filter { it.pergunta.contains(busca, ignoreCase = true) }
                    .sortedBy { it.pergunta } // Ordena pela pergunta após filtrar
            }
        } else {
            if(busca.isEmpty()){
                _cartaoListSort.value = _cartaoList.value!!
                    .sortedBy { it.resposta } // Ordena pela resposta
            }
            else{
                _cartaoListSort.value = _cartaoList.value!!
                    .filter { it.resposta.contains(busca, ignoreCase = true) }
                    .sortedBy { it.resposta } // Ordena pela resposta após filtrar
            }
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