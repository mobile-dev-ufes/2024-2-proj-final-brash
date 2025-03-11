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
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Baralho
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.HomeAcListItem
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Pasta
import com.example.brash.databinding.GtcHomeAcBinding
import com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel.HomeVM
import com.example.brash.nucleo.ui.view.ConfiguracaoAC
import com.example.brash.nucleo.ui.view.PerfilAC
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments.OpcoesDeBuscaFrDialog

import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments.AcoesAdicionaisFrDialog
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments.AcoesBaralhoFrDialog
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments.AcoesPastaFrDialog
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
        binding.HomeAcShapeableImageViewIconePerfil.setOnClickListener(this)
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
            HomeAcListItem.HomeAcPastaItem(pasta = Pasta(nome = "Roupas")),
            HomeAcListItem.HomeAcPastaItem(isExpanded = true, pasta = Pasta(nome =  "Eletrônicos",
                baralho =  mutableListOf(
                    Baralho(nome = "Celular"),
                    Baralho(nome = "Notebook"),
                    Baralho(nome = "Fone de ouvido")
                )
            )),
            HomeAcListItem.HomeAcPastaItem(pasta = Pasta(nome =  "Alimentos")),
            HomeAcListItem.HomeAcPastaItem(pasta = Pasta(nome =  "Frutas")),
            HomeAcListItem.HomeAcPastaItem(pasta = Pasta(nome =  "Verduras")),
            HomeAcListItem.HomeAcPastaItem(pasta = Pasta(nome =  "abacaxi")),
            HomeAcListItem.HomeAcPastaItem(pasta = Pasta(nome =  "4")),
            HomeAcListItem.HomeAcPastaItem(pasta = Pasta(nome =  "5")),
            HomeAcListItem.HomeAcPastaItem(pasta = Pasta(nome =  "6")),
            HomeAcListItem.HomeAcBaralhoItem(Baralho(nome = "Fone de ouvido"))
        )

        adapter = ExpandableListAdapter(items,
            onPastaItemLongClick = { item ->
            Toast.makeText(this, "Clicou no pasta: ${item.pasta.nome}", Toast.LENGTH_SHORT).show()
                AcoesPastaFrDialog(item.pasta).show(supportFragmentManager, "AcoesAdicionaisDialog")
        }, onBaralhoItemClick = { item ->
            Toast.makeText(this, "Clicou no baralho: ${item.baralho.nome}", Toast.LENGTH_SHORT).show()
                AcoesBaralhoFrDialog(item.baralho).show(supportFragmentManager, "AcoesAdicionaisDialog")
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
                AcoesAdicionaisFrDialog().show(supportFragmentManager, "AcoesAdicionaisDialog")
                //AlertDialogFr("Isso eh um teste").show(supportFragmentManager, "ExclusaoAlertDialog")
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
            R.id.HomeAcShapeableImageViewIconePerfil -> {
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