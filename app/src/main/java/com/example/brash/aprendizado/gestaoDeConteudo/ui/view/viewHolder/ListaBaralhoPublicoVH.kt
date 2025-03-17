package com.example.brash.aprendizado.gestaoDeConteudo.ui.view.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Baralho
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.BaralhoPublico
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.listener.OnBaralhoPublicoListener
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.listener.OnPastaListener
import com.example.brash.databinding.GtcItemBaralhoPublicoBinding

class ListaBaralhoPublicoVH(private val binding: GtcItemBaralhoPublicoBinding, private val listener: OnBaralhoPublicoListener) : RecyclerView.ViewHolder(binding.root) {


    fun bindVH(baralho: BaralhoPublico){
        binding.ItemBaralhoPublicoTextViewNome.text = baralho.nomeBaralho

        // Defina o clique do item
        binding.ItemBaralhoPublicoLayout.setOnClickListener {
            listener.onClick(baralho)
        }
    }

}
