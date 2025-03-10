package com.example.brash.nucleo.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.brash.R
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.HomeAC
import com.example.brash.databinding.NucConfiguracaoAcBinding
import com.example.brash.databinding.NucDefinirEmailAcBinding
//import com.example.brash.databinding.ActivityLoginBinding
import com.example.brash.databinding.NucLoginAcBinding
import com.example.brash.nucleo.ui.viewModel.LoginVM
import com.example.brash.nucleo.ui.view.LoginAC
import com.example.brash.nucleo.ui.viewModel.ConfiguracaoVM

class DefinirEmailAC : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: NucDefinirEmailAcBinding
    //private lateinit var loginVM: ConfiguracaoVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //loginVM = ViewModelProvider(this).get(ConfiguracaoVM::class.java)

        binding = NucDefinirEmailAcBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setOnClickListeners()
        setObservers()
    }
    private fun setOnClickListeners(){
        //binding.ConfiguracaoAcTextViewTrocarSenha.setOnClickListener(this)
        //binding.ConfiguracaoAcTextViewTrocarEmail.setOnClickListener(this)
        //binding.ConfiguracaoAcTextViewSairDaConta.setOnClickListener(this)
    }

    private fun setObservers(){
    }


    override fun onClick(view : View) {

        when(view.id){
            /*
            R.id.ConfiguracaoAcTextViewTrocarSenha -> {
                //UtilsFoos.showToast(this, "Você clicou na mensgem de ir cadastrar")
                //intentToCadastrarContaActivity()
            }
            R.id.ConfiguracaoAcTextViewTrocarEmail -> {

            }
            R.id.ConfiguracaoAcTextViewSairDaConta ->{
                //loginVM.signOut()
                //intentToLoginActivity()
            }*/
        }

    }

    override fun onStop() {
        super.onStop()
        //binding.LoginAcTextViewErroEntrar.visibility = View.GONE // esconder o erro depois que sair da tela
        // não vai previsar por causa do finish
    }

}