package com.example.brash.aprendizado.gestaoDeAuxilioDeAprendizado.domain.model

import com.example.brash.nucleo.domain.model.Usuario
import java.time.LocalDateTime

//NOT USED YET

data class Colocacao (
    val idColocacao: Long,
    val data: LocalDateTime,
    val posicao: Int,
    val pontuacao: Int,
    val usuario: Usuario
)