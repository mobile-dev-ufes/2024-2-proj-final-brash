package com.example.brash.aprendizado.gestaoDeConteudo.domain.model

/**
 * Data class representing a note associated with a flashcard deck.
 * An `Anotacao` contains a name, the note's content, and the deck it is related to.
 *
 * @property idAnotacao Unique identifier for the note.
 * @property nome The name or title of the note.
 * @property texto The textual content of the note.
 * @property baralho The flashcard deck to which the note belongs.
 */
data class Anotacao (
    val idAnotacao: Long = 0,
    val nome: String = "",
    val texto: String = "",
    val baralho: Baralho? = null
)