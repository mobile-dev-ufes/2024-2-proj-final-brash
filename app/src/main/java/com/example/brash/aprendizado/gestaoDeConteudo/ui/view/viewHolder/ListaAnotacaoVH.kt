package com.example.brash.aprendizado.gestaoDeConteudo.ui.view.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Anotacao
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Baralho
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Cartao
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.listener.OnAnotacaoListener
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.listener.OnBaralhoPublicoListener
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.listener.OnCartaoListener
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.listener.OnPastaListener
import com.example.brash.databinding.GtcItemAnotacaoBinding
import com.example.brash.databinding.GtcItemBaralhoPublicoBinding
import com.example.brash.databinding.GtcItemCartaoBinding

class ListaAnotacaoVH(private val binding: GtcItemAnotacaoBinding, private val listener: OnAnotacaoListener) : RecyclerView.ViewHolder(binding.root) {


    fun bindVH(anotacao: Anotacao){
        binding.ItemAnotacaoTextViewNome.text = anotacao.nome

        // Defina o clique do item
        binding.ItemAnotacaoLayout.setOnClickListener {
            listener.onClick(anotacao)
        }
    }

}
