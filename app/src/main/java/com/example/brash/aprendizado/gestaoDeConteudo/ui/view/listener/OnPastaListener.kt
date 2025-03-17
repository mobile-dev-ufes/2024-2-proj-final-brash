package com.example.brash.aprendizado.gestaoDeConteudo.ui.view.listener

import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Pasta

/**
 * Interface for handling interactions with folder items.
 *
 * This listener is used to handle click events on `Pasta` (Folder) objects.
 */
interface OnPastaListener {
    /**
     * Called when a folder item is clicked.
     *
     * @param p The `Pasta` object that was clicked.
     */
    fun onClick(p: Pasta)
}