package com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.brash.R
import com.example.brash.aprendizado.gestaoDeConteudo.data.repository.BaralhoRepository
import com.example.brash.aprendizado.gestaoDeConteudo.data.repository.BaralhoRepository2
import com.example.brash.aprendizado.gestaoDeConteudo.data.repository.PastaRepository
import com.example.brash.aprendizado.gestaoDeConteudo.data.repository.PastaRepository2
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Baralho
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.HomeAcListItem
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Pasta
import com.example.brash.nucleo.data.repository.UsuarioRepository
import com.example.brash.nucleo.utils.UtilsFoos
import kotlinx.coroutines.launch

/**
 * ViewModel for the Home screen.
 * Handles the logic for fetching and updating folders (Pasta) and decks (Baralho) from a repository.
 */
class HomeVM(application: Application) : AndroidViewModel(application) {

    private var _teste = MutableLiveData<Boolean>()
    val teste get() = _teste

    private var _listPastaMsg = MutableLiveData<Int>()
    private var _pastaList = MutableLiveData<List<Pasta>>()
    val listPastaMsg get() = _listPastaMsg
    val pastaList get() = _pastaList

    private var _listHomeAcListItemMsg = MutableLiveData<Int>()
    private var _homeAcListItemList = MutableLiveData<List<HomeAcListItem>>()
    val listHomeAcListItemMsg get() = _listHomeAcListItemMsg
    val homeAcListItemList get()  = _homeAcListItemList

    private var _pastaEmFoco = MutableLiveData<Pasta>()
    private var _baralhoEmFoco = MutableLiveData<Baralho>()

    val pastaEmFoco get() = _pastaEmFoco
    val baralhoEmFoco get() = _baralhoEmFoco

    private val _pastaEmMover = MutableLiveData<Pasta?>()
    val pastaEmMover get() = _pastaEmMover

    private val baralhoRepository = BaralhoRepository2()
    private val pastaRepository = PastaRepository2()
    private val usuarioRepository = UsuarioRepository()

    private val pastaRoot = MutableLiveData<Pasta>(Pasta(idPasta = usuarioRepository.getRootFolderId(), nome ="root"))

    /**
     * Retrieves a string resource from the application.
     *
     * @param id The resource ID of the string.
     * @return The string corresponding to the resource ID.
     */
    private fun getStringApplication(id : Int) : String{
        return getApplication<Application>().getString(id)
    }


    /*
    fun getAllPastas() {

        //TODO:: requisitar do firebase

        _pastaList.value = listOf(
            Pasta(nome =  "Alimentos"),
            Pasta(nome =  "Frutas", idPasta = "1"),
            Pasta(nome =  "VerdurasVerdurasAlimentosmorango"),
            Pasta(nome =  "abacaxi"),
            Pasta(nome =  "Alimentos"),
            Pasta(nome =  "Frutas"),
            Pasta(nome =  "morango"),
            Pasta(nome =  "uva"),
            Pasta(nome =  "Frutas"))

        Log.d("ListaPastaAdapter", "DEFINIÇÃO DAS PASTAS")
    }*/

