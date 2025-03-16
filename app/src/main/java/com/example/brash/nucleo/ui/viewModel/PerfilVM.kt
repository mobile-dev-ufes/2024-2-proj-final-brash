package com.example.brash.nucleo.ui.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

import android.util.Log
import com.example.brash.nucleo.data.repository.UsuarioRepository
import com.example.brash.nucleo.domain.model.IconeDeUsuario
import com.example.brash.nucleo.domain.model.Usuario
import com.example.brash.nucleo.utils.IconeCor
import com.example.brash.nucleo.utils.IconeImagem


/**
 * ViewModel responsible for managing the profile-related data and logic.
 *
 * It holds the user's icon information, including the image and color, and provides
 * methods to update and retrieve this information.
 *
 * @param application The application context.
 */
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
    val usuarioRepository = UsuarioRepository()

    /**
     * Initializes the user's icon with a default value and sets it as the focused icon.
     */
    fun getIconeUsuarioLogado() {
        _iconeEmFoco.value = IconeDeUsuario(imagem = IconeImagem.PADRAO, cor = IconeCor.DEEP_PURPLE )
        _corEmFoco.value = _iconeEmFoco.value!!.cor
        _imagemEmFoco.value = _iconeEmFoco.value!!.imagem
        Log.d("ListaPastaAdapter", "DEFINIÇÃO DAS PASTAS")
    }

    /**
     * Sets the given [iconeDeUsuario] as the focused icon.
     *
     * @param iconeDeUsuario The icon to be set as the focused one.
     */
    fun setIconeEmFoco(iconeDeUsuario: IconeDeUsuario){
        _iconeEmFoco.value = iconeDeUsuario
        Log.d("HomeDialogs", "Defini Baralho em FOCO")
    }

    /**
     * Sets the given [iconeImagem] as the focused image.
     *
     * @param iconeImagem The image to be set as the focused one.
     */
    fun setImagemEmFoco(iconeImagem: IconeImagem){
        _imagemEmFoco.value = iconeImagem
        Log.d("HomeDialogs", "Defini Baralho em FOCO")
    }

    /**
     * Sets the given [iconeCor] as the focused color.
     *
     * @param iconeCor The color to be set as the focused one.
     */
    fun setCorEmFoco(iconeCor: IconeCor){
        _corEmFoco.value = iconeCor
        Log.d("HomeDialogs", "Defini Baralho em FOCO")
    }

    /**
     * Method for editing the user details.
     *
     * Currently, it is a placeholder function and does not contain logic.
     * It can be implemented to handle user data editing in the future.
     *
     * @param usuario The user whose data is to be edited.
     */
    fun editarUsuario(usuario: Usuario){
        // Logic to edit the user's details can be implemented here.
    }

}

/*

val dialog = OpcaoDialogFragment()
dialog.show(supportFragmentManager, "OpcaoDialog")
 */