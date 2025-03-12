package com.example.brash.nucleo.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.brash.R
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.HomeAC
import com.example.brash.databinding.NucConfiguracaoAcBinding
import com.example.brash.databinding.NucDefinirEmailAcBinding
//import com.example.brash.databinding.ActivityLoginBinding
import com.example.brash.databinding.NucLoginAcBinding
import com.example.brash.databinding.NucRedefinirSenhaAcBinding
import com.example.brash.nucleo.ui.viewModel.LoginVM
import com.example.brash.nucleo.ui.view.LoginAC
import com.example.brash.nucleo.ui.viewModel.ConfiguracaoVM

class RedefinirSenhaAC : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: NucRedefinirSenhaAcBinding
    //private lateinit var loginVM: ConfiguracaoVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //loginVM = ViewModelProvider(this).get(ConfiguracaoVM::class.java)

        binding = NucRedefinirSenhaAcBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setOnClickListeners()
        setObservers()
        setOnBackPressedToLoginAc()
    }

    private fun setOnBackPressedToLoginAc(){
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        })
    }

    private fun setOnClickListeners(){

    }

    private fun setObservers(){

    }


    override fun onClick(view : View) {

        when(view.id){

        }

    }

}