    /**
     * Fetches all HomeAcListItems (folders and decks) from the repository.
     * This function updates the HomeAcListItem list with data from the Firebase database.
     */
    fun getAllHomeAcListItem(){

        //TODO:: requisitar do firebase
        /*
        val pastaComBaralhos = Pasta(nome =  "Eletrônicos", idPasta ="1" )

        val listaBaralho = mutableListOf(
            Baralho(nome = "Celular", pasta = p),
            Baralho(nome = "Notebook Teste do tamanhoa aaaaaaabbbbbb", pasta = p),
            Baralho(nome = "Fone de ouvido", pasta = p)
        )

        pastaComBaralhos.baralhos = listaBaralho

        val listaHomeAcListItem = listOf<HomeAcListItem>(
            HomeAcListItem.HomeAcPastaItem(pasta = Pasta(nome = "Roupas ")),
            HomeAcListItem.HomeAcPastaItem(isExpanded = true, pasta = pastaComBaralhos),
            HomeAcListItem.HomeAcPastaItem(pasta = Pasta(nome =  "Alimentos")),
            HomeAcListItem.HomeAcPastaItem(pasta = Pasta(nome =  "Frutas")),
            HomeAcListItem.HomeAcPastaItem(pasta = Pasta(nome =  "Verduras")),
            HomeAcListItem.HomeAcPastaItem(pasta = Pasta(nome =  "abacaxi")),
            HomeAcListItem.HomeAcPastaItem(pasta = Pasta(nome =  "4")),
            HomeAcListItem.HomeAcPastaItem(pasta = Pasta(nome =  "5")),
            HomeAcListItem.HomeAcPastaItem(pasta = Pasta(nome =  "6")),
            HomeAcListItem.HomeAcBaralhoItem(Baralho(nome = "Fone de ouvido"))
        )
        _homeAcListItemList.value = listaHomeAcListItem
        */

        viewModelScope.launch {
            val result = pastaRepository.getFolders2()

            result
                .onSuccess {
                        folders ->
                    // A operação foi bem-sucedida, faça algo com as pastas (folders)
                    Log.d("Pasta", "Pastas carregadas: $folders")

                    //TODO:: transformar em homeAcListItem
                    initPastaList(folders)
                    initHomeAcListItemList(folders)
                }
                .onFailure {
                    UtilsFoos.showToast(getApplication(), getStringApplication(R.string.erro_requisicao_banco_dados_firebase))
                    Log.e("Pasta", "Erro ao carregar pastas do firebase")

                    _pastaList.value = emptyList()
                    _homeAcListItemList.value = emptyList()
                }

        }

    }

    /**
     * Initializes the list of folders (Pasta).
     *
     * @param folders The list of folders to be displayed.
     */
    private fun initPastaList(folders: List<Pasta>){
        _pastaList.value = folders
        sortPastaList()
    }

    private fun addPasta(pasta: Pasta){
        _pastaList.value = _pastaList.value?.toMutableList()?.apply {
            add(pasta)
        }
    }
    /**
     * Initializes the list of HomeAcListItems (folders and decks).
     *
     * @param folders The list of folders to be converted into HomeAcListItems.
     */
    private fun initHomeAcListItemList(folders: List<Pasta>){
        // Criação do homeAcListItem, onde vamos adicionar as pastas e baralhos
        val homeAcListItem = folders.flatMap { folder ->
            if (folder.idPasta == pastaRoot.value!!.idPasta) {
                // Se for a pasta "root", adicione os baralhos
                folder.baralhos.map { baralho ->
                    HomeAcListItem.HomeAcBaralhoItem(baralho = baralho)
                }
            } else {
                // Caso contrário, adicione a pasta
                listOf(HomeAcListItem.HomeAcPastaItem(pasta = folder, isExpanded = folder.baralhos.isNotEmpty()))
            }
        }.toMutableList()

        // Primeiro, ordena as pastas pelo nome
        val (pastas, baralhos) = homeAcListItem.partition {
            it is HomeAcListItem.HomeAcPastaItem
        }

        // Ordena as pastas pelo nome
        val sortedPastas = pastas.sortedBy { (it as HomeAcListItem.HomeAcPastaItem).pasta.nome }

        // Ordena os baralhos pelo nome
        val sortedBaralhos = baralhos.sortedBy { (it as HomeAcListItem.HomeAcBaralhoItem).baralho.nome }

        // Junta as pastas ordenadas com os baralhos, com os baralhos vindo após as pastas
        _homeAcListItemList.value = sortedPastas + sortedBaralhos
    }

    /**
     * Sorts the list of folders by name and ensures that the "root" folder appears first.
     */
    private fun sortPastaList(){
        _pastaList.value = _pastaList.value?.sortedWith(compareBy<Pasta> { it.idPasta != pastaRoot.value!!.idPasta }.thenBy { it.nome })
    }

    /**
     * Sorts the list of HomeAcListItems by the names of the folders and decks.
     */
    private fun sortHomeAcListItemList(){
        _homeAcListItemList.value = _homeAcListItemList.value?.sortedWith(
            compareBy<HomeAcListItem> {
                // Ordena pelos itens HomeAcBaralhoItem e pelo nome do baralho
                (it as? HomeAcListItem.HomeAcBaralhoItem)?.baralho?.nome ?: ""
            }.thenBy {
                // Ordena pelos itens HomeAcPastaItem e pelo nome da pasta
                (it as? HomeAcListItem.HomeAcPastaItem)?.pasta?.nome ?: ""
            }
        )

        Log.e("HOMEVM", "essa eh a lista da home: ${_homeAcListItemList.value!!}")
    }

