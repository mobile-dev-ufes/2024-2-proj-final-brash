package com.example.brash.nucleo.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.brash.R
import com.example.brash.databinding.ActivityLoginBinding
import com.example.brash.nucleo.ui.viewModel.LoginViewModel
import com.example.brash.nucleo.utils.UtilsFoos

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginVM: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loginVM = ViewModelProvider(this).get(LoginViewModel::class.java)

        setOnClickListeners()
        setObservers()
    }

    private fun setOnClickListeners(){
        binding.LoginAcTextViewEsqueceuSenha.setOnClickListener(this)
        binding.LoginAcButtonEntrar.setOnClickListener(this)
    }

    private fun setObservers(){
        loginVM.erroMessageLD.observe(this, Observer{
            binding.LoginAcTextViewErroEntrar.text = it
            binding.LoginAcTextViewErroEntrar.visibility = View.VISIBLE
        })
    }

    private fun intentToCadastrarContaActivity(){
        val intent = Intent(this, CadastrarContaActivity::class.java)
        startActivity(intent)
        //aqui precisa de finish?
    }

    override fun onClick(view : View) {

        when(view.id){
            R.id.LoginAcTextViewEsqueceuSenha -> {
                //UtilsFoos.showToast(this, "Você clicou na mensgem de ir cadastrar")
                //intentToCadastrarContaActivity()
            }
            R.id.LoginAcButtonEntrar -> {
                val email = binding.LoginAcUsuarioInput.text.toString()
                val password = binding.LoginAcSenhaInput.text.toString()

                loginVM.signIn(email, password, {
                    intentToHomeActivity()
                })

            }
        }

    }

    override fun onStop() {
        super.onStop()
        //binding.LoginAcTextViewErroEntrar.visibility = View.GONE // esconder o erro depois que sair da tela
        // não vai previsar por causa do finish
    }

    private fun intentToHomeActivity(){
        val intent = Intent(this, CadastrarContaActivity::class.java)
        startActivity(intent)
        // vai ter que botar finish
    }

}