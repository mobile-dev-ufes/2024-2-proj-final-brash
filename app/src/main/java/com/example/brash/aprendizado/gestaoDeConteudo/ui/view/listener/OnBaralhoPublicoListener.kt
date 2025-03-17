package com.example.brash.aprendizado.gestaoDeConteudo.ui.view.listener

import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Baralho

/**
 * Interface for handling interactions with public deck items.
 *
 * This listener is used to handle click events on `Baralho` (deck) objects.
 */
interface OnBaralhoPublicoListener {
    /**
     * Called when a public deck is clicked.
     *
     * @param b The `Baralho` object that was clicked.
     */
    fun onClick(b: Baralho)
}