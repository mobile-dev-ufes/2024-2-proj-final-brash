package com.example.brash.aprendizado.gestaoDeConteudo.domain.model

import com.example.brash.nucleo.domain.model.Usuario

data class Pasta (
    var idPasta: String = "",
    var nome: String = "",
    val usuario: Usuario? = null,
    var baralhos: MutableList<Baralho> = mutableListOf()
){
    override fun toString(): String {
        return "Pasta(id='$idPasta', nome='$nome')"
    }
}


/*
(
    val id: Long,
    val nome: String
)

 */