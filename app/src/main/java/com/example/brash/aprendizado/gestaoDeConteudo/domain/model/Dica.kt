package com.example.brash.aprendizado.gestaoDeConteudo.domain.model

data class Dica (
    var idDica: String = "",
    var texto: String = "",
    var cartao: Cartao? = null
)