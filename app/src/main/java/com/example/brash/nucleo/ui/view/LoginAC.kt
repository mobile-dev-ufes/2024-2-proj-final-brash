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
import com.example.brash.nucleo.data.remoto.services.AccountService
import com.example.brash.nucleo.data.remoto.services.impl.AccountServiceImpl
import com.example.brash.nucleo.ui.viewModel.LoginVM
import com.example.brash.nucleo.ui.viewModel.viewModelFactory
import com.example.brash.nucleo.utils.UtilsFoos
import com.example.brash.utilsGeral.MyApplication

class LoginAC : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: NucLoginAcBinding
    private lateinit var loginVM: LoginVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loginVM = ViewModelProvider(this, viewModelFactory {
            LoginVM(application, MyApplication.appModule.accountService)
        }).get(LoginVM::class.java)

        binding = NucLoginAcBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setOnClickListeners()
        setObservers()
    }
    private fun setOnClickListeners(){
        binding.LoginAcTextViewEsqueceuSenha.setOnClickListener(this)
        binding.LoginAcButtonEntrar.setOnClickListener(this)
        binding.LoginAcButtonCriar.setOnClickListener(this)
        binding.LoginAcTextViewIdioma.setOnClickListener(this)
        binding.LoginAcRadioButtonIdiomaPt.setOnClickListener(this)
        binding.LoginAcRadioButtonIdiomaEn.setOnClickListener(this)
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

                loginVM.onSignInClick(email, password, {
                    intentToHomeActivity()
                })

            }
            R.id.LoginAcButtonCriar ->{
                intentToCadastrarContaAC()
            }
            R.id.LoginAcTextViewEsqueceuSenha -> {
                //UtilsFoos.showToast(this, "Você clicou na mensgem de ir cadastrar")
                //intentToCadastrarContaActivity()
            }
            R.id.LoginAcTextViewIdioma -> {

                attRadioGroup()

                if(binding.LoginAcRadioGroupIdioma.visibility == View.GONE){
                    binding.LoginAcRadioGroupIdioma.alpha = 0f
                    binding.LoginAcRadioGroupIdioma.visibility = View.VISIBLE
                    binding.LoginAcRadioGroupIdioma.animate().alpha(1f).setDuration(300).start()
                }else{
                    binding.LoginAcRadioGroupIdioma.animate().alpha(0f).setDuration(300).withEndAction {
                        binding.LoginAcRadioGroupIdioma.visibility = View.GONE
                    }.start()
                }
            }

            R.id.LoginAcRadioButtonIdiomaPt -> {
                UtilsFoos.changeLanguage(this, "pt")
                restartToLoginAc()
            }

            R.id.LoginAcRadioButtonIdiomaEn -> {
                UtilsFoos.changeLanguage(this, "en")
                restartToLoginAc()
            }
        }

    }

    private fun restartToLoginAc() {
        val intent = Intent(this, LoginAC::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        finish()
    }

    private fun attRadioGroup(){
        when (UtilsFoos.getLocaleLanguage(this)) {
            "pt" -> {
                binding.LoginAcRadioButtonIdiomaEn.isChecked = false
                binding.LoginAcRadioButtonIdiomaPt.isChecked = true
            }
            "en" -> {
                binding.LoginAcRadioButtonIdiomaPt.isChecked = false
                binding.LoginAcRadioButtonIdiomaEn.isChecked = true
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