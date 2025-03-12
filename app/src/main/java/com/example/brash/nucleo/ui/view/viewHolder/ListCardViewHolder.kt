package com.example.brash.nucleo.ui.view.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.example.brash.nucleo.domain.model.Cartao
import com.example.brash.databinding.GtcItemListarCartaoBinding

class ListCardViewHolder(private val binding: GtcItemListarCartaoBinding) : RecyclerView.ViewHolder(binding.root) {


    fun bindVH(card: Cartao){
        binding.gtcPerguntaListarCard.text = card.pergunta
        binding.gtcRespostaListarCard.text = card.resposta
    }

}
