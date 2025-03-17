package com.example.brash.aprendizado.gestaoDeConteudo.domain.useCase

import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Cartao
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.CategoriaDoAprendizado
import com.example.brash.aprendizado.gestaoDeConteudo.utils.NivelRevisao
import java.time.LocalDateTime
import kotlin.math.max
import kotlin.math.roundToInt

/**
 * The SuperMemo2 algorithm for spaced repetition.
 *
 * This class implements the SuperMemo2 algorithm, which adjusts the review schedule of a card based on
 * the user's feedback on previous reviews. It calculates the next review date, interval, and the card's
 * learning category, improving retention of the learned material.
 */
class SuperMemo2 {

    companion object{

        /**
         * The SuperMemo2 algorithm for spaced repetition.
         *
         * This class implements the SuperMemo2 algorithm, which adjusts the review schedule of a card based on
         * the user's feedback on previous reviews. It calculates the next review date, interval, and the card's
         * learning category, improving retention of the learned material.
         */
        fun reviewCard(card : Cartao, nivelRevisao: NivelRevisao) : Cartao{

            val newEfactor = calculateNewEFactor(card.fatorDeRevisao, nivelRevisao.valor)
            if(nivelRevisao == NivelRevisao.DIFICIL){ // selecionou difícil, não muda nada no cartão, só efactor
                card.categoriaDoAprendizado = CategoriaDoAprendizado.APRENDENDO
                card.fatorDeRevisao = newEfactor
                card.dataDeRevisao = LocalDateTime.now()
                return card
            }
            val newInterval = calculateNewInterval(card, newEfactor, nivelRevisao.valor)
            val newReviewDate = LocalDateTime.now().plusDays(newInterval.toLong())
            val newCategory = defineNewCategory(newInterval, card.categoriaDoAprendizado)

            card.fatorDeRevisao = newEfactor
            card.dataDeRevisao = newReviewDate
            card.intervaloRevisao = newInterval
            card.categoriaDoAprendizado = newCategory

            return card
        }

        /**
         * Calculates the new interval for the next review based on the user's feedback.
         *
         * @param card The [Cartao] object representing the card being reviewed.
         * @param eFactor The updated EFactor value after review.
         * @param quality The user's rating for the card (1 to 5, where 1 is "very difficult" and 5 is "easy").
         * @return The new interval in days until the next review.
         */
         fun calculateNewInterval(card : Cartao, eFactor : Double, quality : Int) : Int{
            return when {
                quality < 3 -> 0
                card.intervaloRevisao == 0 -> 1
                card.intervaloRevisao == 1 -> 6
                else -> (card.intervaloRevisao * eFactor).roundToInt()
            }
        }

        /**
         * Calculates the new EFactor (ease factor) for a card.
         *
         * @param oldFactor The current EFactor value.
         * @param quality The user's rating for the card (1 to 5).
         * @return The updated EFactor.
         */
        private fun calculateNewEFactor(oldFactor : Double, quality : Int) : Double{
            val newEFactor = oldFactor+(0.1-(5-quality)*(0.08+(5-quality)*0.02))
            return max(1.3, newEFactor)
        }

        /**
         * Defines the new category for the card based on the updated review interval.
         *
         * @param newInterval The new review interval.
         * @param currentCategory The current learning category of the card.
         * @return The updated [CategoriaDoAprendizado] category.
         */
        private fun defineNewCategory(newInterval : Int, currentCategory : CategoriaDoAprendizado) : CategoriaDoAprendizado{
            return when{
                newInterval in 1..21 -> CategoriaDoAprendizado.RECENTE
                newInterval > 21 -> CategoriaDoAprendizado.MADURO
                newInterval == 0 && currentCategory == CategoriaDoAprendizado.NOVO -> CategoriaDoAprendizado.APRENDENDO
                newInterval == 0 && currentCategory != CategoriaDoAprendizado.APRENDENDO -> CategoriaDoAprendizado.REAPRENDENDO
                else -> currentCategory // Aprendendo e errou, continua Aprendendo
            }
        }
    }
}