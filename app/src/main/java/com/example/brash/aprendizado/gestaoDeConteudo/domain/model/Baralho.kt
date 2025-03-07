package com.example.brash.aprendizado.gestaoDeConteudo.domain.model

import com.example.brash.nucleo.domain.model.Usuario

data class Baralho (
    val idBaralho: Long,
    val nome: String,
    val descricao: String,
    val areasDoConhecimento: MutableList<String>,
    val areasEspecificas: MutableList<String>,
    val publico: Boolean,
    val cartoesNovosPorDia: Int,
    val usuario: Usuario?,
    val pasta: Pasta?
)