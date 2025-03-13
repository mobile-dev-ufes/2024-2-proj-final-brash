package com.example.brash.aprendizado.gestaoDeConteudo.ui.view.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Baralho
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Cartao
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.listener.OnBaralhoPublicoListener
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.listener.OnCartaoListener
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.listener.OnPastaListener
import com.example.brash.databinding.GtcItemBaralhoPublicoBinding
import com.example.brash.databinding.GtcItemCartaoBinding

class ListaCartaoVH(private val binding: GtcItemCartaoBinding, private val listener: OnCartaoListener) : RecyclerView.ViewHolder(binding.root) {


    fun bindVH(cartao: Cartao){
        binding.ItemCartaoTextViewPergunta.text = cartao.pergunta
        binding.ItemCartaoTextViewResposta.text = cartao.resposta

        // Defina o clique do item
        binding.ItemCartaoLayout.setOnClickListener {
            listener.onClick(cartao)
        }
    }

}
