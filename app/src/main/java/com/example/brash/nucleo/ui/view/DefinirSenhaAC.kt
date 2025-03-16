package com.example.brash.nucleo.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
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
import com.example.brash.nucleo.ui.viewModel.DefinirSenhaVM
import com.example.brash.nucleo.ui.viewModel.viewModelFactory
import com.example.brash.utilsGeral.MyApplication

class DefinirSenhaAC : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: NucRedefinirSenhaAcBinding
    private lateinit var definirSenhaVM: DefinirSenhaVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        definirSenhaVM = ViewModelProvider(this, viewModelFactory {
            DefinirSenhaVM(application, MyApplication.appModule.accountService)
        }).get(DefinirSenhaVM::class.java)

        binding = NucRedefinirSenhaAcBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Define o email fixo e bloqueia a edição
        val userEmail = definirSenhaVM.currentUserEmail().toString()

        binding.CadastrarContaAcTextInputLayoutNome.suffixText = null
        binding.CadastrarContaAcTextInputLayoutNome.endIconMode = com.google.android.material.textfield.TextInputLayout.END_ICON_NONE

        binding.RedefinirSenhaAcTextInputEditTextEmail.setTextColor(resources.getColor(R.color.charcoal_gray))
        binding.RedefinirSenhaAcTextInputEditTextEmail.setText(userEmail)
        binding.RedefinirSenhaAcTextInputEditTextEmail.isEnabled = false
        binding.RedefinirSenhaAcTextInputEditTextEmail.isFocusableInTouchMode = false
        binding.RedefinirSenhaAcTextInputEditTextEmail.isFocusable = false

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
        binding.RedefinirSenhaAcButtonCancelar.setOnClickListener(this)
        binding.RedefinirSenhaAcButtonEnviar.setOnClickListener(this)
    }

    private fun setObservers(){
        definirSenhaVM.erroMessageLD.observe(this, Observer{
            binding.LoginAcTextViewErroEntrar.text = it
            binding.LoginAcTextViewErroEntrar.visibility = View.VISIBLE
        })
    }


    override fun onClick(view : View) {
        when(view.id){
            R.id.RedefinirSenhaAcButtonEnviar -> {
                val email = binding.RedefinirSenhaAcTextInputEditTextEmail.text.toString()

                definirSenhaVM.updateUsersPassword(email, {
                    Toast.makeText(applicationContext, R.string.nuc_email_alterar_senha, Toast.LENGTH_LONG).show()
                    finish()
                })
            }
            R.id.RedefinirSenhaAcButtonCancelar -> {
                finish()
            }
        }

    }
}