package com.example.brash.nucleo.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.brash.R
//import com.example.brash.databinding.ActivityLoginBinding
import com.example.brash.databinding.NucPerfilAcBinding
import com.example.brash.nucleo.ui.viewModel.PerfilVM
import com.example.brash.nucleo.utils.IconeImagem
import com.example.brash.utilsGeral.AppVM
import com.example.brash.utilsGeral.MyApplication

class PerfilAC : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: NucPerfilAcBinding
    private lateinit var perfilVM: PerfilVM

    private lateinit var appVM: AppVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        perfilVM = ViewModelProvider(this).get(PerfilVM::class.java)

        binding = NucPerfilAcBinding.inflate(layoutInflater)

        appVM = (application as MyApplication).appSharedInformation

        setContentView(binding.root)
        setOnClickListeners()
        setObservers()

        initAC()

    }
    fun initAC(){

        binding.PerfilAcTextViewNomeDeExibicao.post {
            binding.PerfilAcTextViewNomeDeExibicao.requestLayout()
        }
        binding.PerfilAcShapeableImageViewIconePerfil.setImageResource(IconeImagem.FELIZ.drawableRes)

        appVM.usuarioLogado.value?.let { usuario ->
            binding.PerfilAcShapeableImageViewIconePerfil.setImageResource(usuario.iconeDeUsuario.imagemPath.drawableRes)
            binding.PerfilAcShapeableImageViewIconePerfil.setBackgroundResource(usuario.iconeDeUsuario.cor.colorRes)
            binding.PerfilAcTextViewNomeDeExibicao.text = usuario.nomeDeExibicao
            binding.PerfilAcTextViewNomeDeUsuario.text =
                getString(R.string.username_display, usuario.nomeDeUsuario)
        } ?: run {
            Toast.makeText(this, "Erro ao carregar o ícone do usuário.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setOnClickListeners(){
        binding.PerfilAcImageViewRetornar.setOnClickListener(this)
        binding.PerfilAcButtonEditarPerfil.setOnClickListener(this)
    }

    private fun setObservers(){
        appVM.usuarioLogado.observe(this, Observer{
            binding.PerfilAcShapeableImageViewIconePerfil.setImageResource(it.iconeDeUsuario.imagemPath.drawableRes)
            binding.PerfilAcShapeableImageViewIconePerfil.setBackgroundResource(it.iconeDeUsuario.cor.colorRes)
        })
    }


    override fun onClick(view : View) {

        when(view.id){
            R.id.PerfilAcImageViewRetornar -> {
                finish()
            }
            R.id.PerfilAcButtonEditarPerfil -> {
                intentToEditarPerfilAC()
            }
        }

    }

    override fun onStop() {
        super.onStop()
        //binding.LoginAcTextViewErroEntrar.visibility = View.GONE // esconder o erro depois que sair da tela
        // não vai previsar por causa do finish
    }

    private fun intentToEditarPerfilAC(){
        val intent = Intent(this, EditarPerfilAC::class.java)
        startActivity(intent)
    }

}