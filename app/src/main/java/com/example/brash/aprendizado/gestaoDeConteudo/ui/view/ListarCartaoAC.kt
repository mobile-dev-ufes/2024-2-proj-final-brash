package com.example.brash.aprendizado.gestaoDeConteudo.ui.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
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
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments.OpcoesDeBuscaListarCartaoFrDialog
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
import com.example.brash.utilsGeral.AppVM
import com.example.brash.utilsGeral.MyApplication

class ListarCartaoAC : AppCompatActivity() {

    private lateinit var binding: GtcListarCartaoAcBinding
    private lateinit var listarCartaoVM: ListarCartaoVM
    private lateinit var appVM: AppVM

    private lateinit var recyclerView: RecyclerView

    private lateinit var adapter : ListaCartaoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = GtcListarCartaoAcBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listarCartaoVM = ViewModelProvider(this)[ListarCartaoVM::class.java]
        appVM = (application as MyApplication).appSharedInformation


        // Inicializando o listener diretamente
        val listener = object : OnCartaoListener {
            override fun onClick(c: Cartao) {
                //Toast.makeText(applicationContext, c.pergunta, Toast.LENGTH_SHORT).show()
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


        appVM.baralhoEmAC.value?.let {
            binding.ListarCartaoAcTextViewTitulo.text = it.nome
            listarCartaoVM.setBaralhoOwner(it)
        } ?: run {
            //Toast.makeText(applicationContext, "Baralho não encontrado para nomear o título.", Toast.LENGTH_SHORT).show()
        }

        setOnClickListeners()
        setObservers()

        listarCartaoVM.getAllCartoes()
    }
    private fun setOnClickListeners(){

        binding.ListarCartaoAcInputDePesquisa.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                // O usuário pressionou "Done"
                listarCartaoVM.updateFilterCartaoList(binding.ListarCartaoAcInputDePesquisa.text.toString())

                true // Retorna true para indicar que o evento foi tratado
            } else {
                false // Permite que o evento continue propagando
            }
        }

        binding.ListarCartaoAcImageViewOpcoesDeBusca.setOnClickListener {
            OpcoesDeBuscaListarCartaoFrDialog().show(supportFragmentManager, "OpcaoDialog")
        }
        binding.ListarCartaoAcButtonCriar.setOnClickListener{
            CriarCartaoFrDialog().show(supportFragmentManager, "OpcaoDialog")
        }
        binding.ListarCartaoAcImageViewRetornar.setOnClickListener {
            finish()
        }

    }

    private fun setObservers(){
        //loginVM.erroMessageLD.observe(this, Observer{
            //binding.LoginAcTextViewErroEntrar.text = it
            //binding.LoginAcTextViewErroEntrar.visibility = View.VISIBLE
        //})
        listarCartaoVM.cartaoListSort.observe(this, Observer { cartaoListSort ->

            adapter.updateCartaoList(cartaoListSort)
        })

        listarCartaoVM.opcoesDeBusca.observe(this, Observer{
            //Toast.makeText(applicationContext, "Opcoes de busca foram alteradas", Toast.LENGTH_SHORT).show()
            listarCartaoVM.updateFilterCartaoList(binding.ListarCartaoAcInputDePesquisa.text.toString())
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