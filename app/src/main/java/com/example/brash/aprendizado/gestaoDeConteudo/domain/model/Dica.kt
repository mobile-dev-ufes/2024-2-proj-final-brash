package com.example.brash.aprendizado.gestaoDeConteudo.domain.model

data class Dica (
    val idDica: Long = 0,
    val texto: String = "",
    val cartao: Cartao? = null
)