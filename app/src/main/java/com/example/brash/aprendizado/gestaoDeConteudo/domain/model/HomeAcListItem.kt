package com.example.brash.aprendizado.gestaoDeConteudo.domain.model

sealed class HomeAcListItem {
    data class HomeAcPastaItem(
        val pasta: Pasta,
        var isExpanded: Boolean = false
    ) : HomeAcListItem()

    data class HomeAcBaralhoItem(
        val baralho: Baralho
    ) : HomeAcListItem()
}