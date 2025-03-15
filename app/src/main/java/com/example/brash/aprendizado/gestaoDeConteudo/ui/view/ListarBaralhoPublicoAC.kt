package com.example.brash.aprendizado.gestaoDeConteudo.ui.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.brash.R
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Baralho
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Pasta
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments.AcoesPastaFrDialog
import com.example.brash.nucleo.ui.view.PerfilAC
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments.VisualizarBaralhoPublicoFrDialog

import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.adapter.ListaBaralhoPublicoAdapter
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.listener.OnBaralhoPublicoListener
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.listener.OnPastaListener
import com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel.ListarBaralhoPublicoVM
import com.example.brash.databinding.GtcListarBaralhoPublicoAcBinding
import com.example.brash.nucleo.ui.view.Fragments.AlertDialogFr

class ListarBaralhoPublicoAC : AppCompatActivity() {

    private lateinit var binding: GtcListarBaralhoPublicoAcBinding
    private lateinit var listarBaralhoPublicoVM: ListarBaralhoPublicoVM

    private lateinit var recyclerView: RecyclerView

    private lateinit var adapter : ListaBaralhoPublicoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = GtcListarBaralhoPublicoAcBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listarBaralhoPublicoVM = ViewModelProvider(this)[ListarBaralhoPublicoVM::class.java]


        // Inicializando o listener diretamente
        val listener = object : OnBaralhoPublicoListener {
            override fun onClick(b: Baralho) {
                Toast.makeText(applicationContext, b.nome, Toast.LENGTH_SHORT).show()
                listarBaralhoPublicoVM.setBaralhoPublicoEmFoco(b)
                VisualizarBaralhoPublicoFrDialog().show(supportFragmentManager, "AcoesAdicionaisDialog")
            }
        }

        // Inicializando o adapter com o listener
        adapter = ListaBaralhoPublicoAdapter().apply {
            setListener(listener) // Garanta que o listener seja configurado
        }

        recyclerView = binding.ListarBaralhoPublicoAcRecycleViewResultadoBusca
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter // Defina o adapter na RecyclerView

        setOnClickListeners()
        setObservers()

        listarBaralhoPublicoVM.getAllBaralhosPublicos()


    }
    private fun setOnClickListeners(){
        binding.ListarBaralhoPublicoAcInputDePesquisa.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                // O usuário pressionou "Done"
                listarBaralhoPublicoVM.updateFilterBaralhoPublicoList(binding.ListarBaralhoPublicoAcInputDePesquisa.text.toString())

                true // Retorna true para indicar que o evento foi tratado
            } else {
                false // Permite que o evento continue propagando
            }
        }
        binding.ListarBaralhoPublicoAcImageViewRetornar.setOnClickListener {
            finish()
        }
    }

    private fun setObservers(){

        listarBaralhoPublicoVM.baralhoPublicoListSort.observe(this, Observer { baralhoListSort ->
            //if (baralhoListSort != null && baralhoListSort.isNotEmpty()) {
                adapter.updateBaralhoPublicoList(baralhoListSort)
                //Log.d("ListaBaralhoPublico", "Lista atualizada com sucesso.")
            //} else {
                //Log.d("ListaBaralhoPublico", "A lista de baralhos públicos está vazia.")
            //}
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