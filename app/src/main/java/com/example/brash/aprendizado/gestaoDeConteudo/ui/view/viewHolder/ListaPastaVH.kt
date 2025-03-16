package com.example.brash.aprendizado.gestaoDeConteudo.ui.view.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Pasta
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.listener.OnPastaListener
import com.example.brash.aprendizado.gestaoDeConteudo.utils.getColorSetMoverBaralho
import com.example.brash.databinding.GtcItemPastaComIconeBinding

class ListaPastaVH (val binding: GtcItemPastaComIconeBinding, private val listener: OnPastaListener) : RecyclerView.ViewHolder(binding.root) {


    fun bindVH(pasta: Pasta){
        binding.ItemPastaComIconeTextViewNome.text = pasta.nome

        binding.ItemPastaComIconeLayout.setOnClickListener({
            listener.onClick(pasta)
        })
    }

}
