package com.example.brash.aprendizado.gestaoDeConteudo.ui.view.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Cartao
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.listener.OnCartaoListener
import com.example.brash.databinding.GtcItemCartaoBinding

/**
 * ViewHolder for displaying a "Cartao" (Card) item in a RecyclerView.
 *
 * This class is responsible for binding the card's data to the UI components
 * and handling user interactions, such as item clicks.
 *
 * @param binding The view binding to access UI elements.
 * @param listener The listener that handles item click events.
 */
class ListaCartaoVH(private val binding: GtcItemCartaoBinding, private val listener: OnCartaoListener) : RecyclerView.ViewHolder(binding.root) {

    /**
     * Binds the card data to the UI components.
     *
     * @param cartao The Cartao object to be displayed.
     */
    fun bindVH(cartao: Cartao){
        binding.ItemCartaoTextViewPergunta.text = cartao.pergunta
        binding.ItemCartaoTextViewResposta.text = cartao.resposta

        // Set a click listener on the card layout to trigger the OnCartaoListener
        binding.ItemCartaoLayout.setOnClickListener {
            listener.onClick(cartao)
        }
    }
}