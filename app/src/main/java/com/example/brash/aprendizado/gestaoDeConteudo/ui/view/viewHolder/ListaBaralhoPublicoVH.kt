package com.example.brash.aprendizado.gestaoDeConteudo.ui.view.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Baralho
import com.example.brash.databinding.GtcItemBaralhoPublicoBinding

class ListaBaralhoPublicoVH(private val binding: GtcItemBaralhoPublicoBinding) : RecyclerView.ViewHolder(binding.root) {


    fun bindVH(baralho: Baralho, onItemClick: (Baralho) -> Unit){
        binding.ItemBaralhoPublicoTextViewNome.text = baralho.nome

        // Defina o clique do item
        binding.root.setOnClickListener {
            onItemClick(baralho)  // Chama o callback passando o item
        }
    }

}
