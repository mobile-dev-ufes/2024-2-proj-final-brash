package com.example.brash.aprendizado.gestaoDeAuxilioDeAprendizado.domain.model

import com.example.brash.nucleo.domain.model.Usuario
import java.time.LocalDateTime

//NOT USED YET

data class RelatorioGeral(
    val usuario: Usuario,  // Adiciona a propriedade usuario
    override val idRelatorio: Long,
    override val numeroDeCartoesNovos: Int,
    override val numeroDeCartoesRecentes: Int,
    override val numeroDeCartoesMaduros: Int,
    override val numeroDeAcertosDeCartoesMaduros: Int,
    override val numeroDeCartoesAprendendo: Int,
    override val numeroDeCartoesReaprendendo: Int,
    override val data: LocalDateTime,
    override val minutosRevisados: Double
) : Relatorio(
    idRelatorio,
    numeroDeCartoesNovos,
    numeroDeCartoesRecentes,
    numeroDeCartoesMaduros,
    numeroDeAcertosDeCartoesMaduros,
    numeroDeCartoesAprendendo,
    numeroDeCartoesReaprendendo,
    data,
    minutosRevisados
)
