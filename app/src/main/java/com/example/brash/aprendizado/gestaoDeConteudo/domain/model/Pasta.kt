package com.example.brash.aprendizado.gestaoDeConteudo.domain.model

import com.example.brash.nucleo.domain.model.Usuario

data class Pasta (
    val idPasta: String = "",
    val nome: String = "",
    val usuario: Usuario? = null,
    var baralhos: MutableList<Baralho> = mutableListOf()
)



/*
(
    val id: Long,
    val nome: String
)

 */