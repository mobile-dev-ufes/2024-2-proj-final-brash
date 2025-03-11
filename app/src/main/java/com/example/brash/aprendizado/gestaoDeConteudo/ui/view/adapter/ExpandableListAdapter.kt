package com.example.brash.aprendizado.gestaoDeConteudo.ui.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.brash.R
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.HomeAcListItem

class ExpandableListAdapter(
    private val items: MutableList<HomeAcListItem>,
    private val onPastaItemLongClick: (HomeAcListItem.HomeAcPastaItem) -> Unit,
    private val onBaralhoItemClick: (HomeAcListItem.HomeAcBaralhoItem) -> Unit// 🔹 Adicionando listener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_CATEGORY = 0
        private const val TYPE_PRODUCT = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is HomeAcListItem.HomeAcPastaItem -> TYPE_CATEGORY
            is HomeAcListItem.HomeAcBaralhoItem -> TYPE_PRODUCT
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_CATEGORY -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.gtc_item_pasta, parent, false)
                HomeAcPastaItemVH(view)
            }
            TYPE_PRODUCT -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.gtc_item_baralho, parent, false)
                HomeAcBaralhoItemVH(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is HomeAcListItem.HomeAcPastaItem -> (holder as HomeAcPastaItemVH).bind(item, position)
            is HomeAcListItem.HomeAcBaralhoItem -> (holder as HomeAcBaralhoItemVH).bind(item)
        }
    }

    override fun getItemCount(): Int = items.size

    inner class HomeAcPastaItemVH(view: View) : RecyclerView.ViewHolder(view) {
        private val tvCategoryName: TextView = view.findViewById(R.id.ItemPastaTextViewNome)
        private val ivExpandIcon: ImageView = view.findViewById(R.id.ItemPastaExpandIcon)

        fun bind(category: HomeAcListItem.HomeAcPastaItem, position: Int) {
            tvCategoryName.text = category.pasta.nome
            ivExpandIcon.rotation = if (category.isExpanded) 180f else 0f

            if(category.isExpanded){
                items.addAll(position + 1, category.pasta.baralho.map { it ->
                    HomeAcListItem.HomeAcBaralhoItem(it)
                })
            }
            itemView.setOnClickListener {
                category.isExpanded = !category.isExpanded
                ivExpandIcon.rotation = if (category.isExpanded) 180f else 0f
                toggleProducts(category, position)
            }

            // 🔥 Clique longo
            itemView.setOnLongClickListener {
                onPastaItemLongClick(category) // 🔹 Chama o callback
                true // 🔹 Retorna `true` indicando que tratamos o evento
            }
        }

        private fun toggleProducts(category: HomeAcListItem.HomeAcPastaItem, position: Int) {
            if (category.isExpanded) {
                /*items.addAll(position + 1, category.pasta.baralho.map { baralho ->
                    HomeAcListItem.HomeAcBaralhoItem(baralho)
                })*/
                items.addAll(position + 1, category.pasta.baralho.map { it ->
                    HomeAcListItem.HomeAcBaralhoItem(it)
                })
            } else {
                items.subList(position + 1, position + 1 + category.pasta.baralho.size).clear()
            }
            notifyDataSetChanged()
        }
    }

    inner class HomeAcBaralhoItemVH(view: View) : RecyclerView.ViewHolder(view) {
        private val textViewB: TextView = view.findViewById(R.id.ItemBaralhoTextViewNome)

        fun bind(baralhoItem: HomeAcListItem.HomeAcBaralhoItem) {
            textViewB.text = baralhoItem.baralho.nome
            textViewB.setPadding(50, textViewB.paddingTop, textViewB.paddingRight, textViewB.paddingBottom) // Indenta


            itemView.setOnClickListener {
                onBaralhoItemClick(baralhoItem) // 🔹 Chama o listener passando o item clicado
            }
        }
    }

}

