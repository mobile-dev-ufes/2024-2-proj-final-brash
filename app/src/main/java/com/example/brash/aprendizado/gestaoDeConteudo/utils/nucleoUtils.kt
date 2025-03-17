package com.example.brash.aprendizado.gestaoDeConteudo.utils


import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Baralho
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Cartao
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.CategoriaDoAprendizado
import java.time.LocalDateTime

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.example.brash.R

/**
 * Utility class providing helper functions related to learning content management.
 */
class nucleoUtils {
    companion object{

        /**
         * Generates a list of [Cartao] objects.
         * This is a mock function to simulate a collection of cards with sample data.
         *
         * @return A list of [Cartao] objects populated with sample data.
         */
        fun getCartoes() : List<Cartao>{
            val listCartoes = mutableListOf<Cartao>()
            for (i in 1..20){
                val card = Cartao(
                    idCartao = "1",
                     pergunta = "pergunta $i",
                     resposta= "resposta $i",
                     fatorDeRevisao = 2.5,
                     intervaloRevisao  = 0,
                     dataDeRevisao = LocalDateTime.now(),
                     dica = mutableListOf(),
                     baralho = Baralho(),
                     categoriaDoAprendizado = CategoriaDoAprendizado.NOVO
                )
                listCartoes.add(card)
            }
            return listCartoes
        }


    }
}

//enum class OrdemDeBuscaHome { ALFANUMERICO, QUANTIDADE_CARTOES, SEM_ORDEM }
//enum class FiltroDeBuscaHome { TODOS, NOME_DE_BARALHO, NOME_DE_PASTA, BARALHOS_PUBLICOS, AREAS_DO_CONHECIMENTO, AREAS_ESPECIFICAS }

//enum class OrdemDeBuscaListarCartao {ALFANUMERICO}

/**
 * Enum class for filtering the search of [Cartao] in the context of listing cards.
 */
enum class FiltroDeBuscaListarCartao {PERGUNTA, RESPOSTA}

/**
 * Gets the color code for the "reset" move action on a deck.
 *
 * @return The color resource ID for the reset move action.
 */
fun getColorResetMoverBaralho(): Int{
    return R.color.charcoal_gray
}

/**
 * Gets the color code for the "set" move action on a deck.
 *
 * @return The color resource ID for the set move action.
 */
fun getColorSetMoverBaralho(): Int{
    return R.color.lavender_purple
}

/**
 * Enum class for categorizing review levels of cards.
 * The value represents the ease of review or difficulty.
 *
 * @param valor The numeric value representing the difficulty level (5 = easy, 3 = hard, 0 = forgot).
 */
enum class NivelRevisao(val valor: Int) {
    FACIL(5),
    BOM(4),
    DIFICIL(3),
    ESQUECI(0)
}

/**
 * Displays an informational dialog with the description of the revision colors.
 *
 * @param context The context in which the dialog is displayed.
 */
fun showCartoesInfoDialog(context: Context) {
    // Infla o layout do Dialog
    val dialogView = LayoutInflater.from(context).inflate(R.layout.alert_dialog_descricao_cores_da_revisao, null)

    // Cria o AlertDialog
    val alertDialog = AlertDialog.Builder(context)
        .setView(dialogView)
        .create()

    // Exibe o AlertDialog
    alertDialog.show()
}

/**
 * Displays an informational dialog with the description of the revision colors in HomeAC.
 *
 * @param context The context in which the dialog is displayed.
 */
fun showCartoesAllInfoDialog(context: Context) {
    // Infla o layout do Dialog
    val dialogView = LayoutInflater.from(context).inflate(R.layout.alert_dialog_descricao_cores_do_cartao, null)

    // Cria o AlertDialog
    val alertDialog = AlertDialog.Builder(context)
        .setView(dialogView)
        .create()

    // Exibe o AlertDialog
    alertDialog.show()
}