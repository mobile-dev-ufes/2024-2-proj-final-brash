package com.example.brash.utilsGeral

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.brash.nucleo.domain.model.IconeDeUsuario
import com.example.brash.nucleo.domain.model.Usuario
import com.example.brash.nucleo.utils.IconeCor
import com.example.brash.nucleo.utils.IconeImagem
import com.google.firebase.auth.FirebaseAuth


class AppVM(application: Application) : AndroidViewModel(application) {

    private var _usuarioLogado = MutableLiveData<Usuario>()
    val usuarioLogado get() = _usuarioLogado

    private val auth = FirebaseAuth.getInstance()

    // Função para definir o usuário logado
    fun setUsuarioLogado(usuario: Usuario) {
        //TODO:: Fazer verificação da mudança das informações
        _usuarioLogado.value = usuario
    }

    // Função para solicitar os dados do usuário logado
    fun requestUsuarioLogado() {
        // Aqui, você pode obter dados do Firebase ou de outro serviço
        // Exemplo com dados mockados para demonstração:
        _usuarioLogado.value = Usuario(iconeDeUsuario = IconeDeUsuario(cor = IconeCor.HOT_PINK, imagemPath = IconeImagem.CARRO))
    }
}