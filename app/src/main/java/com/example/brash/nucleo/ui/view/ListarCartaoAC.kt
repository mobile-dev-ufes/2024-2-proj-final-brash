package com.example.brash.nucleo.ui.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.brash.nucleo.domain.model.Cartao
import com.example.brash.databinding.GtcListarCartaoAcBinding
import com.example.brash.nucleo.ui.view.adapter.ListCardAdapter

class ListarCartaoAC : AppCompatActivity(), View.OnClickListener{

    private lateinit var binding : GtcListarCartaoAcBinding
    private lateinit var listCardAdapter: ListCardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = GtcListarCartaoAcBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ListarCartaoAcExpandableListViewResultadoBusca.layoutManager = LinearLayoutManager(this)
        binding.ListarCartaoAcExpandableListViewResultadoBusca.adapter = ListCardAdapter()

        // Sample data (replace with real data)

        val cardList = listOf(
            Cartao(pergunta = "What is Kotlin?", resposta = "A modern programming language."),
            Cartao(pergunta = "What is Android?", resposta = "A mobile operating system."),
            Cartao(pergunta = "What is RecyclerView?", resposta = "A flexible view for large data sets.")

        )

        // Update the adapter with the data
        listCardAdapter.updateCardList(cardList)

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.ListarBaralhoPublicoAcImageViewRetornar.setOnClickListener(this)
        binding.ListarCartaoAcFiltro.setOnClickListener(this)
        binding.ListarCartaoAddBtn.setOnClickListener(this)
        binding.ListarCartaoAcLayoutInputDePesquisa.setOnClickListener(this)
    }

    override fun onClick(view : View) {
        when (view.id) {
            binding.ListarCartaoAddBtn.id -> {
                Toast.makeText(this, "Cria Cartao", Toast.LENGTH_LONG).show()
            }
        }
    }
}