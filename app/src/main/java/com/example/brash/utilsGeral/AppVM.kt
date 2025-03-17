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


/**
 * ViewModel responsible for managing user-related data and application state.
 *
 * @param application The application context.
 */
class AppVM(application: Application) : AndroidViewModel(application) {

    private var _usuarioLogado = MutableLiveData<Usuario>()
    val usuarioLogado get() = _usuarioLogado

    private var _cartaoEmAC = MutableLiveData<Cartao>()
    private var _baralhoEmAC = MutableLiveData<Baralho>()
    val cartaoEmAC get() = _cartaoEmAC
    val baralhoEmAC get() = _baralhoEmAC

    val usuarioRepository = UsuarioRepository()
    private val auth = FirebaseAuth.getInstance()

    /**
     * Retrieves a string resource from the application context.
     *
     * @param id The resource ID.
     * @return The corresponding string.
     */
    private fun getStringApplication(id : Int) : String{
        return getApplication<Application>().getString(id)
    }

    /**
     * Updates the logged-in user information.
     *
     * @param userName The new username.
     * @param exhibitionName The new display name.
     * @param iconColor The new user icon color.
     * @param iconImage The new user icon image.
     * @param onSuccess Callback executed if the update is successful.
     */
    fun updateUsuarioLogado(userName : String, exhibitionName : String, iconColor : IconeCor, iconImage: IconeImagem, onSuccess : () -> Unit) {
        viewModelScope.launch {
            if (processaInfoUser(userName, exhibitionName) &&
                (_usuarioLogado.value!!.nomeDeUsuario == userName || verificaNomeDeUsuarioUnico(userName))
                ) {

                val result2 = usuarioRepository.updateUser(userName, exhibitionName, iconColor, iconImage)
                result2.onSuccess {
                    _usuarioLogado.value = Usuario(
                        nomeDeUsuario = userName,
                        nomeDeExibicao = exhibitionName,
                        iconeDeUsuario = IconeDeUsuario(iconColor, iconImage)
                    )
                    onSuccess()
                }.onFailure { e ->
                    UtilsFoos.showToast(
                        getApplication(),
                        getStringApplication(R.string.erro_requisicao_banco_dados_firebase)
                    )
                }
            }
        }
    }

    /**
     * Updates the user information in the repository.
     *
     * @param userName The new username.
     * @param exhibitionName The new display name.
     * @param iconColor The new user icon color.
     * @param iconImage The new user icon image.
     * @param onSuccess Callback executed if the update is successful.
     */
    private suspend fun atualizaUsuario(userName : String, exhibitionName : String, iconColor : IconeCor, iconImage: IconeImagem, onSuccess : () -> Unit) {

        if (processaInfoUser(userName, exhibitionName)) {
            val result2 = usuarioRepository.updateUser(userName, exhibitionName, iconColor, iconImage)
            result2.onSuccess {
                _usuarioLogado.value = Usuario(
                    nomeDeUsuario = userName,
                    nomeDeExibicao = exhibitionName,
                    iconeDeUsuario = IconeDeUsuario(iconColor, iconImage)
                )
                onSuccess()
            }.onFailure { e ->
                UtilsFoos.showToast(
                    getApplication(),
                    getStringApplication(R.string.erro_requisicao_banco_dados_firebase)
                )
            }
        }
    }

    /**
     * Validates user input for username and display name.
     *
     * @param userName The username.
     * @param exhibitionName The display name.
     * @return `true` if the input is valid, `false` otherwise.
     */
    fun processaInfoUser(userName : String, exhibitionName : String): Boolean {

        if (userName.isEmpty() || exhibitionName.isEmpty()) {
            UtilsFoos.showToast(getApplication(), getStringApplication(R.string.nuc_preencha_todos_campos))
            return false
        }


        return true
    }

    suspend fun verificaNomeDeUsuarioUnico(userName: String): Boolean{
        val userExists = usuarioRepository.checkExistsUserName(userName)
        userExists.onSuccess { exists ->
            if (exists) {
                UtilsFoos.showToast(getApplication(), getStringApplication(R.string.nuc_msg_erro_nome_usuario_ja_cadastrado))
                return false
            }
        }

        return true
    }

    /**
     * Requests the logged-in user's data from the repository.
     */
    fun requestUsuarioLogado() {
        viewModelScope.launch {
            val result = usuarioRepository.getUser()
            result
                .onSuccess { user ->
                    _usuarioLogado.value = user

                }.onFailure { e ->
                    UtilsFoos.showToast(getApplication(), getStringApplication(R.string.erro_requisicao_banco_dados_firebase))
                }
        }
    }

    /**
     * Sets the active deck in the application.
     *
     * @param baralho The deck to be set.
     */
    fun setBaralhoEmAC(baralho: Baralho){
        _baralhoEmAC.value = baralho
    }

    /**
     * Updates the active deck in the database.
     *
     * @param baralho The deck to be updated.
     */
    fun updateBaralhoEmAC(baralho: Baralho){
        // TODO: Database request to update the deck
    }

    /**
     * Sets the active flashcard in the application.
     *
     * @param cartao The flashcard to be set.
     */
    fun setCartaoEmAC(cartao: Cartao){
        _cartaoEmAC.value = cartao
    }

    /**
     * Updates the active flashcard in the database.
     *
     * @param cartao The flashcard to be updated.
     */
    fun updateCartaoEmAC(cartao: Cartao){
        // TODO: Database request to update the flashcard
    }
}