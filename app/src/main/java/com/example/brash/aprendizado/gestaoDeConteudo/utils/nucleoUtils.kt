package com.example.brash.aprendizado.gestaoDeConteudo.utils

import com.example.brash.R

class nucleoUtils {
}

enum class OrdemDeBuscaHome { ALFANUMERICO, QUANTIDADE_CARTOES, SEM_ORDEM }
enum class FiltroDeBuscaHome { TODOS, NOME_DE_BARALHO, NOME_DE_PASTA, BARALHOS_PUBLICOS, AREAS_DO_CONHECIMENTO, AREAS_ESPECIFICAS }


fun getColorResetMoverBaralho(): Int{
    return R.color.charcoal_gray
}
fun getColorSetMoverBaralho(): Int{
    return R.color.lavender_purple
}