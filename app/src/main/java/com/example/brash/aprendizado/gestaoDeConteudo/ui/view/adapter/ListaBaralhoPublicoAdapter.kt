package com.example.brash.aprendizado.gestaoDeConteudo.ui.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Baralho
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.viewHolder.ListaBaralhoPublicoVH
import com.example.brash.databinding.GtcItemBaralhoPublicoBinding

class ListaBaralhoPublicoAdapter(private val onItemClick: (Baralho) -> Unit) : RecyclerView.Adapter<ListaBaralhoPublicoVH>() {

    private var baralhoList: List<Baralho> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaBaralhoPublicoVH {
        val item = GtcItemBaralhoPublicoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,false)
        return ListaBaralhoPublicoVH(item)
    }

    override fun onBindViewHolder(holder: ListaBaralhoPublicoVH, position: Int) {
        val baralho = baralhoList[position]
        holder.bindVH(baralho, onItemClick) // Passando o onItemClick
    }

    override fun getItemCount(): Int {
        return baralhoList.count()
    }

    fun updateBaralhoList(list: List<Baralho>) {
        baralhoList = list
        notifyDataSetChanged()
    }
}

