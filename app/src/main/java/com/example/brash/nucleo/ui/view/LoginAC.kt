package com.example.brash.nucleo.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.brash.R
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.HomeAC
//import com.example.brash.databinding.ActivityLoginBinding
import com.example.brash.databinding.NucLoginAcBinding
import com.example.brash.nucleo.ui.viewModel.LoginVM

class LoginAC : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: NucLoginAcBinding
    private lateinit var loginVM: LoginVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = NucLoginAcBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loginVM = ViewModelProvider(this).get(LoginVM::class.java)

        setOnClickListeners()
        setObservers()
    }

    private fun setOnClickListeners(){
        binding.LoginAcButtonEntrar.setOnClickListener(this)
        binding.LoginAcButtonCriar.setOnClickListener(this)
    }

    private fun setObservers(){
        loginVM.erroMessageLD.observe(this, Observer{
            binding.LoginAcTextViewErroEntrar.text = it
            binding.LoginAcTextViewErroEntrar.visibility = View.VISIBLE
        })
    }

    override fun onClick(view : View) {

        when(view.id){
            R.id.LoginAcButtonEntrar -> {
                val email = binding.LoginAcUsuarioInput.text.toString()
                val password = binding.LoginAcSenhaInput.text.toString()

                loginVM.signIn(email, password, {
                    intentToHomeActivity()
                })

            }
            R.id.LoginAcButtonCriar ->{
                intentToCadastrarContaAC()
            }
        }

    }

    override fun onStop() {
        super.onStop()
        //binding.LoginAcTextViewErroEntrar.visibility = View.GONE // esconder o erro depois que sair da tela
        // não vai previsar por causa do finish
    }

    private fun intentToHomeActivity(){
        val intent = Intent(this, HomeAC::class.java)
        startActivity(intent)
        finish()
    }

    private fun intentToCadastrarContaAC(){
        val intent = Intent(this, CadastrarContaAC::class.java)
        startActivity(intent)
        //TODO:: aqui precisa de finish?
    }

}