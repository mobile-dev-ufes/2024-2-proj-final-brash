package com.example.brash.aprendizado.gestaoDeConteudo.ui.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
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
import androidx.lifecycle.Observer
import com.example.brash.nucleo.ui.view.ConfiguracaoAC
import com.example.brash.nucleo.ui.view.PerfilAC
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments.OpcoesDeBuscaHomeFrDialog

import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments.AcoesAdicionaisFrDialog
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments.AcoesBaralhoFrDialog
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments.AcoesPastaFrDialog
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.adapter.ListaExpandableAdapter
import com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel.RevisaoVM
import com.example.brash.databinding.GtcRevisaoAcBinding
import com.example.brash.nucleo.ui.view.Fragments.AlertDialogFr

class RevisaoAC : AppCompatActivity(), View.OnClickListener{

    private lateinit var binding: GtcRevisaoAcBinding
    private lateinit var revisaoVM: RevisaoVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = GtcRevisaoAcBinding.inflate(layoutInflater)
        setContentView(binding.root)
        revisaoVM = ViewModelProvider(this).get(RevisaoVM::class.java)

        // Obtem baralho
        //revisaoVM.setBaralhoEmFoco(ViewModelProvider(this).get(HomeVM::class.java).baralhoEmFoco.value!!)
        //TODO:: Passar corretamente o baralho para revisaoVM
        // RevisaoAC

        ViewModelProvider(this).get(HomeVM::class.java).baralhoEmFoco.value?.let { baralho ->
            revisaoVM.setBaralhoEmFoco(baralho)
            binding.RevisaoAcTextViewNomeBaralho.text = baralho.nome
        } ?: run {
            Toast.makeText(this, "Erro: Nenhum baralho selecionado!", Toast.LENGTH_LONG).show()
        }
        setOnClickListeners()
        setObservers()
        //binding.RevisaoAcExemplo.text = revisaoVM.baralhoEmFoco.value!!.nome
    }

    private fun setOnClickListeners(){

        binding.RevisaoAcImageViewRetornar.setOnClickListener {
            finish()
        }

        binding.RevisaoAcButtonIniciarRevisao.setOnClickListener {
            val intent = Intent(this, RevisaoCartaoAC::class.java)
            startActivity(intent)
        }

    }

    private fun setObservers(){
    }

    override fun onClick(view : View) {
    }

    override fun onStop() {
        super.onStop()
        //binding.LoginAcTextViewErroEntrar.visibility = View.GONE // esconder o erro depois que sair da tela
        // não vai previsar por causa do finish
    }

}