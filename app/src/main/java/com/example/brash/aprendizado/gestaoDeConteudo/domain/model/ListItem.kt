package com.example.brash.aprendizado.gestaoDeConteudo.domain.model

import androidx.browser.browseractions.BrowserActionsIntent.BrowserActionsItemId

sealed class HomeAcListItem {
    data class PastaItem(
        val idPasta: Long = 0,
        val nome: String = "",
        val baralhos: MutableList<BaralhoItem> = mutableListOf(),
        var isExpanded: Boolean = false
    ) : HomeAcListItem()
    data class BaralhoItem(
        val idBaralho: Long = 0,
        val nome: String = ""
    ) : HomeAcListItem()
}