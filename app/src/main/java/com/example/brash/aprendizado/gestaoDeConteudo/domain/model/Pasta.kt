package com.example.brash.aprendizado.gestaoDeConteudo.domain.model

import com.example.brash.nucleo.domain.model.Usuario

/**
 * Represents a folder (Pasta) that contains a collection of `Baralho` (Deck) objects
 * and is associated with a specific `Usuario` (User).
 *
 * @property idPasta The unique identifier for this folder.
 * @property nome The name of the folder.
 * @property usuario The user associated with this folder. Can be null if not associated.
 * @property baralhos A mutable list of `Baralho` (Deck) objects contained within the folder.
 *
 * @constructor Creates a `Pasta` (Folder) instance with the specified properties.
 */
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