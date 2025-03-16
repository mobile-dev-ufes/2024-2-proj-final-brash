package com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.brash.R
import com.example.brash.aprendizado.gestaoDeConteudo.data.repository.BaralhoRepository
import com.example.brash.aprendizado.gestaoDeConteudo.data.repository.PastaRepository
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Baralho
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.HomeAcListItem
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Pasta
import com.example.brash.nucleo.domain.model.Usuario
import com.example.brash.nucleo.utils.UtilsFoos
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.Locale

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

    private val baralhoRepository = BaralhoRepository()
    private val pastaRepository = PastaRepository()



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
            val result = pastaRepository.getFolders()

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
                    Log.e("Pasta", "Erro ao carregar pastas do firebase")

                    _pastaList.value = emptyList()
                    _homeAcListItemList.value = emptyList()
                }

        }

    }

    private fun initPastaList(folders: List<Pasta>){
        _pastaList.value = folders
    }
    private fun initHomeAcListItemList(folders: List<Pasta>){
        // Criação do homeAcListItem, onde vamos adicionar as pastas e baralhos
        val homeAcListItem = folders.flatMap { folder ->
            if (folder.idPasta == "root") {
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

    private fun sortPastaList(){
        _pastaList.value = _pastaList.value?.sortedBy { it.nome }
    }
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
    }

    fun setPastaEmFoco(pasta: Pasta){
        pastaEmFoco.value = pasta
        Log.d("HomeDialogs", "Defini Pasta em FOCO")
    }
    fun resetPastaEmFoco(){
        pastaEmFoco.value = null
        Log.d("HomeDialogs", "Resetei Pasta em FOCO")
    }

    fun setBaralhoEmFoco(baralho: Baralho){
        baralhoEmFoco.value = baralho
        baralho.pasta?.let { pasta ->
            setPastaEmMover(pasta)
        }
        Log.d("HomeDialogs", "Defini Baralho em FOCO")
    }
    fun resetBaralhoEmFoco(){
        baralhoEmFoco.value = null
        Log.d("HomeDialogs", "Resetei Baralho em FOCO")
    }

    fun setPastaEmMover(pasta: Pasta){
        _pastaEmMover.value = pasta
    }
    fun resetPastaEmMover(){
        _pastaEmMover.value = null
    }

    fun criarBaralho(nome : String, descricao : String, onSuccess : () -> Unit){
        //TODO:: apenas confirmar a criação se o nome for único para o usuário

        if(processaInforBaralho(nome, descricao)){

            viewModelScope.launch {
                val baralho = Baralho(
                    nome = nome,
                    descricao = descricao
                )
                val result = baralhoRepository.createDeck(baralho)
                result
                    .onSuccess {
                        _homeAcListItemList.value = _homeAcListItemList.value?.plus(HomeAcListItem.HomeAcBaralhoItem(baralho = baralho))
                        sortHomeAcListItemList()
                        onSuccess()
                    }
                    .onFailure {
                        UtilsFoos.showToast(getApplication(), "Ocorreu algum erro na criação da baralho")
                        Log.e("criar baralho debug", "Ocorreu algum erro na criação da baralho")
                    }
            }
        }
    }

    private fun processaInforBaralho(name : String, description : String) : Boolean{
        if(name.isEmpty() || description.isEmpty()){

            UtilsFoos.showToast(getApplication(), getStringApplication(R.string.nuc_preencha_todos_campos))
            return false
        }else if(false){

            return false
        }

        return true
    }

    fun editarBaralho(baralho: Baralho, nome : String, descricao : String, numberNewCardsPerDay: Int, public: Boolean, onSuccess : () -> Unit){
        //TODO:: Fazer a edição de baralho do firebase também
        //TODO:: apenas requisitar se tiver ALGUMA informação diferente
        //TODO:: apenas confirmar a mudança do nome se for único para o usuário, o restante pode sempre atualizar
        //TODO:: Se não conseguir alterar o nome ele altera o resto
        if(numberNewCardsPerDay <= 0){
            UtilsFoos.showToast(getApplication(), getStringApplication(R.string.nuc_preencha_todos_campos))
        }
        else if(processaInforBaralho(nome, descricao) ){
            viewModelScope.launch{
                val result = baralhoRepository.updateDeck(baralho, nome, descricao,numberNewCardsPerDay, public)

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
                        UtilsFoos.showToast(getApplication(), "Ocorreu algum erro na edição do baralho")
                        Log.e("criar Pasta debug", "Ocorreu algum erro na criação da pasta")
                    }
            }
        }

        // request para atualizar dados
        //getAllHomeAcListItem()
    }

    fun excluirBaralho(baralho: Baralho, onSuccess : () -> Unit){
        viewModelScope.launch{
            val result = baralhoRepository.deleteDeck(baralho)

            result
                .onSuccess {
                    _homeAcListItemList.value = _homeAcListItemList.value?.toMutableList()?.apply {
                        removeAll {
                            (it as? HomeAcListItem.HomeAcBaralhoItem)?.baralho == baralho
                        }
                    }
                    val pastaDona = baralho.pasta
                    pastaDona?.baralhos?.remove(baralho)
                    onSuccess()
                    sortHomeAcListItemList()
                }
                .onFailure {
                    UtilsFoos.showToast(getApplication(), "Ocorreu algum erro na edição do baralho")
                    Log.e("criar Pasta debug", "Ocorreu algum erro na criação da pasta")
                }
        }
    }

    fun moverBaralho(pasta: Pasta, baralho: Baralho, onSuccess: () -> Unit){
        //TODO:: Fazer a lógica
        onSuccess()
    }

    fun criarPasta(nome : String, onSuccess: () -> Unit){
        //TODO:: apenas confirmar a criação se o nome for único para o usuário

        if(processaInfoPasta(nome)){
            viewModelScope.launch{
                val result = pastaRepository.createFolder(nome)
                result
                    .onSuccess {
                        _homeAcListItemList.value = _homeAcListItemList.value?.plus(HomeAcListItem.HomeAcPastaItem(pasta = Pasta(nome)))
                        sortHomeAcListItemList()
                        onSuccess()
                    }
                    .onFailure {
                        UtilsFoos.showToast(getApplication(), "Ocorreu algum erro na criação da pasta")
                        Log.e("criar Pasta debug", "Ocorreu algum erro na criação da pasta")
                    }
            }
        }
        // request para atualizar dados
        //getAllHomeAcListItem()
    }

    private fun processaInfoPasta(nome : String) : Boolean{

        if(nome.isEmpty()){
            UtilsFoos.showToast(getApplication(), getStringApplication(R.string.nuc_preencha_todos_campos))
            return false
        }else if(false){ // verificacao de nome único
            return false
        }
        return true
    }

    fun editarPasta(pasta: Pasta, nome : String, onSuccess : () -> Unit){
        //TODO:: Fazer a edição de pasta do firebase também
        //TODO:: apenas requisitar se tiver ALGUMA informação diferente
        //TODO:: apenas confirmar a mudança do nome se for único para o usuário
        //TODO:: Se não conseguir alterar o nome ele altera o resto
        if(processaInfoPasta(nome)){
            viewModelScope.launch{
                val result = pastaRepository.updateFolder(pasta, nome)
                result
                    .onSuccess {
                        onSuccess()
                        pasta.nome = nome
                        sortPastaList()
                        sortHomeAcListItemList()
                    }
                    .onFailure {
                        UtilsFoos.showToast(
                            getApplication(),
                            "Ocorreu algum erro na edição da pasta"
                        )
                        Log.e("editar Pasta debug", "Ocorreu algum erro na edição da pasta")
                    }
            }
        }
        // request para atualizar dados
        //getAllHomeAcListItem()
    }
    fun excluirPasta(pasta: Pasta, onSuccess : () -> Unit){

        if(pasta.baralhos.isEmpty()){
            viewModelScope.launch{
                val result = pastaRepository.deleteFolder(pasta)
                result
                    .onSuccess {
                        _homeAcListItemList.value = _homeAcListItemList.value?.toMutableList()?.apply {
                            removeAll {
                                (it as? HomeAcListItem.HomeAcPastaItem)?.pasta == pasta
                            }
                        }
                        _pastaList.value = _pastaList.value?.toMutableList()?.apply {
                            removeAll {
                                it  == pasta
                            }
                        }
                        onSuccess()
                        sortHomeAcListItemList()
                    }
                    .onFailure {
                        UtilsFoos.showToast(getApplication(), "Ocorreu algum erro na edição do baralho")
                        Log.e("criar Pasta debug", "Ocorreu algum erro na criação da pasta")
                    }
            }
        }
        else{
            UtilsFoos.showToast(getApplication(), "Esta pasta contém baralhos")
        }

    }
}


/*

val dialog = OpcaoDialogFragment()
dialog.show(supportFragmentManager, "OpcaoDialog")
 */