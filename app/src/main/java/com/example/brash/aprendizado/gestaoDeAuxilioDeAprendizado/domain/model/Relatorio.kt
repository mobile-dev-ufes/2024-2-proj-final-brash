package com.example.brash.aprendizado.gestaoDeAuxilioDeAprendizado.domain.model

import java.time.LocalDateTime

//NOT USED YET

abstract class Relatorio(
    open val idRelatorio: Long,
    open val numeroDeCartoesNovos: Int,
    open val numeroDeCartoesRecentes: Int,
    open val numeroDeCartoesMaduros: Int,
    open val numeroDeAcertosDeCartoesMaduros: Int,
    open val numeroDeCartoesAprendendo: Int,
    open val numeroDeCartoesReaprendendo: Int,
    open val data: LocalDateTime,
    open val minutosRevisados: Double

)
