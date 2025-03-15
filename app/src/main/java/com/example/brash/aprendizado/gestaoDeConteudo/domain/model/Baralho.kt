package com.example.brash.aprendizado.gestaoDeConteudo.domain.model

import com.example.brash.nucleo.domain.model.Usuario


data class Baralho (
    val idBaralho: String = "",
    val nome: String = "",
    val descricao: String = "",
    val areasDoConhecimento: MutableList<String> = mutableListOf(),
    val areasEspecificas: MutableList<String> = mutableListOf(),
    val publico: Boolean = false,
    val cartoesNovosPorDia: Int = 20,
    val usuario: Usuario? = null,
    var pasta: Pasta? = null
){
    override fun toString(): String {
        return "Baralho(id='$idBaralho', nome='$nome', pastaId='${pasta?.idPasta}')"
    }
}