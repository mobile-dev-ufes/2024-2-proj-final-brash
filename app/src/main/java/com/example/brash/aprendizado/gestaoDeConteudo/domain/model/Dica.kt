package com.example.brash.aprendizado.gestaoDeConteudo.domain.model

data class Dica (
    var idDica: String = "",
    var texto: String = "",
    var cartao: Cartao? = null
){
    override fun toString(): String {
        return "Dica(id='$idDica', texto='$texto', idCartao='${cartao?.idCartao}')"
    }
}