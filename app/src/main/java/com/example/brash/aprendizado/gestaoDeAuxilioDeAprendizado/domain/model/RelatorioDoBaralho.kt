package com.example.brash.aprendizado.gestaoDeAuxilioDeAprendizado.domain.model

import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Baralho
import java.time.LocalDateTime

//NOT USED YET

// Data class RelatorioDoBaralho que herda de Relatorio
data class RelatorioDoBaralho(
    val baralho: Baralho,  // Adiciona a propriedade baralho
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
