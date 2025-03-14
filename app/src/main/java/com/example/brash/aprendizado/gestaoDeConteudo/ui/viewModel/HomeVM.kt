package com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.brash.R
import com.example.brash.aprendizado.gestaoDeConteudo.data.repository.BaralhoRepository
import com.example.brash.aprendizado.gestaoDeConteudo.data.repository.PastaRepository
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Baralho
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.HomeAcListItem
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.OpcoesDeBuscaHome
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Pasta
import com.example.brash.aprendizado.gestaoDeConteudo.utils.FiltroDeBuscaHome
import com.example.brash.aprendizado.gestaoDeConteudo.utils.OrdemDeBuscaHome
import com.example.brash.nucleo.domain.model.Usuario
import com.example.brash.nucleo.utils.UtilsFoos
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.Exception

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

    private var _opcoesDeBusca = MutableLiveData<OpcoesDeBuscaHome>()
    val opcoesDeBusca get() = _opcoesDeBusca

    val _pastaEmMover = MutableLiveData<Pasta?>()
    val pastaEmMover get() = _pastaEmMover

    private val baralhoRepository = BaralhoRepository()
    private val pastaRepository = PastaRepository()

    fun createDeck(name : String, description : String, onSuccess : () -> Unit){

        val deck = Baralho(
            nome = name,
            descricao = description
        )
        if(handleDeckInfo(name, description)){
            baralhoRepository.createDeck(deck, {
                onSuccess()
            },{
                UtilsFoos.showToast(getApplication(), "Não foi possível criar o baralho")
            })
        }

    }

    private fun handleDeckInfo(name : String, description : String) : Boolean{
        if(name.isEmpty() || description.isEmpty()){

            UtilsFoos.showToast(getApplication(), getStringApplication(R.string.nuc_preencha_todos_campos))
            return false
        }else if(false){ //TODO verificar se o nome do baralho é único

            return false
        }

        return true
    }

    private fun getStringApplication(id : Int) : String{
        return getApplication<Application>().getString(id)
    }



    fun getAllPastas() {

        //TODO:: requisitar do firebase

        _pastaList.value = listOf(
            Pasta(nome =  "Alimentos"),
            Pasta(nome =  "Frutas", idPasta = 1),
            Pasta(nome =  "VerdurasVerdurasAlimentosmorango"),
            Pasta(nome =  "abacaxi"),
            Pasta(nome =  "Alimentos"),
            Pasta(nome =  "Frutas"),
            Pasta(nome =  "morango"),
            Pasta(nome =  "uva"),
            Pasta(nome =  "Frutas"))

        Log.d("ListaPastaAdapter", "DEFINIÇÃO DAS PASTAS")
    }

    fun getAllHomeAcListItem(){

        //TODO:: requisitar do firebase

        val p = Pasta(nome =  "Eletrônicos", idPasta =1 )

        val listaBaralho = mutableListOf(
            Baralho(nome = "Celular", pasta = p),
            Baralho(nome = "Notebook Teste do tamanhoa aaaaaaabbbbbb", pasta = p),
            Baralho(nome = "Fone de ouvido", pasta = p)
        )

        p.baralhos = listaBaralho

        var listaHomeAcListItem = listOf<HomeAcListItem>(
            HomeAcListItem.HomeAcPastaItem(pasta = Pasta(nome = "Roupas ")),
            HomeAcListItem.HomeAcPastaItem(isExpanded = true, pasta = p),
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
        Log.d("HomeDialogs", "Defini Baralho em FOCO")
    }
    fun resetBaralhoEmFoco(){
        baralhoEmFoco.value = null
        Log.d("HomeDialogs", "Resetei Baralho em FOCO")
    }

    fun updateOpcoesDeBusca(ordem: OrdemDeBuscaHome, filtro : FiltroDeBuscaHome){
        _opcoesDeBusca.value = OpcoesDeBuscaHome(ordem, filtro)
        Log.d("HomeDialogs", "Defini Pasta em FOCO")

        Log.d("HomeDialogs", ordem.toString())
        Log.d("HomeDialogs", filtro.toString())
    }

    fun setPastaEmMover(pasta: Pasta){
        _pastaEmMover.value = pasta
    }
    fun resetPastaEmMover(){
        _pastaEmMover.value = null
    }

    fun criarBaralho(baralho: Baralho){
        //TODO:: Fazer a criação de baralho do firebase também
        //TODO:: apenas confirmar a criação se o nome for único para o usuário

        // request para atualizar dados
        getAllHomeAcListItem()
    }
    fun editarBaralho(baralho: Baralho){
        //TODO:: Fazer a edição de baralho do firebase também
        //TODO:: apenas requisitar se tiver ALGUMA informação diferente
        //TODO:: apenas confirmar a mudança do nome se for único para o usuário, o restante pode sempre atualizar
        //TODO:: Se não conseguir alterar o nome ele altera o resto

        // request para atualizar dados
        getAllHomeAcListItem()
    }
    fun excluirBaralho(baralho: Baralho){
        //TODO:: Fazer a exclusão de baralho do firebase também

        // request para atualizar dados
        getAllHomeAcListItem()
    }

    fun criarPasta(pasta: Pasta){
        //TODO:: Fazer a criação de pasta do firebase também
        //TODO:: apenas confirmar a criação se o nome for único para o usuário

        // request para atualizar dados
        getAllHomeAcListItem()
    }
    fun editarPasta(pasta: Pasta){
        //TODO:: Fazer a edição de pasta do firebase também
        //TODO:: apenas requisitar se tiver ALGUMA informação diferente
        //TODO:: apenas confirmar a mudança do nome se for único para o usuário
        //TODO:: Se não conseguir alterar o nome ele altera o resto

        // request para atualizar dados
        getAllHomeAcListItem()
    }
    fun excluirPasta(pasta: Pasta){
        //TODO:: Fazer a exclusão de pasta do firebase também

        // request para atualizar dados
        getAllHomeAcListItem()
    }
}


/*

val dialog = OpcaoDialogFragment()
dialog.show(supportFragmentManager, "OpcaoDialog")
 */