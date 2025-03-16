package com.example.brash.aprendizado.gestaoDeConteudo.domain.model

data class Dica (
    val idDica: String = "",
    val texto: String = "",
    val cartao: Cartao? = null
)