    /**
     * Sets the folder that is currently in focus.
     *
     * @param pasta The folder to be set in focus.
     */
    fun setPastaEmFoco(pasta: Pasta){
        pastaEmFoco.value = pasta
        Log.d("HomeDialogs", "Defini Pasta em FOCO")
    }

    /**
     * Resets the folder that is currently in focus.
     */
    fun resetPastaEmFoco(){
        pastaEmFoco.value = null
        Log.d("HomeDialogs", "Resetei Pasta em FOCO")
    }

    /**
     * Sets the deck that is currently in focus.
     *
     * @param baralho The deck to be set in focus.
     */
    fun setBaralhoEmFoco(baralho: Baralho){
        baralhoEmFoco.value = baralho
        baralho.pasta?.let { pasta ->
            setPastaEmMover(pasta)
        }
        Log.d("HomeDialogs", "Defini Baralho em FOCO")
    }

    /**
     * Resets the deck that is currently in focus.
     */
    fun resetBaralhoEmFoco(){
        baralhoEmFoco.value = null
        Log.d("HomeDialogs", "Resetei Baralho em FOCO")
    }

    /**
     * Sets the folder that is currently being moved.
     *
     * @param pasta The folder to be set for moving.
     */
    fun setPastaEmMover(pasta: Pasta){
        _pastaEmMover.value = pasta
    }

    /**
     * Resets the folder that is currently being moved.
     */
    fun resetPastaEmMover(){
        _pastaEmMover.value = null
    }

    /**
     * Creates a new deck with the given name and description.
     * This function also sorts the list of HomeAcListItems after the creation.
     *
     * @param nome The name of the deck.
     * @param descricao The description of the deck.
     * @param onSuccess Callback to be called when the deck is successfully created.
     */
    fun criarBaralho(nome : String, descricao : String, onSuccess : () -> Unit){
        if(processaInforBaralho(nome, descricao) && verificaBaralhoNomeUnico(nome)){

            viewModelScope.launch {
                val baralho = Baralho(
                    idBaralho = "0",
                    nome = nome,
                    descricao = descricao
                )
                val result = baralhoRepository.createDeck2(baralho)
                result
                    .onSuccess { id ->
                        baralho.idBaralho = id
                        baralho.pasta = pastaRoot.value
                        _homeAcListItemList.value = _homeAcListItemList.value?.plus(HomeAcListItem.HomeAcBaralhoItem(baralho = baralho))
                        sortHomeAcListItemList()
                        onSuccess()
                    }
                    .onFailure {
                        UtilsFoos.showToast(getApplication(), getStringApplication(R.string.erro_requisicao_banco_dados_firebase))
                        //UtilsFoos.showToast(getApplication(), "Ocorreu algum erro na criação da baralho:: ${e}")
                        Log.e("criar baralho debug", "Ocorreu algum erro na criação da baralho")
                    }
            }
        }
    }

    /**
     * Validates the name and description for the deck.
     * Ensures both are not empty and that the name is unique.
     *
     * @param name The name of the deck.
     * @param description The description of the deck.
     * @return Boolean indicating whether the deck information is valid.
     */
    private fun processaInforBaralho(name : String, description : String) : Boolean{
        if(name.isEmpty() || description.isEmpty()){

            UtilsFoos.showToast(getApplication(), getStringApplication(R.string.nuc_preencha_todos_campos))
            return false
        }//else if(!verificaBaralhoNomeUnico(name)){
            //UtilsFoos.showToast(getApplication(), getStringApplication(R.string.gtc_nome_unico_baralho))
            //return false
        //}

        return true
    }

