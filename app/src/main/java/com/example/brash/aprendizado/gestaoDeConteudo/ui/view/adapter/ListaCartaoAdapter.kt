package com.example.brash.aprendizado.gestaoDeConteudo.ui.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Baralho
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Cartao
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.listener.OnBaralhoPublicoListener
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.listener.OnCartaoListener
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.listener.OnPastaListener
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.viewHolder.ListaBaralhoPublicoVH
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.viewHolder.ListaCartaoVH
import com.example.brash.databinding.GtcItemBaralhoPublicoBinding
import com.example.brash.databinding.GtcItemCartaoBinding

class ListaCartaoAdapter() : RecyclerView.Adapter<ListaCartaoVH>() {

    private var cartaoList: List<Cartao> = listOf()

    private lateinit var listener: OnCartaoListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaCartaoVH {
        val item = GtcItemCartaoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,false)
        return ListaCartaoVH(item,listener)
    }

    override fun onBindViewHolder(holder: ListaCartaoVH, position: Int) {
        holder.bindVH(cartaoList[position])

        // Adiciona o listener para o clique do item
        holder.itemView.setOnClickListener {
            listener.onClick(cartaoList[position])  // Chama o listener para a ação de clique
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return cartaoList.count()
    }

    fun updateCartaoList(list: List<Cartao>) {
        cartaoList = list
        notifyDataSetChanged()
    }

    fun setListener(cartaoListener: OnCartaoListener) {
        listener = cartaoListener
    }
}

