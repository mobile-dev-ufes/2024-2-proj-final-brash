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
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Pasta
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments.AcoesCartaoFrDialog
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments.AcoesPastaFrDialog
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments.CriarCartaoFrDialog
import com.example.brash.nucleo.ui.view.PerfilAC
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments.OpcoesDeBuscaHomeFrDialog
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments.VisualizarBaralhoPublicoFrDialog
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments.VisualizarCartaoFrDialog

import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.adapter.ListaBaralhoPublicoAdapter
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.adapter.ListaCartaoAdapter
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.listener.OnBaralhoPublicoListener
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.listener.OnCartaoListener
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.listener.OnPastaListener
import com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel.ListarBaralhoPublicoVM
import com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel.ListarCartaoVM
import com.example.brash.databinding.GtcListarBaralhoPublicoAcBinding
import com.example.brash.databinding.GtcListarCartaoAcBinding
import com.example.brash.nucleo.ui.view.Fragments.AlertDialogFr

class ListarCartaoAC : AppCompatActivity() {

    private lateinit var binding: GtcListarCartaoAcBinding
    private lateinit var listarCartaoVM: ListarCartaoVM

    private lateinit var recyclerView: RecyclerView

    private lateinit var adapter : ListaCartaoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = GtcListarCartaoAcBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listarCartaoVM = ViewModelProvider(this).get(ListarCartaoVM::class.java)



        // Inicializando o listener diretamente
        val listener = object : OnCartaoListener {
            override fun onClick(c: Cartao) {
                Toast.makeText(applicationContext, c.pergunta, Toast.LENGTH_SHORT).show()
                listarCartaoVM.setCartaoEmFoco(c)
                AcoesCartaoFrDialog().show(supportFragmentManager, "VisualizarCartaoDialog")
            }
        }

        // Inicializando o adapter com o listener
        adapter = ListaCartaoAdapter().apply {
            setListener(listener) // Garanta que o listener seja configurado
        }

        recyclerView = binding.ListarCartaoAcRecycleViewResultadoBusca
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter // Defina o adapter na RecyclerView

        setOnClickListeners()
        setObservers()

        listarCartaoVM.getAllCartoes()


    }
    private fun setOnClickListeners(){

        binding.ListarCartaoAcImageViewRetornar.setOnClickListener {
            finish()
        }
        binding.ListarCartaoAcImageViewOpcoesDeBusca.setOnClickListener {
            OpcoesDeBuscaHomeFrDialog().show(supportFragmentManager, "OpcaoDialog")
        }
        binding.ListarCartaoAcButtonCriar.setOnClickListener{
            CriarCartaoFrDialog().show(supportFragmentManager, "OpcaoDialog")
        }

    }

    private fun setObservers(){
        //loginVM.erroMessageLD.observe(this, Observer{
            //binding.LoginAcTextViewErroEntrar.text = it
            //binding.LoginAcTextViewErroEntrar.visibility = View.VISIBLE
        //})
        listarCartaoVM.cartaoList.observe(this, Observer { cartaoList ->
            if (cartaoList != null && cartaoList.isNotEmpty()) {
                adapter.updateCartaoList(cartaoList)
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