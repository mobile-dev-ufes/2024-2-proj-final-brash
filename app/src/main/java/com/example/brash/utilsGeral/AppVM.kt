package com.example.brash.utilsGeral

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.brash.R
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Baralho
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Cartao
import com.example.brash.nucleo.data.repository.UsuarioRepository
import com.example.brash.nucleo.domain.model.IconeDeUsuario
import com.example.brash.nucleo.domain.model.Usuario
import com.example.brash.nucleo.utils.IconeCor
import com.example.brash.nucleo.utils.IconeImagem
import com.example.brash.nucleo.utils.UtilsFoos
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch


class AppVM(application: Application) : AndroidViewModel(application) {

    private var _usuarioLogado = MutableLiveData<Usuario>()
    val usuarioLogado get() = _usuarioLogado

    private var _cartaoEmAC = MutableLiveData<Cartao>()
    private var _baralhoEmAC = MutableLiveData<Baralho>()
    val cartaoEmAC get() = _cartaoEmAC
    val baralhoEmAC get() = _baralhoEmAC

    val usuarioRepository = UsuarioRepository()
    private val auth = FirebaseAuth.getInstance()

    private fun getStringApplication(id : Int) : String{
        return getApplication<Application>().getString(id)
    }

    // Função para definir o usuário logado
    fun updateUsuarioLogado(userName : String, exhibitionName : String, iconColor : IconeCor, iconImage: IconeImagem, onSuccess : () -> Unit) {
        viewModelScope.launch {
            if(_usuarioLogado.value!!.nomeDeUsuario == userName){
                atualizaUsuario(userName , exhibitionName , iconColor , iconImage,{
                    onSuccess()
                })
            }
            else{
                val result = usuarioRepository.checkExistsUserName(userName)
                result.onSuccess {
                    atualizaUsuario(userName , exhibitionName , iconColor , iconImage,{
                        onSuccess()
                    })
                }.onFailure { e->
                    UtilsFoos.showToast(
                        getApplication(),
                        "Ocorreu algum erro ao checar nome do usuário:: ${e}"
                    )
                }
            }
        }
    }
    private suspend fun atualizaUsuario(userName : String, exhibitionName : String, iconColor : IconeCor, iconImage: IconeImagem, onSuccess : () -> Unit){
        val result2 = usuarioRepository.updateUser(
            userName, exhibitionName, iconColor, iconImage)
        result2.onSuccess {
            _usuarioLogado.value = Usuario(
                nomeDeUsuario = userName, nomeDeExibicao = exhibitionName, iconeDeUsuario = IconeDeUsuario(iconColor, iconImage)
            )
            onSuccess()
        }.onFailure { e->
            UtilsFoos.showToast(
                getApplication(),
                "Ocorreu algum erro ao dar update no usuário:: ${e}"
            )
        }
    }
    fun processaInfoUser(userName : String, exhibitionName : String): Boolean{
        if(userName.isEmpty() || exhibitionName.isEmpty()){
            UtilsFoos.showToast(getApplication(), getStringApplication(R.string.nuc_preencha_todos_campos))
            return false
        }
        return true
    }

    // Função para solicitar os dados do usuário logado
    fun requestUsuarioLogado() {
        // Aqui, você pode obter dados do Firebase ou de outro serviço
        // Exemplo com dados mockados para demonstração:
        viewModelScope.launch {
            val result = usuarioRepository.getUser()
            result
                .onSuccess { user ->
                    _usuarioLogado.value = user

                }.onFailure { e ->
                    UtilsFoos.showToast(
                        getApplication(),
                        "Ocorreu algum erro ao obter usuário:: ${e}"
                    )
                }
        }
        //_usuarioLogado.value = Usuario(nomeDeUsuario = "MerlinMago", nomeDeExibicao = "CaixinhaMaker", iconeDeUsuario = IconeDeUsuario(cor = IconeCor.HOT_PINK, imagem = IconeImagem.CARRO))
    }

    fun setBaralhoEmAC(baralho: Baralho){
        _baralhoEmAC.value = baralho
    }

    fun updateBaralhoEmAC(baralho: Baralho){
        //TODO:: Requisição ao banco de dados para alterar o baralho
    }

    fun setCartaoEmAC(cartao: Cartao){
        _cartaoEmAC.value = cartao
    }
    fun updateCartaoEmAC(cartao: Cartao){
        //TODO:: Requisição ao banco de dados para alterar o baralho
    }
}