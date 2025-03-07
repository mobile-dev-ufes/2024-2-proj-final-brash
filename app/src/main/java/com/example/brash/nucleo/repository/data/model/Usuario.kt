package com.example.brash.nucleo.repository.data.model
import com.example.brash.nucleo.repository.utils.Constants
import com.example.brash.nucleo.repository.utils.Idioma
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