    /**
     * Checks if the deck name is unique across all existing decks.
     *
     * @param name The name of the deck to check.
     * @return Boolean indicating whether the deck name is unique.
     */
    private fun verificaBaralhoNomeUnico(name : String): Boolean{

        for (pasta in _pastaList.value.orEmpty()) {  // Usando orEmpty() para garantir que seja uma lista não-nula
            for (baralho in pasta.baralhos) {  // Usando orEmpty() para evitar NPE
                if (baralho.nome == name) {
                    UtilsFoos.showToast(getApplication(), getStringApplication(R.string.gtc_nome_unico_baralho))
                    return false  // Retorna false caso o nome do baralho seja encontrado
                }
            }
        }
        return true  // Retorna true caso o nome do baralho não seja encontrado em nenhuma pasta

    }

    /**
     * Edits the details of an existing deck.
     *
     * @param baralho The deck to be edited.
     * @param nome The new name of the deck.
     * @param descricao The new description of the deck.
     * @param numberNewCardsPerDay The new number of new cards per day.
     * @param public A boolean indicating whether the deck is public or not.
     * @param onSuccess A callback function to be executed on successful update of the deck.
     *
     * This function validates the input values and updates the deck in the repository. If successful,
     * it updates the deck in the list and triggers the callback.
     */
    fun editarBaralho(baralho: Baralho, nome : String, descricao : String, numberNewCardsPerDay: Int, public: Boolean, onSuccess : () -> Unit){
        if(numberNewCardsPerDay <= 0){
            UtilsFoos.showToast(getApplication(), getStringApplication(R.string.gtc_numero_cartoes_maior_que_zero))
        }
        else if(processaInforBaralho(nome, descricao) && (baralho.nome == nome || verificaBaralhoNomeUnico(nome)) ){
            viewModelScope.launch{
                val result = baralhoRepository.updateDeck2(baralho, nome, descricao,numberNewCardsPerDay, public)

                result
                    .onSuccess {
                        onSuccess()
                        baralho.nome = nome
                        baralho.descricao = descricao
                        baralho.publico = public
                        baralho.cartoesNovosPorDia = numberNewCardsPerDay
                        sortHomeAcListItemList()
                    }
                    .onFailure {
                        UtilsFoos.showToast(getApplication(), getStringApplication(R.string.erro_requisicao_banco_dados_firebase))
                        //UtilsFoos.showToast(getApplication(), "Ocorreu algum erro na edição do baralho: ${e}")
                        Log.e("criar Pasta debug", "Ocorreu algum erro na criação da pasta")
                    }
            }
        }

        // request para atualizar dados
        //getAllHomeAcListItem()
    }

    /**
     * Deletes an existing deck from the repository and updates the UI.
     *
     * @param baralho The deck to be deleted.
     * @param onSuccess A callback function to be executed on successful deletion of the deck.
     *
     * This function removes the specified deck from the repository and updates the list of decks
     * in the UI. If successful, it removes the deck from the list and triggers the callback.
     */
    fun excluirBaralho(baralho: Baralho, onSuccess : () -> Unit){
        viewModelScope.launch{
            val result = baralhoRepository.deleteDeck2(baralho)

            result
                .onSuccess {
                    _homeAcListItemList.value = _homeAcListItemList.value?.toMutableList()?.apply {
                        removeAll {
                            (it as? HomeAcListItem.HomeAcBaralhoItem)?.baralho == baralho
                        }
                    }?: emptyList()
                    val pastaDona = baralho.pasta
                    pastaDona?.baralhos?.remove(baralho)
                    onSuccess()
                    sortHomeAcListItemList()
                }
                .onFailure {
                    UtilsFoos.showToast(getApplication(), getStringApplication(R.string.erro_requisicao_banco_dados_firebase))
                    //UtilsFoos.showToast(getApplication(), "Ocorreu algum erro na edição do baralho")
                    Log.e("criar Pasta debug", "Ocorreu algum erro na criação da pasta")
                }
        }
    }

