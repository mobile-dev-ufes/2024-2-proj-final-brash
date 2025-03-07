package com.example.brash.aprendizado.gestaoDeAuxilioDeAprendizado.repository.data.model

import com.example.brash.nucleo.repository.data.model.Usuario
import java.time.LocalDateTime

data class Colocacao (
    val idColocacao: Long,
    val data: LocalDateTime,
    val posicao: Int,
    val pontuacao: Int,
    val usuario: Usuario
)