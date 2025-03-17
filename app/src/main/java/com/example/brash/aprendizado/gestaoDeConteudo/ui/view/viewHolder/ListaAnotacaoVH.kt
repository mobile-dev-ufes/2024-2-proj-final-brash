package com.example.brash.aprendizado.gestaoDeConteudo.ui.view.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Anotacao
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.listener.OnAnotacaoListener
import com.example.brash.databinding.GtcItemAnotacaoBinding

/**
 * ViewHolder for displaying an annotation item in a RecyclerView.
 *
 * This class binds the annotation data to the corresponding UI components
 * and handles user interactions.
 *
 * @param binding The view binding for the annotation item layout.
 * @param listener The click listener for handling user interactions.
 */
class ListaAnotacaoVH(private val binding: GtcItemAnotacaoBinding, private val listener: OnAnotacaoListener) : RecyclerView.ViewHolder(binding.root) {

    /**
     * Binds the annotation data to the view.
     *
     * @param anotacao The annotation object to be displayed.
     */
    fun bindVH(anotacao: Anotacao){
        binding.ItemAnotacaoTextViewNome.text = anotacao.nome

        // Set up the click listener for the item layout
        binding.ItemAnotacaoLayout.setOnClickListener {
            listener.onClick(anotacao)
        }
    }
}