package com.example.brash.aprendizado.gestaoDeConteudo.domain.useCase

import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Cartao
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.CategoriaDoAprendizado
import java.time.LocalDateTime
import kotlin.math.max
import kotlin.math.roundToInt

class SuperMemo2 {
    companion object{

        fun reviewCard(card : Cartao, quality : Int) : Cartao{

            val newEfactor = calculateNewEFactor(card.fatorDeRevisao, quality)
            if(quality == 3){ // selecionou difícil, não muda nada no cartão, só efactor
                card.fatorDeRevisao = newEfactor
                card.dataDeRevisao = LocalDateTime.now()
                return card
            }
            val newInterval = calculateNewInterval(card, newEfactor, quality)
            val newReviewDate = LocalDateTime.now().plusDays(newInterval.toLong())
            val newCategory = defineNewCategory(newInterval, card.categoriaDoAprendizado)

            card.fatorDeRevisao = newEfactor
            card.dataDeRevisao = newReviewDate
            card.intervaloRevisao = newInterval
            card.categoriaDoAprendizado = newCategory

            return card
        }

         fun calculateNewInterval(card : Cartao, eFactor : Double, quality : Int) : Int{
            return when {
                quality < 3 -> 0
                card.intervaloRevisao == 0 -> 1
                card.intervaloRevisao == 1 -> 6
                else -> (card.intervaloRevisao * eFactor).roundToInt()
            }
        }

        // EF':=EF+(0.1-(5-q)*(0.08+(5-q)*0.02))
        private fun calculateNewEFactor(oldFactor : Double, quality : Int) : Double{
            val newEFactor = oldFactor+(0.1-(5-quality)*(0.08+(5-quality)*0.02))
            return max(1.3, newEFactor)
        }

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