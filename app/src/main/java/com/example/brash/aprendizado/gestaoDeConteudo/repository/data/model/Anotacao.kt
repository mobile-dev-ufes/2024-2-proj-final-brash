package com.example.brash.aprendizado.gestaoDeConteudo.repository.data.model

data class Anotacao (
    val idAnotacao: Long,
    val nome: String,
    val texto: String,
    val baralho: Baralho
)