package com.example.brash.aprendizado.gestaoDeConteudo.domain.model

import com.example.brash.aprendizado.gestaoDeConteudo.utils.FiltroDeBuscaListarCartao

/**
 * Represents the search options used to filter and list the cards (`Cartao`) in the application.
 *
 * @property filtrar The filter option to be applied for listing cards. The default is set to `PERGUNTA`
 * from the `FiltroDeBuscaListarCartao` enum, which filters by the question of the card.
 *
 * @constructor Creates an `OpcoesDeBuscaListarCartao` instance with the specified filter option.
 */
data class OpcoesDeBuscaListarCartao(
    val filtrar: FiltroDeBuscaListarCartao = FiltroDeBuscaListarCartao.PERGUNTA
)