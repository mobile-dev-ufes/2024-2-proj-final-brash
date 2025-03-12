package com.example.brash.aprendizado.gestaoDeConteudo.ui.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Pasta
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.listener.OnPastaListener
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.viewHolder.ListaPastaVH
import com.example.brash.aprendizado.gestaoDeConteudo.utils.getColorResetMoverBaralho
import com.example.brash.aprendizado.gestaoDeConteudo.utils.getColorSetMoverBaralho
import com.example.brash.databinding.GtcItemPastaComIconeBinding

class ListaPastaAdapter : RecyclerView.Adapter<ListaPastaVH>() {

    private var pastaList: List<Pasta> = listOf()

    private lateinit var listener: OnPastaListener

    // Variável para controlar a posição do item selecionado
    var selectedPosition: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaPastaVH {
        val item = GtcItemPastaComIconeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,false)
        return ListaPastaVH(item, listener)
    }

    override fun onBindViewHolder(holder: ListaPastaVH, position: Int) {
        holder.bindVH(pastaList[position])

        // Mudando o fundo do item selecionado
        if (position == selectedPosition) {
            holder.itemView.setBackgroundColor(
                ContextCompat.getColor(holder.itemView.context, getColorSetMoverBaralho()) // Ou outra cor de sua escolha
            )
        } else {
            holder.itemView.setBackgroundColor(
                ContextCompat.getColor(holder.itemView.context, getColorResetMoverBaralho()) // Cor padrão
            )
        }

        // Adiciona o listener para o clique do item
        holder.itemView.setOnClickListener {
            selectedPosition = position  // Atualiza a posição do item selecionado
            notifyDataSetChanged()  // Notifica que houve mudança
            listener.onClick(pastaList[position])  // Chama o listener para a ação de clique
        }
    }

    override fun getItemCount(): Int {
        return pastaList.count()
    }

    fun updateProdList(list: List<Pasta>) {
        pastaList = list
        Log.d("ListaPastaAdapter", "Lista atualizada: ${list.size} itens")
        notifyDataSetChanged()
    }

    fun setListener(pastaListener: OnPastaListener) {
        listener = pastaListener
    }

    fun resetSelectedItem() {
        // Reseta a variável de posição selecionada
        selectedPosition = -1
        // Notifica a RecyclerView para atualizar todos os itens
        notifyDataSetChanged()
    }
}
