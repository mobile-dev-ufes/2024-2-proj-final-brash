package com.example.brash.aprendizado.gestaoDeConteudo.ui.view.listener

import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Anotacao
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Cartao

/**
 * Interface for handling interactions with annotation items.
 *
 * This listener is used to handle click events on annotation objects (`Anotacao`).
 */
interface OnAnotacaoListener {
    /**
     * Called when an annotation is clicked.
     *
     * @param anotacao The annotation that was clicked.
     */
    fun onClick(anotacao: Anotacao)
}