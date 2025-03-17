package com.example.brash.aprendizado.gestaoDeConteudo.ui.view.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Dica
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.listener.OnDicaListener
import com.example.brash.databinding.GtcItemDicaBinding

/**
 * ViewHolder for displaying a "Dica" (Tip) item in a RecyclerView.
 *
 * This class is responsible for binding the tip's data to the UI components
 * and handling user interactions, such as item clicks.
 *
 * @param binding The view binding to access UI elements.
 * @param listener The listener that handles item click events.
 */
class ListaDicaVH(private val binding: GtcItemDicaBinding, private val listener: OnDicaListener) : RecyclerView.ViewHolder(binding.root) {

    /**
     * Binds the tip data to the UI components.
     *
     * @param dica The Dica object to be displayed.
     */
    fun bindVH(dica: Dica){
        binding.ItemDicaTextViewTexto.text = dica.texto

        // Set a click listener on the item layout to trigger the OnDicaListener
        binding.ItemDicaLayout.setOnClickListener {
            listener.onClick(dica)
        }
    }
}