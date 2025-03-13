package com.example.brash.aprendizado.gestaoDeConteudo.ui.view.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Baralho
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Cartao
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Dica
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.listener.OnBaralhoPublicoListener
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.listener.OnCartaoListener
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.listener.OnDicaListener
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.listener.OnPastaListener
import com.example.brash.databinding.GtcItemBaralhoPublicoBinding
import com.example.brash.databinding.GtcItemCartaoBinding
import com.example.brash.databinding.GtcItemDicaBinding

class ListaDicaVH(private val binding: GtcItemDicaBinding, private val listener: OnDicaListener) : RecyclerView.ViewHolder(binding.root) {


    fun bindVH(dica: Dica){
        binding.ItemDicaTextViewTexto.text = dica.texto

        // Defina o clique do item
        binding.ItemDicaLayout.setOnClickListener {
            listener.onClick(dica)
        }
    }

}
