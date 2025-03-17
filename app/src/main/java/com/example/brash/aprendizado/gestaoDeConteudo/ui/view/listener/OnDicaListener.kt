package com.example.brash.aprendizado.gestaoDeConteudo.ui.view.listener

import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Dica

/**
 * Interface for handling interactions with hint (Dica) items.
 *
 * This listener is used to handle click events on `Dica` objects.
 */
interface OnDicaListener {
    /**
     * Called when a hint (Dica) is clicked.
     *
     * @param dica The `Dica` object that was clicked.
     */
    fun onClick(dica: Dica)
}