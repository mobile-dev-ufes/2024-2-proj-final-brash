package com.example.brash.aprendizado.gestaoDeConteudo.ui.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.brash.R
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.HomeAcListItem

/**
 * Adapter for displaying a list of expandable items, where each item can be either a category (`Pasta`)
 * or a product (`Baralho`). This adapter allows for expansion of categories to reveal associated items.
 *
 * @param items A list of items to be displayed. It can include both `HomeAcPastaItem` (categories) and
 *              `HomeAcBaralhoItem` (products).
 * @param onPastaItemLongClick A callback triggered when a category (`Pasta`) item is long-clicked.
 * @param onBaralhoItemClick A callback triggered when a product (`Baralho`) item is clicked.
 */
class ListaExpandableAdapter(
    private var items: List<HomeAcListItem>,
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
        private val containerBaralhos: LinearLayout = view.findViewById(R.id.ItemPastaContainerBaralhos)

        fun bind(category: HomeAcListItem.HomeAcPastaItem, position: Int) {
            tvCategoryName.text = category.pasta.nome
            ivExpandIcon.rotation = if (category.isExpanded) 180f else 0f

            //Limpa antes de adicionar novos itens, evitando duplicação
            containerBaralhos.removeAllViews()

            if (category.isExpanded) {
                containerBaralhos.visibility = View.VISIBLE
                category.pasta.baralhos.forEach { baralho ->
                    val baralhoView = LayoutInflater.from(itemView.context)
                        .inflate(R.layout.gtc_item_baralho, containerBaralhos, false)

                    val textViewB: TextView = baralhoView.findViewById(R.id.ItemBaralhoTextViewNome)
                    textViewB.text = baralho.nome
                    //Configurar o clique
                    baralhoView.setOnClickListener {
                        onBaralhoItemClick(HomeAcListItem.HomeAcBaralhoItem(baralho))
                    }

                    containerBaralhos.addView(baralhoView)
                }
            } else {
                containerBaralhos.visibility = View.GONE
            }

            itemView.setOnClickListener {
                category.isExpanded = !category.isExpanded

                if(containerBaralhos.visibility == View.VISIBLE){
                    containerBaralhos.visibility = View.GONE
                }
                else{
                    containerBaralhos.visibility = View.VISIBLE
                }
                ivExpandIcon.rotation = if (category.isExpanded) 180f else 0f
                //toggleProducts(category, position)
            }
            // 🔥 Clique longo
            itemView.setOnLongClickListener {
                onPastaItemLongClick(category) // Chama o callback
                true // Retorna `true` indicando que tratamos o evento
            }
        }
    }

    inner class HomeAcBaralhoItemVH(view: View) : RecyclerView.ViewHolder(view) {
        private val textViewB: TextView = view.findViewById(R.id.ItemBaralhoTextViewNome)

        fun bind(baralhoItem: HomeAcListItem.HomeAcBaralhoItem) {
            textViewB.text = baralhoItem.baralho.nome

            itemView.setOnClickListener {
                onBaralhoItemClick(baralhoItem) // 🔹 Chama o listener passando o item clicado
            }
        }
    }

    fun updateProdList(list: List<HomeAcListItem>) {
        items = list
        Log.d("ListaPastaAdapter", "Lista atualizada: ${list.size} itens")
        notifyDataSetChanged()
    }

}

