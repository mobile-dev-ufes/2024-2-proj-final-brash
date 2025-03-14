package com.example.brash.nucleo.ui.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.brash.nucleo.utils.UtilsFoos
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.example.brash.nucleo.utils.MyPreferences
import kotlin.math.sign

import android.util.Log
import com.example.brash.nucleo.domain.model.IconeDeUsuario
import com.example.brash.nucleo.domain.model.Usuario
import com.example.brash.nucleo.utils.IconeCor
import com.example.brash.nucleo.utils.IconeImagem
import java.lang.Exception

class PerfilVM(application: Application) : AndroidViewModel(application) {

    private var _teste = MutableLiveData<Boolean>()
    val teste get() = _teste

    private var _iconeDeUsuarioMsg = MutableLiveData<Int>()
    val iconeDeUsuarioMsg get() = _iconeDeUsuarioMsg

    private var _iconeEmFoco = MutableLiveData<IconeDeUsuario>()
    val iconeEmFoco get() = _iconeEmFoco

    private var _corEmFoco = MutableLiveData<IconeCor>()
    val corEmFoco get() = _corEmFoco

    private var _imagemEmFoco = MutableLiveData<IconeImagem>()
    val imagemEmFoco get() = _imagemEmFoco
    //private var _opcoesDeBusca = MutableLiveData<OpcoesDeBuscaBaralhoPublico>()
    //val opcoesDeBusca get() = _opcoesDeBusca

    fun getIconeUsuarioLogado() {

        _iconeEmFoco.value = IconeDeUsuario(imagemPath = IconeImagem.PADRAO, cor = IconeCor.DEEP_PURPLE )
        _corEmFoco.value = _iconeEmFoco.value!!.cor
        _imagemEmFoco.value = _iconeEmFoco.value!!.imagemPath
        Log.d("ListaPastaAdapter", "DEFINIÇÃO DAS PASTAS")
    }

    fun setIconeEmFoco(iconeDeUsuario: IconeDeUsuario){
        _iconeEmFoco.value = iconeDeUsuario
        Log.d("HomeDialogs", "Defini Baralho em FOCO")
    }

    fun setImagemEmFoco(iconeImagem: IconeImagem){
        _imagemEmFoco.value = iconeImagem
        Log.d("HomeDialogs", "Defini Baralho em FOCO")
    }

    fun setCorEmFoco(iconeCor: IconeCor){
        _corEmFoco.value = iconeCor
        Log.d("HomeDialogs", "Defini Baralho em FOCO")
    }

    /*
    fun updateOpcoesDeBusca(ordem: OrdemDeBuscaHome, filtro : FiltroDeBuscaHome){
        _opcoesDeBusca.value = OpcoesDeBuscaHome(ordem, filtro)
        Log.d("HomeDialogs", "Defini Pasta em FOCO")

        Log.d("HomeDialogs", ordem.toString())
        Log.d("HomeDialogs", filtro.toString())
    }*/

    fun editarUsuario(usuario: Usuario){
        //TODO:: requisitar o firebase a edição do usuário
        //TODO:: apenas requisitar se tiver ALGUMA informação diferente
        //TODO:: apenas confirmar a mudança do nome de usuário se for único no sistema, o restante pode sempre atualizar
        //TODO:: Se não conseguir alterar o nome de usuário ele altera o resto
    }

}

/*

val dialog = OpcaoDialogFragment()
dialog.show(supportFragmentManager, "OpcaoDialog")
 */