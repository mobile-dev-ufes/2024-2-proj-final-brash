package com.example.brash.aprendizado.gestaoDeConteudo.domain.model

import androidx.browser.browseractions.BrowserActionsIntent.BrowserActionsItemId

sealed class HomeAcListItem {
    data class HomeAcPastaItem(
        val pasta: Pasta,
        var isExpanded: Boolean = false
    ) : HomeAcListItem()

    data class HomeAcBaralhoItem(
        val baralho: Baralho
    ) : HomeAcListItem()
}