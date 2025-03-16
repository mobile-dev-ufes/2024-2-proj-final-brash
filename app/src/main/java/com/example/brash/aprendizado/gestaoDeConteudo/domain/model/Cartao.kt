package com.example.brash.aprendizado.gestaoDeConteudo.domain.model

import java.time.LocalDateTime

/**
 * Data class representing a flashcard with a question and its corresponding answer.
 * This class contains additional fields related to the revision process, such as the revision factor,
 * the next revision interval, and the category of the learning process.
 *
 * @property idCartao Unique identifier for the flashcard.
 * @property pergunta The question or prompt of the flashcard.
 * @property resposta The correct answer for the flashcard's question.
 * @property fatorDeRevisao A factor used in the revision process, which helps determine the frequency of future revisions.
 * @property intervaloRevisao The interval (in days) for the next revision.
 * @property dataDeRevisao The date and time when the last revision occurred.
 * @property dica A list of tips or hints associated with the flashcard to help with learning.
 * @property baralho The deck to which this flashcard belongs.
 * @property categoriaDoAprendizado The current stage or category of the learning process (e.g., `NOVO`, `RECENTE`).
 */
data class Cartao(
    var idCartao: String = "",
    var pergunta: String = "",
    var resposta: String = "",
    //val imagem: File,
    var fatorDeRevisao: Double = 2.5,
    var intervaloRevisao : Int = 0,
    var dataDeRevisao: LocalDateTime = LocalDateTime.now(),
    var dica: MutableList<Dica> = mutableListOf(),
    var baralho: Baralho = Baralho(),
    var categoriaDoAprendizado: CategoriaDoAprendizado = CategoriaDoAprendizado.NOVO
)
{
    override fun toString(): String {
        return "Cartao(id='$idCartao', pergunta='$pergunta', resposta='$resposta', idBaralho='${baralho.idBaralho}')"
    }
}