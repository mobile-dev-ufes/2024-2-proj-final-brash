package com.example.brash.nucleo.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.brash.R
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.HomeAC
import com.example.brash.databinding.NucEditarPerfilAcBinding
//import com.example.brash.databinding.ActivityLoginBinding
import com.example.brash.databinding.NucPerfilAcBinding
import com.example.brash.nucleo.ui.viewModel.PerfilVM

class EditarPerfilAC : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: NucEditarPerfilAcBinding
    //private lateinit var perfilVM: PerfilVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //perfilVM = ViewModelProvider(this).get(PerfilVM::class.java)
        binding = NucEditarPerfilAcBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setOnClickListeners()
        setObservers()
    }

    private fun setOnClickListeners(){
        binding.EditarPerfilAcImageViewRetornar.setOnClickListener(this)
    }

    private fun setObservers(){

    }


    override fun onClick(view : View) {
        when(view.id){
            R.id.PerfilAcImageViewRetornar -> {
                finish()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        //binding.LoginAcTextViewErroEntrar.visibility = View.GONE // esconder o erro depois que sair da tela
        // não vai previsar por causa do finish
    }

}