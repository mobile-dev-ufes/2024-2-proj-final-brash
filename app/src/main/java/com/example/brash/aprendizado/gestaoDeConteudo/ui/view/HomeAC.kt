package com.example.brash.aprendizado.gestaoDeConteudo.ui.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.brash.R
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.HomeAcListItem
import com.example.brash.databinding.GtcHomeAcBinding
import com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel.HomeVM
import com.example.brash.nucleo.ui.view.ConfiguracaoAC
import com.example.brash.nucleo.ui.view.PerfilAC
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments.OpcoesDeBuscaFrDialog

import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments.AcoesAdicionaisFrDialog
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.adapter.ExpandableListAdapter
import com.example.brash.nucleo.ui.view.Fragments.AlertDialogFr

class HomeAC : AppCompatActivity(), View.OnClickListener, AlertDialogFr.OnConfirmListener {

    private lateinit var binding: GtcHomeAcBinding
    private lateinit var homeVM: HomeVM

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ExpandableListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = GtcHomeAcBinding.inflate(layoutInflater)
        setContentView(binding.root)
        homeVM = ViewModelProvider(this).get(HomeVM::class.java)

        initResultadoBusca()
        setOnClickListeners()
        setObservers()
    }
    private fun setOnClickListeners(){
        binding.HomeAcButtonAcoesAdicionais.setOnClickListener(this)
        binding.HomeAcImageViewOpcoesDeBusca.setOnClickListener(this)
        binding.HomeAcImageViewConfiguracoes.setOnClickListener(this)
        binding.HomeAcLayoutIconePerfil.setOnClickListener(this)
    }

    private fun setObservers(){
        //loginVM.erroMessageLD.observe(this, Observer{
            //binding.LoginAcTextViewErroEntrar.text = it
            //binding.LoginAcTextViewErroEntrar.visibility = View.VISIBLE
        //})
    }

    private fun initResultadoBusca(){
        recyclerView = findViewById(R.id.HomeAcExpandableListViewResultadoBusca)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val items = mutableListOf<HomeAcListItem>(
            HomeAcListItem.PastaItem(nome = "Roupas"),
            HomeAcListItem.PastaItem(nome = "Eletrônicos",
                baralhos = mutableListOf(
                    HomeAcListItem.BaralhoItem(nome = "Celular"),
                    HomeAcListItem.BaralhoItem(nome = "Notebook"),
                    HomeAcListItem.BaralhoItem(nome = "Fone de ouvido")
                )),
            HomeAcListItem.PastaItem(nome = "Alimentos")
        )

        adapter = ExpandableListAdapter(items,
            onPastaItemClick = { product ->
            Toast.makeText(this, "Clicou no pasta: ${product.nome}", Toast.LENGTH_SHORT).show()
        }, onBaralhoItemClick = { product ->
            Toast.makeText(this, "Clicou no baralho: ${product.nome}", Toast.LENGTH_SHORT).show()
        })

        recyclerView.adapter = adapter
    }
    private fun intentToCadastrarContaActivity(){
        //val intent = Intent(this, CadastrarContaAC::class.java)
        //startActivity(intent)
    }

    override fun onClick(view : View) {

        when(view.id){
            R.id.HomeAcButtonAcoesAdicionais -> {
                //Toast.makeText(applicationContext, "Você clicou em MoreActions", Toast.LENGTH_SHORT).show()
                //intentToCadastrarContaActivity()
                //AcoesAdicionaisFrDialog().show(supportFragmentManager, "AcoesAdicionaisDialog")
                AlertDialogFr("Isso eh um teste").show(supportFragmentManager, "ExclusaoAlertDialog")
            }
            R.id.HomeAcImageViewOpcoesDeBusca -> {
                //Toast.makeText(applicationContext, "Você clicou em MoreActions", Toast.LENGTH_SHORT).show()
                //intentToCadastrarContaActivity()
                OpcoesDeBuscaFrDialog().show(supportFragmentManager, "OpcaoDialog")
            }
            R.id.HomeAcImageViewConfiguracoes -> {
                Toast.makeText(applicationContext, "Você clicou em Configuracoes", Toast.LENGTH_SHORT).show()
                intentToConfiguracaoActivity()
            }
            R.id.HomeAcLayoutIconePerfil -> {
                //Toast.makeText(applicationContext, "Você clicou em MoreActions", Toast.LENGTH_SHORT).show()
                intentToPerfilActivity()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        //binding.LoginAcTextViewErroEntrar.visibility = View.GONE // esconder o erro depois que sair da tela
        // não vai previsar por causa do finish
    }

    private fun intentToConfiguracaoActivity(){
        val intent = Intent(this, ConfiguracaoAC::class.java)
        startActivity(intent)
        // vai ter que botar finish
    }
    private fun intentToPerfilActivity(){
        val intent = Intent(this, PerfilAC::class.java)
        startActivity(intent)
    }

    // Implementação da interface
    override fun onConfirmAlertDialog() {
        // Aqui você pode navegar para outra Activity ou realizar alguma ação
        Toast.makeText(this, "Confirmado Exclusao!", Toast.LENGTH_SHORT).show()
    }
}