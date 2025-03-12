package com.example.brash.aprendizado.gestaoDeConteudo.domain.model

import java.io.File
import java.time.LocalDateTime

data class Cartao(
    val idCartao: Long = 0,
    val pergunta: String = "",
    val resposta: String = "",
    //val imagem: File,
    val fatorDeRevisao: Double = 0.0,
    val dataDeRevisao: LocalDateTime = LocalDateTime.now(),
    val dica: MutableList<Dica> = mutableListOf(),
    val baralho: Baralho = Baralho(),
    val categoriaDoAprendizado: CategoriaDoAprendizado = CategoriaDoAprendizado.NOVO
)