package com.example.brash.aprendizado.gestaoDeConteudo.domain.model

data class Dica (
    var idDica: String = "",
    val texto: String = "",
    var cartao: Cartao? = null
)