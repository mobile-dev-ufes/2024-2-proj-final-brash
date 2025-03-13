package com.example.brash.aprendizado.gestaoDeConteudo.ui.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Baralho
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Cartao
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Dica
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.listener.OnBaralhoPublicoListener
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.listener.OnCartaoListener
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.listener.OnDicaListener
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.listener.OnPastaListener
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.viewHolder.ListaBaralhoPublicoVH
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.viewHolder.ListaCartaoVH
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.viewHolder.ListaDicaVH
import com.example.brash.databinding.GtcItemBaralhoPublicoBinding
import com.example.brash.databinding.GtcItemCartaoBinding
import com.example.brash.databinding.GtcItemDicaBinding

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

