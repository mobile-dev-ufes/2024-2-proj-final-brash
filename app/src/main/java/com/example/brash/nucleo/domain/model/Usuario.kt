package com.example.brash.nucleo.domain.model
import java.time.LocalDateTime

data class Usuario (
    val idUsuario: Long = 0,
    val nomeDeExibicao: String = "",
    val nomeDeUsuario: String = "",
    //val senha: String,
    val dataDeCriacao: LocalDateTime = LocalDateTime.now(),
    val email: String = "",
    //val codigoAleatorio : String?,
    //val idioma : Idioma = Idioma.INGLES,
    val iconeDeUsuario: IconeDeUsuario = IconeDeUsuario()
)