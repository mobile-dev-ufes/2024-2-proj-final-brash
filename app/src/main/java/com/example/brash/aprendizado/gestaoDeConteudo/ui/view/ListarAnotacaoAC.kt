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
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Anotacao
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Baralho
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Cartao
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Pasta
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments.AcoesAnotacaoFrDialog
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments.AcoesCartaoFrDialog
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments.AcoesPastaFrDialog
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments.CriarAnotacaoFrDialog
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments.CriarCartaoFrDialog
import com.example.brash.nucleo.ui.view.PerfilAC
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments.OpcoesDeBuscaHomeFrDialog
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments.VisualizarBaralhoPublicoFrDialog
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments.VisualizarCartaoFrDialog
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.adapter.ListaAnotacaoAdapter

import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.adapter.ListaBaralhoPublicoAdapter
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.adapter.ListaCartaoAdapter
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.listener.OnAnotacaoListener
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.listener.OnBaralhoPublicoListener
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.listener.OnCartaoListener
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.listener.OnPastaListener
import com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel.ListarAnotacaoVM
import com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel.ListarBaralhoPublicoVM
import com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel.ListarCartaoVM
import com.example.brash.databinding.GtcListarAnotacaoAcBinding
import com.example.brash.databinding.GtcListarBaralhoPublicoAcBinding
import com.example.brash.databinding.GtcListarCartaoAcBinding
import com.example.brash.nucleo.ui.view.Fragments.AlertDialogFr

class ListarAnotacaoAC : AppCompatActivity() {

    private lateinit var binding: GtcListarAnotacaoAcBinding
    private lateinit var listarAnotacaoVM: ListarAnotacaoVM

    private lateinit var recyclerView: RecyclerView

    private lateinit var adapter : ListaAnotacaoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = GtcListarAnotacaoAcBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listarAnotacaoVM = ViewModelProvider(this).get(ListarAnotacaoVM::class.java)



        // Inicializando o listener diretamente
        val listener = object : OnAnotacaoListener {
            override fun onClick(anotacao: Anotacao) {
                Toast.makeText(applicationContext, anotacao.nome, Toast.LENGTH_SHORT).show()
                listarAnotacaoVM.setAnotacaoEmFoco(anotacao)
                AcoesAnotacaoFrDialog().show(supportFragmentManager, "VisualizarCartaoDialog")
            }
        }

        // Inicializando o adapter com o listener
        adapter = ListaAnotacaoAdapter().apply {
            setListener(listener) // Garanta que o listener seja configurado
        }
        recyclerView = binding.ListarAnotacaoAcRecycleViewResultadoBusca
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter // Defina o adapter na RecyclerView

        setOnClickListeners()
        setObservers()

        listarAnotacaoVM.getAllCartoes()


    }
    private fun setOnClickListeners(){

        binding.ListarAnotacaoAcImageViewRetornar.setOnClickListener {
            finish()
        }
        binding.ListarAnotacaoAcImageViewOpcoesDeBusca.setOnClickListener {
            OpcoesDeBuscaHomeFrDialog().show(supportFragmentManager, "OpcaoDialog")
        }
        binding.ListarAnotacaoAcButtonCriar.setOnClickListener{
            CriarAnotacaoFrDialog().show(supportFragmentManager, "OpcaoDialog")
        }

    }

    private fun setObservers(){
        //loginVM.erroMessageLD.observe(this, Observer{
            //binding.LoginAcTextViewErroEntrar.text = it
            //binding.LoginAcTextViewErroEntrar.visibility = View.VISIBLE
        //})
        listarAnotacaoVM.anotacaoList.observe(this, Observer { anotacaoList ->
            if (anotacaoList != null && anotacaoList.isNotEmpty()) {
                adapter.updateAnotacaoList(anotacaoList)
                Log.d("ListaBaralhoPublico", "Lista atualizada com sucesso.")
            } else {
                Log.d("ListaBaralhoPublico", "A lista de baralhos públicos está vazia.")
            }
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