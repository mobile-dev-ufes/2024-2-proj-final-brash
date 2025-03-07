package com.example.brash.aprendizado.gestaoDeConteudo.repository.data.model

import com.example.brash.nucleo.repository.data.model.Usuario

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