    /**
     * Moves a deck to a different folder.
     *
     * @param pastaDestino The destination folder for the deck.
     * @param baralho The deck to be moved.
     * @param onSuccess A callback function to be executed on successful move of the deck.
     *
     * This function copies the deck to the destination folder, deletes the original deck,
     * and updates the UI. If successful, it triggers the callback and logs the movement.
     */
    fun moverBaralho(pastaDestino: Pasta, baralho: Baralho, onSuccess: () -> Unit){
        viewModelScope.launch{
            val resultCopy = pastaRepository.moveDeckToFolder2(pastaDestino, baralho)
            resultCopy
                .onSuccess {id ->
                    getAllHomeAcListItem()
                    onSuccess()
                    Log.e("homeVM", "Copia e delete sucedidos\n\"Movendo --${baralho.nome}-- da pasta -- ${baralho.pasta?.nome} -- para pasta --${pastaDestino.nome}--\"")
                }
                .onFailure { eCopy->
                    UtilsFoos.showToast(getApplication(), getStringApplication(R.string.erro_requisicao_banco_dados_firebase))
                    //UtilsFoos.showToast(getApplication(), "Ocorreu algum erro na cópia do baralho")
                    Log.e("homeVM", "Ocorreu algum erro na cópia do baralho:: ${eCopy}\n\"Movendo --${baralho.nome}-- da pasta -- ${baralho.pasta?.nome} -- para pasta --${pastaDestino.nome}--\"")
                }
        }
    }

    fun moverBaralhoAntigo(pastaDestino: Pasta, baralho: Baralho, onSuccess: () -> Unit){
        /*viewModelScope.launch{
            val resultCopy = pastaRepository.copyDeck2(pastaDestino, baralho)
            resultCopy
                .onSuccess {id ->
                    val resultDelete = baralhoRepository.deleteDeck2(baralho)
                    resultDelete.onSuccess {

                        getAllHomeAcListItem()
                        onSuccess()
                        Log.e("homeVM", "Copia e delete sucedidos\n\"Movendo --${baralho.nome}-- da pasta -- ${baralho.pasta?.nome} -- para pasta --${pastaDestino.nome}--\"")
                    }.onFailure { eDelete->
                        UtilsFoos.showToast(getApplication(), getStringApplication(R.string.erro_requisicao_banco_dados_firebase))
                        Log.e("homeVM", "Ocorreu algum erro na exclusão do baralho:: ${eDelete}\n\"Movendo --${baralho.nome}-- da pasta -- ${baralho.pasta?.nome} -- para pasta --${pastaDestino.nome}--\"")
                    }
                }
                .onFailure { eCopy->
                    UtilsFoos.showToast(getApplication(), getStringApplication(R.string.erro_requisicao_banco_dados_firebase))
                    //UtilsFoos.showToast(getApplication(), "Ocorreu algum erro na cópia do baralho")
                    Log.e("homeVM", "Ocorreu algum erro na cópia do baralho:: ${eCopy}\n\"Movendo --${baralho.nome}-- da pasta -- ${baralho.pasta?.nome} -- para pasta --${pastaDestino.nome}--\"")
                }
        }*/
    }

    /**
     * Creates a new folder and adds it to the list of folders.
     *
     * @param nome The name of the new folder.
     * @param onSuccess A callback function to be executed on successful creation of the folder.
     *
     * This function validates the folder name, creates the folder in the repository,
     * and updates the UI. If successful, it triggers the callback and adds the new folder to the list.
     */
    fun criarPasta(nome : String, onSuccess: () -> Unit){
        if(processaInfoPasta(nome) && verificaPastaNomeUnico(nome)){
            val pasta = Pasta(nome= nome)
            viewModelScope.launch{
                val result = pastaRepository.createFolder2(pasta)
                result
                    .onSuccess {id ->
                        pasta.idPasta = id
                        Log.e("HOMEVM", "Esse eh o novo idPasta:${id};; nome${nome}")
                        _homeAcListItemList.value = _homeAcListItemList.value?.plus(HomeAcListItem.HomeAcPastaItem(pasta = pasta))
                        sortHomeAcListItemList()

                        addPasta(pasta)
                        sortPastaList()

                        onSuccess()
                    }
                    .onFailure {
                        UtilsFoos.showToast(getApplication(), getStringApplication(R.string.erro_requisicao_banco_dados_firebase))
                        //UtilsFoos.showToast(getApplication(), "Ocorreu algum erro na criação da pasta")
                        Log.e("criar Pasta debug", "Ocorreu algum erro na criação da pasta")
                    }
            }
        }
        // request para atualizar dados
        //getAllHomeAcListItem()
    }

