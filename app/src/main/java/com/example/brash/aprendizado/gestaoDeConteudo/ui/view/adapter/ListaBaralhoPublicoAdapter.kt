package com.example.brash.aprendizado.gestaoDeConteudo.ui.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Baralho
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.listener.OnBaralhoPublicoListener
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.viewHolder.ListaBaralhoPublicoVH
import com.example.brash.databinding.GtcItemBaralhoPublicoBinding

/**
 * Adapter for displaying a list of public `Baralho` items in a RecyclerView.
 * It binds each `Baralho` object to its corresponding view and handles item clicks using the provided listener.
 *
 * @constructor Creates a new adapter for displaying public `Baralho` items.
 */
class ListaBaralhoPublicoAdapter() : RecyclerView.Adapter<ListaBaralhoPublicoVH>() {

    private var baralhoList: List<Baralho> = listOf()

    private lateinit var listener: OnBaralhoPublicoListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaBaralhoPublicoVH {
        val item = GtcItemBaralhoPublicoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,false)
        return ListaBaralhoPublicoVH(item,listener)
    }

    override fun onBindViewHolder(holder: ListaBaralhoPublicoVH, position: Int) {
        holder.bindVH(baralhoList[position])

        // Adiciona o listener para o clique do item
        holder.itemView.setOnClickListener {
            listener.onClick(baralhoList[position])  // Chama o listener para a ação de clique
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return baralhoList.count()
    }

    fun updateBaralhoPublicoList(list: List<Baralho>) {
        baralhoList = list
        notifyDataSetChanged()
    }

    fun setListener(baralhoPublicoListener: OnBaralhoPublicoListener) {
        listener = baralhoPublicoListener
    }
}

