package com.example.brash.aprendizado.gestaoDeConteudo.domain.model

/**
 * A data class representing a tip or suggestion that can be associated with a `Cartao`.
 * This class holds information about the tip's content and its associated card.
 *
 * @property idDica A unique identifier for the tip.
 * @property texto The text content of the tip.
 * @property cartao An optional `Cartao` associated with the tip. This can be null if there is no associated card.
 */
data class Dica (
    var idDica: String = "",
    var texto: String = "",
    var cartao: Cartao? = null
){
    override fun toString(): String {
        return "Dica(id='$idDica', texto='$texto', idCartao='${cartao?.idCartao}')"
    }
}