    /**
     * Validates the folder name.
     *
     * @param nome The name of the folder to validate.
     * @return Boolean indicating whether the folder name is valid.
     *
     * This function checks if the folder name is empty or not unique. It shows a toast message
     * if any of these validations fail.
     */
    private fun processaInfoPasta(nome : String) : Boolean{

        if(nome.isEmpty()){
            UtilsFoos.showToast(getApplication(), getStringApplication(R.string.nuc_preencha_todos_campos))
            return false
        }//else if(!verificaPastaNomeUnico(nome)){ // verificacao de nome único
            //UtilsFoos.showToast(getApplication(), getStringApplication(R.string.gtc_nome_unico_pasta))
            //return false
        //}
        return true
    }

    /**
     * Checks if the folder name is unique across all existing folders.
     *
     * @param name The name of the folder to check.
     * @return Boolean indicating whether the folder name is unique.
     */
    private fun verificaPastaNomeUnico(name : String): Boolean{

        for(pasta in _pastaList.value!!){
            if(pasta.nome == name){
                UtilsFoos.showToast(getApplication(), getStringApplication(R.string.gtc_nome_unico_pasta))
                return false
            }
        }
        return true
    }

    /**
     * Edits the details of an existing folder.
     *
     * @param pasta The folder to be edited.
     * @param nome The new name of the folder.
     * @param onSuccess A callback function to be executed on successful update of the folder.
     *
     * This function validates the input values and updates the folder in the repository. If successful,
     * it updates the folder in the list and triggers the callback.
     */
    fun editarPasta(pasta: Pasta, nome : String, onSuccess : () -> Unit){
        if(processaInfoPasta(nome) && (pasta.nome == nome || verificaPastaNomeUnico(nome))){
            viewModelScope.launch{
                val result = pastaRepository.updateFolder2(pasta, nome)
                result
                    .onSuccess {
                        onSuccess()
                        pasta.nome = nome
                        sortPastaList()
                        sortHomeAcListItemList()
                    }
                    .onFailure {
                        UtilsFoos.showToast(getApplication(), getStringApplication(R.string.erro_requisicao_banco_dados_firebase))
                        //UtilsFoos.showToast(getApplication(), "Ocorreu algum erro na edição da pasta")
                        Log.e("editar Pasta debug", "Ocorreu algum erro na edição da pasta")
                    }
            }
        }
        // request para atualizar dados
        //getAllHomeAcListItem()
    }

    /**
     * Deletes an existing folder from the repository and updates the UI.
     *
     * @param pasta The folder to be deleted.
     * @param onSuccess A callback function to be executed on successful deletion of the folder.
     *
     * This function checks if the folder contains any decks before attempting to delete it. If the folder
     * contains decks, it shows a message. Otherwise, it removes the folder from the repository and updates the UI.
     */
    fun excluirPasta(pasta: Pasta, onSuccess : () -> Unit){

        if(pasta.baralhos.isEmpty()){
            viewModelScope.launch{
                val result = pastaRepository.deleteFolder2(pasta)
                result
                    .onSuccess {
                        _homeAcListItemList.value = _homeAcListItemList.value?.toMutableList()?.apply {
                            removeAll {
                                (it as? HomeAcListItem.HomeAcPastaItem)?.pasta == pasta
                            }
                        }?: emptyList()
                        _pastaList.value = _pastaList.value?.toMutableList()?.apply {
                            removeAll {
                                it  == pasta
                            }
                        }?: emptyList()
                        onSuccess()
                        sortHomeAcListItemList()
                    }
                    .onFailure {
                        UtilsFoos.showToast(getApplication(), getStringApplication(R.string.erro_requisicao_banco_dados_firebase))
                        //UtilsFoos.showToast(getApplication(), "Ocorreu algum erro na edição do baralho")
                        Log.e("criar Pasta debug", "Ocorreu algum erro na criação da pasta")
                    }
            }
        }
        else{
            UtilsFoos.showToast(getApplication(), getStringApplication(R.string.gtc_pasta_contem_baralhos))
        }

    }
}


/*

val dialog = OpcaoDialogFragment()
dialog.show(supportFragmentManager, "OpcaoDialog")
 */