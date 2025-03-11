package com.example.brash.aprendizado.gestaoDeConteudo.domain.model

import androidx.browser.browseractions.BrowserActionsIntent.BrowserActionsItemId

sealed class HomeAcListItem {
    data class HomeAcPastaItem(
        val idPasta: Long = 0,
        val nome: String = "",
        val baralhos: MutableList<HomeAcBaralhoItem> = mutableListOf(),
        var isExpanded: Boolean = false
    ) : HomeAcListItem()
    data class HomeAcBaralhoItem(
        val idBaralho: Long = 0,
        val nome: String = ""
    ) : HomeAcListItem()
}