package com.example.brash.aprendizado.gestaoDeConteudo.ui.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Pasta
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.listener.OnPastaListener
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.viewHolder.ListaPastaVH
import com.example.brash.databinding.GtcItemPastaComIconeBinding

class ListaPastaAdapter : RecyclerView.Adapter<ListaPastaVH>() {

    private var pastaList: List<Pasta> = listOf(
        Pasta(nome =  "Alimentos"),
        Pasta(nome =  "Frutas"),
        Pasta(nome =  "Verduras"),
        Pasta(nome =  "abacaxi"))
    private lateinit var listener: OnPastaListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaPastaVH {
        val item = GtcItemPastaComIconeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,false)
        return ListaPastaVH(item, listener)
    }

    override fun onBindViewHolder(holder: ListaPastaVH, position: Int) {
        holder.bindVH(pastaList[position])
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
}
