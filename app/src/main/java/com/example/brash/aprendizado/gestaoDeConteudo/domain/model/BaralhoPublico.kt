package com.example.brash.aprendizado.gestaoDeConteudo.domain.model

data class BaralhoPublico(
    var idBaralhoPublico : String = "",
    var idBaralho : String = "",
    var idUsuario : String = "",
    var numeroCartoesBaralho : Int = 0,
    var nomeBaralho : String = "",
    var descricaoBaralho : String = "",
    var nomeDeUsuario : String = "",
    var nomeDeExibicaoUsuario : String = "",
)

