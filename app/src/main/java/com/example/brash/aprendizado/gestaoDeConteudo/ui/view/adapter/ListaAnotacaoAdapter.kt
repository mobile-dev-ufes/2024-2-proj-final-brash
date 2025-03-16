package com.example.brash.aprendizado.gestaoDeConteudo.ui.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Anotacao
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.listener.OnAnotacaoListener
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.viewHolder.ListaAnotacaoVH
import com.example.brash.databinding.GtcItemAnotacaoBinding

/**
 * Adapter for displaying a list of `Anotacao` items in a RecyclerView.
 * It binds each `Anotacao` object to its corresponding view and handles item clicks using the provided listener.
 *
 * @constructor Creates a new adapter for displaying `Anotacao` items.
 */
class ListaAnotacaoAdapter() : RecyclerView.Adapter<ListaAnotacaoVH>() {

    private var anotacaoList: List<Anotacao> = listOf()

    private lateinit var listener: OnAnotacaoListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaAnotacaoVH {
        val item = GtcItemAnotacaoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,false)
        return ListaAnotacaoVH(item,listener)
    }

    override fun onBindViewHolder(holder: ListaAnotacaoVH, position: Int) {
        holder.bindVH(anotacaoList[position])

        // Adiciona o listener para o clique do item
        holder.itemView.setOnClickListener {
            listener.onClick(anotacaoList[position])  // Chama o listener para a ação de clique
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return anotacaoList.count()
    }

    fun updateAnotacaoList(list: List<Anotacao>) {
        anotacaoList = list
        notifyDataSetChanged()
    }

    fun setListener(anotacaoListener: OnAnotacaoListener) {
        listener = anotacaoListener
    }
}

