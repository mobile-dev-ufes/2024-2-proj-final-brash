package com.example.brash.aprendizado.gestaoDeConteudo.ui.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.brash.R
import com.example.brash.databinding.GtcHomeAcBinding
import com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel.HomeVM
import com.example.brash.nucleo.ui.view.ConfiguracaoAC
import com.example.brash.nucleo.ui.view.PerfilAC
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments.OpcoesDeBuscaHomeFrDialog

import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments.AcoesAdicionaisFrDialog
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments.AcoesBaralhoFrDialog
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments.AcoesPastaFrDialog
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.adapter.ListaExpandableAdapter
import com.example.brash.nucleo.ui.view.Fragments.AlertDialogFr
import com.example.brash.utilsGeral.AppVM
import com.example.brash.utilsGeral.MyApplication

class HomeAC : AppCompatActivity(), AlertDialogFr.OnConfirmListener {

    private lateinit var binding: GtcHomeAcBinding
    private lateinit var homeVM: HomeVM

    private lateinit var appVM: AppVM

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ListaExpandableAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = GtcHomeAcBinding.inflate(layoutInflater)
        setContentView(binding.root)
        homeVM = ViewModelProvider(this).get(HomeVM::class.java)

        appVM = (application as MyApplication).appSharedInformation

        initResultadoBusca()
        setOnClickListeners()
        setObservers()

        appVM.requestUsuarioLogado()
    }


    private fun setOnClickListeners(){
        binding.HomeAcButtonAcoesAdicionais.setOnClickListener{
            AcoesAdicionaisFrDialog().show(supportFragmentManager, "AcoesAdicionaisDialog")
        }
        binding.HomeAcImageViewOpcoesDeBusca.setOnClickListener{
            OpcoesDeBuscaHomeFrDialog().show(supportFragmentManager, "OpcaoDialog")
        }
        binding.HomeAcImageViewConfiguracoes.setOnClickListener{
            Toast.makeText(applicationContext, "Você clicou em Configuracoes", Toast.LENGTH_SHORT).show()
            intentToConfiguracaoActivity()
        }
        binding.HomeAcShapeableImageViewIconePerfil.setOnClickListener{
            intentToPerfilActivity()
        }
    }

    private fun setObservers(){
        homeVM.homeAcListItemList.observe(this, Observer{
            adapter.updateProdList(homeVM.homeAcListItemList.value!!)
        })

        appVM.usuarioLogado.observe(this, Observer{
            binding.HomeAcShapeableImageViewIconePerfil.setImageResource(it.iconeDeUsuario.imagemPath.drawableRes)
            binding.HomeAcShapeableImageViewIconePerfil.setBackgroundResource(it.iconeDeUsuario.cor.colorRes)
            binding.HomeAcTextViewPerfil.setTextColor(it.iconeDeUsuario.cor.colorRes)
        })

        /*
        perfilVM.imagemEmFoco.observe(this, Observer { imagem ->
            imagem?.let { binding.EditarPerfilAcShapeableImageViewIconePerfil.setImageResource(it.drawableRes) }
            imagem?.let { binding.EditarPerfilAcImageViewCor1.setImageResource(it.drawableRes) }
            imagem?.let { binding.EditarPerfilAcImageViewCor2.setImageResource(it.drawableRes) }
            imagem?.let { binding.EditarPerfilAcImageViewCor3.setImageResource(it.drawableRes) }
            imagem?.let { binding.EditarPerfilAcImageViewCor4.setImageResource(it.drawableRes) }
            imagem?.let { binding.EditarPerfilAcImageViewCor5.setImageResource(it.drawableRes) }
        })
         */

    }

    private fun initResultadoBusca(){
        recyclerView = findViewById(R.id.HomeAcExpandableListViewResultadoBusca)
        recyclerView.layoutManager = LinearLayoutManager(this)


        homeVM.getAllHomeAcListItem()

        adapter = ListaExpandableAdapter(homeVM.homeAcListItemList.value!!,
            onPastaItemLongClick = { item ->
            //Toast.makeText(this, "Clicou no pasta: ${item.pasta.nome}", Toast.LENGTH_SHORT).show()
                homeVM.setPastaEmFoco(item.pasta)
                AcoesPastaFrDialog().show(supportFragmentManager, "AcoesAdicionaisDialog")
        }, onBaralhoItemClick = { item ->
            //Toast.makeText(this, "Clicou no baralho: ${item.baralho.nome}", Toast.LENGTH_SHORT).show()
                homeVM.setBaralhoEmFoco(item.baralho)
                AcoesBaralhoFrDialog().show(supportFragmentManager, "AcoesAdicionaisDialog")
        })


        recyclerView.adapter = adapter
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