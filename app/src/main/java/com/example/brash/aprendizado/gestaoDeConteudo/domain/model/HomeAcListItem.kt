package com.example.brash.aprendizado.gestaoDeConteudo.domain.model

/**
 * A sealed class representing items in the home activity list, which can be either a `Pasta` or a `Baralho`.
 * This allows different types of list items to be displayed in a single list.
 */
sealed class HomeAcListItem {
    /**
     * Represents a list item for a `Pasta`, which may be expanded or collapsed.
     *
     * @property pasta The `Pasta` object that this item represents.
     * @property isExpanded Indicates whether the pasta item is expanded to show its contents. Default is `false`.
     */
    data class HomeAcPastaItem(
        val pasta: Pasta,
        var isExpanded: Boolean = true
    ) : HomeAcListItem()

    /**
     * Represents a list item for a `Baralho`, a collection of flashcards.
     *
     * @property baralho The `Baralho` object that this item represents.
     */
    data class HomeAcBaralhoItem(
        val baralho: Baralho
    ) : HomeAcListItem()
}