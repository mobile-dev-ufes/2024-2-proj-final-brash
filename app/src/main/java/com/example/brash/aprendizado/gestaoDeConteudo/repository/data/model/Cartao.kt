package com.example.brash.aprendizado.gestaoDeConteudo.repository.data.model

import com.example.brash.aprendizado.gestaoDeConteudo.repository.utils.CategoriaDoAprendizado
import java.io.File
import java.time.LocalDateTime

data class Cartao(
    val idCartao: Long,
    val pergunta: String,
    val resposta: String,
    val imagem: File,
    val fatorDeRevisao: Double,
    val dataDeRevisao: LocalDateTime,
    val dica: MutableList<Dica>,
    val baralho: Baralho,
    val categoriaDoAprendizado: CategoriaDoAprendizado
)