package com.example.brash.aprendizado.gestaoDeConteudo.domain.model



/**
 * Data class representing a the necessary public deck information which is displayed to the user when searching for public decks.
 *
 * @property  idBaralhoPublico identifier for the public deck entry in the database.
 * @property idBaralho identifier for the deck in the database.
 * @property idUsuario identifier for the user in the database.
 * @property numeroCartoesBaralho the quantity of cards from the deck.
 * @property nomeBaralho the name of the deck.
 * @property descricaoBaralho the description of the deck.
 * @property nomeDeUsuario the user name that owns the deck.
 * @property nomeDeExibicaoUsuario the user exhibition name of the user that owns the deck.
 */
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

