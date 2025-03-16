package com.example.brash.aprendizado.gestaoDeConteudo.ui.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.brash.R
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Baralho
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Cartao
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Dica
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Pasta
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments.AcoesCartaoFrDialog
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments.AcoesDicaFrDialog
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments.AcoesPastaFrDialog
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments.CriarCartaoFrDialog
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments.CriarDicaFrDialog
import com.example.brash.nucleo.ui.view.PerfilAC
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments.VisualizarBaralhoPublicoFrDialog
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments.VisualizarCartaoFrDialog

import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.adapter.ListaBaralhoPublicoAdapter
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.adapter.ListaCartaoAdapter
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.adapter.ListaDicaAdapter
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.listener.OnBaralhoPublicoListener
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.listener.OnCartaoListener
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.listener.OnDicaListener
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.listener.OnPastaListener
import com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel.ListarBaralhoPublicoVM
import com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel.ListarCartaoVM
import com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel.ListarDicaVM
import com.example.brash.databinding.GtcListarBaralhoPublicoAcBinding
import com.example.brash.databinding.GtcListarCartaoAcBinding
import com.example.brash.databinding.GtcListarDicaAcBinding
import com.example.brash.nucleo.ui.view.Fragments.AlertDialogFr
import com.example.brash.utilsGeral.AppVM
import com.example.brash.utilsGeral.MyApplication

class ListarDicaAC : AppCompatActivity() {

    private lateinit var binding: GtcListarDicaAcBinding
    private lateinit var listarDicaVM: ListarDicaVM
    private lateinit var appVM: AppVM

    private lateinit var recyclerView: RecyclerView

    private lateinit var adapter : ListaDicaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = GtcListarDicaAcBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listarDicaVM = ViewModelProvider(this)[ListarDicaVM::class.java]
        appVM = (application as MyApplication).appSharedInformation


        // Inicializando o listener diretamente
        val listener = object : OnDicaListener {
            override fun onClick(d: Dica) {
                Toast.makeText(applicationContext, d.texto, Toast.LENGTH_SHORT).show()
                listarDicaVM.setDicaEmFoco(d)
                AcoesDicaFrDialog().show(supportFragmentManager, "VisualizarCartaoDialog")
            }
        }

        // Inicializando o adapter com o listener
        adapter = ListaDicaAdapter().apply {
            setListener(listener) // Garanta que o listener seja configurado
        }

        recyclerView = binding.ListarCartaoAcRecycleViewResultadoBusca
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter // Defina o adapter na RecyclerView

        setOnClickListeners()
        setObservers()

        appVM.cartaoEmAC.value?.let {
            listarDicaVM.setCartaoOwner(it)
        } ?: run {
            Toast.makeText(applicationContext, "Cartao não encontrado para obter dicas.", Toast.LENGTH_SHORT).show()
        }
        listarDicaVM.getAllDicas()


    }
    private fun setOnClickListeners(){

        binding.ListarDicaAcImageViewRetornar.setOnClickListener {
            finish()
        }
        binding.ListarDicaAcButtonCriar.setOnClickListener{
            CriarDicaFrDialog().show(supportFragmentManager, "OpcaoDialog")
        }

    }

    private fun setObservers(){
        //loginVM.erroMessageLD.observe(this, Observer{
            //binding.LoginAcTextViewErroEntrar.text = it
            //binding.LoginAcTextViewErroEntrar.visibility = View.VISIBLE
        //})
        listarDicaVM.dicaList.observe(this, Observer { dicaList ->
            adapter.updateDicaList(dicaList)

        })
    }
    private fun intentToCadastrarContaActivity(){
        //val intent = Intent(this, CadastrarContaAC::class.java)
        //startActivity(intent)
    }

    override fun onStop() {
        super.onStop()
        //binding.LoginAcTextViewErroEntrar.visibility = View.GONE // esconder o erro depois que sair da tela
        // não vai previsar por causa do finish
    }

    //private fun intentToPerfilActivity(){
        //val intent = Intent(this, PerfilAC::class.java)
        //startActivity(intent)
    //}

}