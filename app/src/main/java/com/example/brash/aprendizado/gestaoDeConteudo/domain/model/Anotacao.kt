package com.example.brash.aprendizado.gestaoDeConteudo.domain.model

data class Anotacao (
    val idAnotacao: Long = 0,
    val nome: String = "",
    val texto: String = "",
    val baralho: Baralho? = null
)