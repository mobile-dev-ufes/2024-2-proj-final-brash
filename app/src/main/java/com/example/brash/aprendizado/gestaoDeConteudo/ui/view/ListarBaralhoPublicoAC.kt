package com.example.brash.aprendizado.gestaoDeConteudo.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.brash.R
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Baralho
import com.example.brash.nucleo.ui.view.PerfilAC
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments.OpcoesDeBuscaHomeFrDialog
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.Fragments.VisualizarBaralhoPublicoFrDialog

import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.adapter.ListaBaralhoPublicoAdapter
import com.example.brash.databinding.GtcListarBaralhoPublicoAcBinding
import com.example.brash.nucleo.ui.view.Fragments.AlertDialogFr

class ListarBaralhoPublicoAC : AppCompatActivity(), View.OnClickListener, AlertDialogFr.OnConfirmListener {

    private lateinit var binding: GtcListarBaralhoPublicoAcBinding
    //private lateinit var homeVM: HomeVM

    private lateinit var recyclerView: RecyclerView

    private lateinit var adapter : ListaBaralhoPublicoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = GtcListarBaralhoPublicoAcBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //homeVM = ViewModelProvider(this).get(HomeVM::class.java)
        recyclerView = binding.ListarBaralhoPublicoAcRecycleViewResultadoBusca
        recyclerView.layoutManager = LinearLayoutManager(this)



        initResultadoBusca()
        setOnClickListeners()
        setObservers()


        // Inicializando o adapter antes de usá-lo
        adapter = ListaBaralhoPublicoAdapter { baralho ->
            VisualizarBaralhoPublicoFrDialog(baralho).show(supportFragmentManager, "VisualizarBaralhoPublicoFrDialog")
        }
        recyclerView.adapter = adapter // Defina o adapter na RecyclerView


        // Simulação de dados para a lista
        val baralhoList = listOf(
            Baralho(idBaralho = 1, nome = "Baralho 1"),
            Baralho(idBaralho = 2, nome = "Deck 2"),
            Baralho(idBaralho = 3, nome = "Baralho 3"),
            Baralho(idBaralho = 1, nome = "Baralho 4"),
            Baralho(idBaralho = 2, nome = "Deck 5"),
            Baralho(idBaralho = 1, nome = "Baralho 6"),
            Baralho(idBaralho = 2, nome = "Deck 7"),
            Baralho(idBaralho = 1, nome = "Baralho 8"),
            Baralho(idBaralho = 2, nome = "Deck 9"),
            Baralho(idBaralho = 1, nome = "Baralho 10"),
            Baralho(idBaralho = 2, nome = "Deck 11"),
            Baralho(idBaralho = 1, nome = "Baralho 12"),
            Baralho(idBaralho = 2, nome = "Deck 13"),
            Baralho(idBaralho = 1, nome = "Baralho 14"),
            Baralho(idBaralho = 2, nome = "Deck 15"),
            Baralho(idBaralho = 1, nome = "Baralho 16"),
            Baralho(idBaralho = 2, nome = "Deck 17"),

        )
        adapter.updateBaralhoList(baralhoList)

    }
    private fun setOnClickListeners(){
        binding.ListarBaralhoPublicoAcImageViewRetornar.setOnClickListener(this)
        binding.ListarBaralhoPublicoAcImageViewOpcoesDeBusca.setOnClickListener(this)

    }

    private fun setObservers(){
        //loginVM.erroMessageLD.observe(this, Observer{
            //binding.LoginAcTextViewErroEntrar.text = it
            //binding.LoginAcTextViewErroEntrar.visibility = View.VISIBLE
        //})
    }

    private fun initResultadoBusca(){

    }
    private fun intentToCadastrarContaActivity(){
        //val intent = Intent(this, CadastrarContaAC::class.java)
        //startActivity(intent)
    }

    override fun onClick(view : View) {

        when(view.id){
            R.id.ListarBaralhoPublicoAcImageViewRetornar ->{
                finish()
            }
            R.id.ListarBaralhoPublicoAcImageViewOpcoesDeBusca -> {
                OpcoesDeBuscaHomeFrDialog().show(supportFragmentManager, "OpcaoDialog")
            }
        }
    }

    override fun onStop() {
        super.onStop()
        //binding.LoginAcTextViewErroEntrar.visibility = View.GONE // esconder o erro depois que sair da tela
        // não vai previsar por causa do finish
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