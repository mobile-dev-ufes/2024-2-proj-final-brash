package com.example.brash.aprendizado.gestaoDeConteudo.domain.model

data class Dica (
    val idDica: Long,
    val texto: String,
    val cartao: Cartao
)