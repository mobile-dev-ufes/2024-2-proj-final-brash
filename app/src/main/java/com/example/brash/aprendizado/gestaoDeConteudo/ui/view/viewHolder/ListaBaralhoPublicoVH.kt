package com.example.brash.aprendizado.gestaoDeConteudo.ui.view.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Baralho
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.BaralhoPublico
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.listener.OnBaralhoPublicoListener
import com.example.brash.databinding.GtcItemBaralhoPublicoBinding
import java.util.Locale

/**
 * ViewHolder for displaying a public deck (Baralho) item in a RecyclerView.
 *
 * This class is responsible for binding the deck data to the UI components
 * and handling user interactions.
 *
 * @param binding The view binding for the deck item layout.
 * @param listener The click listener for handling user interactions.
 */
class ListaBaralhoPublicoVH(private val binding: GtcItemBaralhoPublicoBinding, private val listener: OnBaralhoPublicoListener) : RecyclerView.ViewHolder(binding.root) {

    /**
     * Binds the deck data to the view.
     *
     * @param baralho The deck object to be displayed.
     */
    fun bindVH(baralho: BaralhoPublico){
        binding.ItemBaralhoPublicoTextViewNome.text = baralho.nomeBaralho
        binding.ItemBaralhoPublicoTextViewQuantidadeCartoes.text =
            String.format(Locale.getDefault(), "%d", baralho.numeroCartoesBaralho)

        // Set up the click listener for the item layout
        binding.ItemBaralhoPublicoLayout.setOnClickListener {
            listener.onClick(baralho)
        }
    }

}
