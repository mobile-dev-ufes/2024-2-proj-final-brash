package com.example.brash.aprendizado.gestaoDeConteudo.ui.view.listener

import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Cartao

/**
 * Interface for handling interactions with card items.
 *
 * This listener is used to handle click events on `Cartao` (card) objects.
 */
interface OnCartaoListener {
    /**
     * Called when a card is clicked.
     *
     * @param c The `Cartao` object that was clicked.
     */
    fun onClick(c: Cartao)
}