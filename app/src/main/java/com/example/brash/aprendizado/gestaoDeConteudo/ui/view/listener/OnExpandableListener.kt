package com.example.brash.aprendizado.gestaoDeConteudo.ui.view.listener

import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.HomeAcListItem

/**
 * Interface for handling interactions with expandable list items.
 *
 * This listener is used to handle click events on `HomeAcListItem` objects.
 */
interface OnExpandableListener {
    /**
     * Called when an expandable list item is clicked.
     *
     * @param p The `HomeAcListItem` object that was clicked.
     */
    fun onClick(p: HomeAcListItem)
}