package com.example.brash.aprendizado.gestaoDeConteudo.utils

import com.example.brash.R
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Baralho
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Cartao
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.CategoriaDoAprendizado
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Dica
import java.time.LocalDateTime

class nucleoUtils {
    companion object{
        fun getCartoes() : List<Cartao>{
            val listCartoes = mutableListOf<Cartao>()
            for (i in 1..20){
                val card = Cartao(
                    idCartao = 1,
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