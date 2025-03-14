package com.example.brash.utilsGeral

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Baralho
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Cartao
import com.example.brash.nucleo.domain.model.IconeDeUsuario
import com.example.brash.nucleo.domain.model.Usuario
import com.example.brash.nucleo.utils.IconeCor
import com.example.brash.nucleo.utils.IconeImagem
import com.google.firebase.auth.FirebaseAuth


class AppVM(application: Application) : AndroidViewModel(application) {

    private var _usuarioLogado = MutableLiveData<Usuario>()
    val usuarioLogado get() = _usuarioLogado

    private var _cartaoEmAC = MutableLiveData<Cartao>()
    private var _baralhoEmAC = MutableLiveData<Baralho>()
    val cartaoEmAC get() = _cartaoEmAC
    val baralhoEmAC get() = _baralhoEmAC

    private val auth = FirebaseAuth.getInstance()

    // Função para definir o usuário logado
    fun updateUsuarioLogado(usuario: Usuario) {
        //TODO:: Fazer verificação da mudança das informações
        _usuarioLogado.value = usuario
    }

    // Função para solicitar os dados do usuário logado
    fun requestUsuarioLogado() {
        // Aqui, você pode obter dados do Firebase ou de outro serviço
        // Exemplo com dados mockados para demonstração:
        //TODO:: Fazer verificação da mudança das informações
        _usuarioLogado.value = Usuario(nomeDeUsuario = "MerlinMago", nomeDeExibicao = "CaixinhaMaker", iconeDeUsuario = IconeDeUsuario(cor = IconeCor.HOT_PINK, imagemPath = IconeImagem.CARRO))
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