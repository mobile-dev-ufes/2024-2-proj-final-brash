package com.example.brash.aprendizado.gestaoDeConteudo.ui.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Dica
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.listener.OnDicaListener
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.viewHolder.ListaDicaVH
import com.example.brash.databinding.GtcItemDicaBinding

/**
 * Adapter for displaying a list of `Dica` items in a RecyclerView.
 * It binds each `Dica` object to its corresponding view and handles item clicks using the provided listener.
 *
 * @constructor Creates a new adapter for displaying `Dica` items.
 */
class ListaDicaAdapter() : RecyclerView.Adapter<ListaDicaVH>() {

    private var dicaList: List<Dica> = listOf()

    private lateinit var listener: OnDicaListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaDicaVH {
        val item = GtcItemDicaBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,false)
        return ListaDicaVH(item,listener)
    }

    override fun onBindViewHolder(holder: ListaDicaVH, position: Int) {
        holder.bindVH(dicaList[position])

        // Adiciona o listener para o clique do item
        holder.itemView.setOnClickListener {
            listener.onClick(dicaList[position])  // Chama o listener para a ação de clique
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return dicaList.count()
    }

    fun updateDicaList(list: List<Dica>) {
        dicaList = list
        notifyDataSetChanged()
    }

    fun setListener(dicaListener: OnDicaListener) {
        listener = dicaListener
    }
}

