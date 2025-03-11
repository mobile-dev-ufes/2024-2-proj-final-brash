package com.example.brash.nucleo.ui.view

//import com.example.brash.databinding.ActivityLoginBinding
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.brash.R
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.HomeAC
import com.example.brash.databinding.NucConfiguracaoAcBinding
import com.example.brash.nucleo.ui.viewModel.ConfiguracaoVM
import com.example.brash.nucleo.utils.UtilsFoos
import java.util.Locale


class ConfiguracaoAC : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: NucConfiguracaoAcBinding
    private lateinit var configuracaoVM: ConfiguracaoVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configuracaoVM = ViewModelProvider(this).get(ConfiguracaoVM::class.java)

        binding = NucConfiguracaoAcBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setOnClickListeners()
        setObservers()


    }
    private fun setOnClickListeners(){
        binding.ConfiguracaoAcTextViewTrocarSenha.setOnClickListener(this)
        binding.ConfiguracaoAcTextViewTrocarEmail.setOnClickListener(this)
        binding.ConfiguracaoAcTextViewSairDaConta.setOnClickListener(this)
        binding.ConfiguracaoAcImageViewRetornar.setOnClickListener(this)
        binding.ConfiguracaoAcTextViewIdioma.setOnClickListener(this)
        binding.ConfiguracaoAcRadioButtonIdiomaPt.setOnClickListener(this)
        binding.ConfiguracaoAcRadioButtonIdiomaEn.setOnClickListener(this)

    }


    private fun setObservers(){

    }


    override fun onClick(view : View) {

        when(view.id){
            R.id.ConfiguracaoAcTextViewTrocarSenha -> {
                //UtilsFoos.showToast(this, "Você clicou na mensgem de ir cadastrar")
                intentToDefinirSenhaActivity()
            }
            R.id.ConfiguracaoAcTextViewTrocarEmail -> {
                intentToDefinirEmailActivity()
            }
            R.id.ConfiguracaoAcTextViewSairDaConta ->{
                configuracaoVM.signOut()
                intentToLoginActivity()
            }
            R.id.ConfiguracaoAcImageViewRetornar ->{
                finish()
            }

            R.id.ConfiguracaoAcTextViewIdioma -> {

                attRadioGroup()

                if(binding.ConfiguracaoAcRadioGroupIdioma.visibility == View.GONE){
                    binding.ConfiguracaoAcRadioGroupIdioma.alpha = 0f
                    binding.ConfiguracaoAcRadioGroupIdioma.visibility = View.VISIBLE
                    binding.ConfiguracaoAcRadioGroupIdioma.animate().alpha(1f).setDuration(300).start()
                }else{
                    binding.ConfiguracaoAcRadioGroupIdioma.animate().alpha(0f).setDuration(300).withEndAction {
                        binding.ConfiguracaoAcRadioGroupIdioma.visibility = View.GONE
                    }.start()
                }
            }

            R.id.ConfiguracaoAcRadioButtonIdiomaPt -> {
                UtilsFoos.changeLanguage(this, "pt")
                restartToHome()
            }

            R.id.ConfiguracaoAcRadioButtonIdiomaEn -> {
                UtilsFoos.changeLanguage(this, "en")
                restartToHome()
            }

        }

    }

    private fun attRadioGroup(){
        when (UtilsFoos.getLocaleLanguage(this)) {
            "pt" -> {
                binding.ConfiguracaoAcRadioButtonIdiomaEn.isChecked = false
                binding.ConfiguracaoAcRadioButtonIdiomaPt.isChecked = true
            }
            "en" -> {
                binding.ConfiguracaoAcRadioButtonIdiomaPt.isChecked = false
                binding.ConfiguracaoAcRadioButtonIdiomaEn.isChecked = true
            }
        }
    }

    override fun onStop() {
        super.onStop()
        //binding.LoginAcTextViewErroEntrar.visibility = View.GONE // esconder o erro depois que sair da tela
        // não vai previsar por causa do finish
    }

    private fun intentToLoginActivity(){
        val intent = Intent(this, LoginAC::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        finish()
    }
    private fun intentToDefinirSenhaActivity(){
        val intent = Intent(this, DefinirSenhaAC::class.java)
        startActivity(intent)
    }
    private fun intentToDefinirEmailActivity(){
        val intent = Intent(this, DefinirEmailAC::class.java)
        startActivity(intent)
    }

    private fun restartToHome() {
        val intent = Intent(this, HomeAC::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        finish()
    }


}