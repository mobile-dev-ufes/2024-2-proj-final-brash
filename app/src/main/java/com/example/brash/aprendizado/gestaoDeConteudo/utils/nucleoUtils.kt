package com.example.brash.aprendizado.gestaoDeConteudo.utils


import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Baralho
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Cartao
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.CategoriaDoAprendizado
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Dica
import java.time.LocalDateTime

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.example.brash.R

class nucleoUtils {
    companion object{
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
enum class FiltroDeBuscaListarCartao {PERGUNTA, RESPOSTA}


fun getColorResetMoverBaralho(): Int{
    return R.color.charcoal_gray
}
fun getColorSetMoverBaralho(): Int{
    return R.color.lavender_purple
}

enum class NivelRevisao(val valor: Int) {
    FACIL(5),
    BOM(4),
    DIFICIL(3),
    ESQUECI(0)
}

fun showCartoesInfoDialog(context: Context) {
    // Infla o layout do Dialog
    val dialogView = LayoutInflater.from(context).inflate(R.layout.alert_dialog_descricao_cores_da_revisao, null)

    // Cria o AlertDialog
    val alertDialog = AlertDialog.Builder(context)
        .setView(dialogView)
        .setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }
        .create()

    // Exibe o AlertDialog
    alertDialog.show()
}