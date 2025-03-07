package com.example.brash.aprendizado.gestaoDeConteudo.domain.model

import com.example.brash.nucleo.domain.model.Usuario

data class Pasta (
    val idPasta: Long,
    val nome: String,
    val usuario: Usuario,
    val baralho: MutableList<Baralho>
)

/*
(
    val id: Long,
    val nome: String
)

 */