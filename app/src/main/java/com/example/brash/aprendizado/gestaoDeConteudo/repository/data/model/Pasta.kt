package com.example.brash.aprendizado.gestaoDeConteudo.repository.data.model

import com.example.brash.nucleo.repository.data.model.Usuario

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