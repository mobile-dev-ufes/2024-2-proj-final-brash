package com.example.brash.aprendizado.gestaoDeConteudo.domain.model

import com.example.brash.nucleo.domain.model.Usuario

/**
 * Data class representing a flashcard deck containing multiple flashcards.
 * A `Baralho` includes metadata such as its name, description, associated knowledge areas,
 * and user-specific information. It also contains settings for how many new cards should
 * be introduced daily and whether the deck is public or private.
 *
 * @property idBaralho Unique identifier for the flashcard deck.
 * @property nome The name of the flashcard deck.
 * @property descricao A description of the flashcard deck, providing additional context.
 * @property areasDoConhecimento A list of knowledge areas associated with the deck.
 * @property areasEspecificas A list of specific topics or sub-areas related to the deck.
 * @property publico A flag indicating if the deck is public (`true`) or private (`false`).
 * @property cartoesNovosPorDia The number of new flashcards to introduce to the user each day.
 * @property usuario The user associated with the deck.
 * @property pasta The folder to which the flashcard deck belongs.
 */
data class Baralho (
    var idBaralho: String = "",
    var nome: String = "",
    var descricao: String = "",
    val areasDoConhecimento: MutableList<String> = mutableListOf(),
    val areasEspecificas: MutableList<String> = mutableListOf(),
    var publico: Boolean = false,
    var cartoesNovosPorDia: Int = 20,
    val usuario: Usuario? = null,
    var pasta: Pasta? = null
){
    override fun toString(): String {
        return "Baralho(id='$idBaralho', nome='$nome', pastaId='${pasta?.idPasta}')"
    }
}