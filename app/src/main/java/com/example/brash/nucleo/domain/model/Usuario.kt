package com.example.brash.nucleo.domain.model
import java.time.LocalDateTime

/**
 * Represents a user in the system.
 *
 * @param idUsuario The unique identifier for the user. Defaults to "0".
 * @param nomeDeExibicao The display name of the user.
 * @param nomeDeUsuario The username used by the user for login.
 * @param dataDeCriacao The timestamp representing when the user was created. Defaults to the current time.
 * @param email The email address of the user.
 * @param iconeDeUsuario The user's profile icon. Defaults to an empty IconeDeUsuario.
 */
data class Usuario (
    val idUsuario: String = "0",
    val nomeDeExibicao: String = "",
    val nomeDeUsuario: String = "",
    //val senha: String,
    val dataDeCriacao: LocalDateTime = LocalDateTime.now(),
    val email: String = "",
    //val codigoAleatorio : String?,
    //val idioma : Idioma = Idioma.INGLES,
    val iconeDeUsuario: IconeDeUsuario = IconeDeUsuario()
)