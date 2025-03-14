package com.example.brash.aprendizado.gestaoDeConteudo.domain.model

import java.io.File
import java.time.LocalDateTime

data class Cartao(
    var idCartao: Long = 0,
    var pergunta: String = "",
    var resposta: String = "",
    //val imagem: File,
    var fatorDeRevisao: Double = 2.5,
    var intervaloRevisao : Int = 0,
    var dataDeRevisao: LocalDateTime = LocalDateTime.now(),
    var dica: MutableList<Dica> = mutableListOf(),
    var baralho: Baralho = Baralho(),
    var categoriaDoAprendizado: CategoriaDoAprendizado = CategoriaDoAprendizado.NOVO
)