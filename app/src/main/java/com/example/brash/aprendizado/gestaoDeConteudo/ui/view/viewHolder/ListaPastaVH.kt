package com.example.brash.aprendizado.gestaoDeConteudo.ui.view.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Pasta
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.listener.OnPastaListener
import com.example.brash.databinding.GtcItemPastaComIconeBinding

/**
 * ViewHolder for displaying a "Pasta" (Folder) item in a RecyclerView.
 *
 * This class is responsible for binding the folder's data to the UI components
 * and handling user interactions, such as item clicks.
 *
 * @param binding The view binding to access UI elements.
 * @param listener The listener that handles item click events.
 */
class ListaPastaVH (val binding: GtcItemPastaComIconeBinding, private val listener: OnPastaListener) : RecyclerView.ViewHolder(binding.root) {

    /**
     * Binds the folder data to the UI components.
     *
     * @param pasta The Pasta object to be displayed.
     */
    fun bindVH(pasta: Pasta){
        binding.ItemPastaComIconeTextViewNome.text = pasta.nome

        // Set a click listener on the item layout to trigger the OnPastaListener
        binding.ItemPastaComIconeLayout.setOnClickListener({
            listener.onClick(pasta)
        })
    }
}