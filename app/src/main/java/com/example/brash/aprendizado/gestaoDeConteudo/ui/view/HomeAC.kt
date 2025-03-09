package com.example.brash.aprendizado.gestaoDeConteudo.ui.view

import com.example.brash.nucleo.ui.view.CadastrarContaAC

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.brash.R
import com.example.brash.databinding.GtcHomeAcBinding
import com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel.HomeVM

class HomeAC : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: GtcHomeAcBinding
    private lateinit var homeVM: HomeVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = GtcHomeAcBinding.inflate(layoutInflater)
        setContentView(binding.root)
        homeVM = ViewModelProvider(this).get(HomeVM::class.java)

        setOnClickListeners()
        setObservers()
    }
    private fun setOnClickListeners(){
        binding.HomeAcButtonMoreActions.setOnClickListener(this)
        //binding.LoginAcButtonEntrar.setOnClickListener(this)
    }

    private fun setObservers(){
        //loginVM.erroMessageLD.observe(this, Observer{
            //binding.LoginAcTextViewErroEntrar.text = it
            //binding.LoginAcTextViewErroEntrar.visibility = View.VISIBLE
        //})
    }

    private fun intentToCadastrarContaActivity(){
        //val intent = Intent(this, CadastrarContaAC::class.java)
        //startActivity(intent)
        //TODO:: aqui precisa de finish?
    }

    override fun onClick(view : View) {

        when(view.id){
            R.id.HomeAcButtonMoreActions -> {
                Toast.makeText(applicationContext, "Você clicou em MoreActions", Toast.LENGTH_SHORT).show()
                //intentToCadastrarContaActivity()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        //binding.LoginAcTextViewErroEntrar.visibility = View.GONE // esconder o erro depois que sair da tela
        // não vai previsar por causa do finish
    }

    private fun intentToHomeActivity(){
        //val intent = Intent(this, CadastrarContaAC::class.java)
        //startActivity(intent)
        // vai ter que botar finish
    }

}