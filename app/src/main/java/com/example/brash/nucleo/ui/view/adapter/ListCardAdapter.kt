package com.example.brash.nucleo.ui.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.brash.databinding.GtcItemListarCartaoBinding
import com.example.brash.nucleo.domain.model.Cartao
import com.example.brash.nucleo.ui.view.viewHolder.ListCardViewHolder

class ListCardAdapter : RecyclerView.Adapter<ListCardViewHolder>() {

    private var cardList: List<Cartao> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListCardViewHolder {
        val item = GtcItemListarCartaoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,false)
        return ListCardViewHolder(item)
    }

    override fun onBindViewHolder(holder: ListCardViewHolder, position: Int) {
        holder.bindVH(cardList[position])
    }

    override fun getItemCount(): Int {
        return cardList.size
    }


    fun updateCardList(list: List<Cartao>) {
        cardList = list
        notifyDataSetChanged()
    }
}
