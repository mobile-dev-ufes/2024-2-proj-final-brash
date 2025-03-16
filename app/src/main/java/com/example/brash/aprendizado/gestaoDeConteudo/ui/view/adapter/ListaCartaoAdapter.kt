package com.example.brash.aprendizado.gestaoDeConteudo.ui.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Cartao
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.listener.OnCartaoListener
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.viewHolder.ListaCartaoVH
import com.example.brash.databinding.GtcItemCartaoBinding

/**
 * Adapter for displaying a list of `Cartao` items in a RecyclerView.
 * It binds each `Cartao` object to its corresponding view and handles item clicks using the provided listener.
 *
 * @constructor Creates a new adapter for displaying `Cartao` items.
 */
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

