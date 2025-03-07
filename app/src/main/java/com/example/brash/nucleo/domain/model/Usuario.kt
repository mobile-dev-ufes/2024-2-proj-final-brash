package com.example.brash.nucleo.domain.model
import java.time.LocalDateTime

data class Usuario (
    val idUsuario: Long,
    val nomeDeExibicao: String,
    val nomeDeUsuario: String,
    val senha: String,
    val dataDeCriacao: LocalDateTime,
    val email: String,
    val codigoAleatorio : String?,
    val idioma : Idioma,
    val iconeDeUsuario: IconeDeUsuario
)