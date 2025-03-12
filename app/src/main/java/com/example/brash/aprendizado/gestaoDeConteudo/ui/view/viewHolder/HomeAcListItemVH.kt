package com.example.brash.aprendizado.gestaoDeConteudo.ui.view.viewHolder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.brash.R
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.HomeAcListItem

import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.adapter.ListaExpandableAdapter

/*
inner class HomeAcPastaItemVH(view: View) : RecyclerView.ViewHolder(view) {
    private val textView: TextView = view.findViewById(R.id.ItemPastaTextViewNome)

    fun bind(pastaItem: HomeAcListItem.PastaItem) {
        textView.text = pastaItem.name
        textView.setPadding(50, textView.paddingTop, textView.paddingRight, textView.paddingBottom) // Indenta


        textView.setOnClickListener {
            ExpandableListAdapter.onPastaItemClick(pastaItem) // 🔹 Chama o listener passando o item clicado
        }
    }
}
*/