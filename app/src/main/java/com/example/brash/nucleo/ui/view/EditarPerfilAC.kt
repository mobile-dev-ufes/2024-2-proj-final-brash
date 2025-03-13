package com.example.brash.nucleo.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.brash.R
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.HomeAC
import com.example.brash.databinding.NucEditarPerfilAcBinding
//import com.example.brash.databinding.ActivityLoginBinding
import com.example.brash.databinding.NucPerfilAcBinding
import com.example.brash.nucleo.ui.viewModel.PerfilVM
import com.example.brash.nucleo.utils.IconeCor
import com.example.brash.nucleo.utils.IconeImagem

class EditarPerfilAC : AppCompatActivity() {

    private lateinit var binding: NucEditarPerfilAcBinding
    private lateinit var perfilVM: PerfilVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        perfilVM = ViewModelProvider(this).get(PerfilVM::class.java)
        binding = NucEditarPerfilAcBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setOnClickListeners()
        setObservers()
    }

    private fun setOnClickListeners(){
        binding.EditarPerfilAcImageViewRetornar.setOnClickListener {
            finish()
        }
        binding.EditarPerfilAcImageViewIcone1.setOnClickListener{
            perfilVM.setImagemEmFoco(IconeImagem.PADRAO)
            Toast.makeText(applicationContext, "Clicaram no Icone", Toast.LENGTH_SHORT).show()
        }
        binding.EditarPerfilAcImageViewIcone2.setOnClickListener{
            perfilVM.setImagemEmFoco(IconeImagem.LUZ)
            Toast.makeText(applicationContext, "Clicaram no Icone", Toast.LENGTH_SHORT).show()
        }
        binding.EditarPerfilAcImageViewIcone3.setOnClickListener{
            perfilVM.setImagemEmFoco(IconeImagem.CARRO)
            Toast.makeText(applicationContext, "Clicaram no Icone", Toast.LENGTH_SHORT).show()
        }
        binding.EditarPerfilAcImageViewIcone4.setOnClickListener{
            perfilVM.setImagemEmFoco(IconeImagem.FELIZ)
            Toast.makeText(applicationContext, "Clicaram no Icone", Toast.LENGTH_SHORT).show()
        }
        binding.EditarPerfilAcImageViewCor1.setOnClickListener{
            perfilVM.setCorEmFoco(IconeCor.DEEP_PURPLE)
            Toast.makeText(applicationContext, "Clicaram no Icone", Toast.LENGTH_SHORT).show()
        }
        binding.EditarPerfilAcImageViewCor2.setOnClickListener{
            perfilVM.setCorEmFoco(IconeCor.SOFT_PINK)
            Toast.makeText(applicationContext, "Clicaram no Icone", Toast.LENGTH_SHORT).show()
        }
        binding.EditarPerfilAcImageViewCor3.setOnClickListener{
            perfilVM.setCorEmFoco(IconeCor.WHITE)
            Toast.makeText(applicationContext, "Clicaram no Icone", Toast.LENGTH_SHORT).show()
        }
        binding.EditarPerfilAcImageViewCor4.setOnClickListener{
            perfilVM.setCorEmFoco(IconeCor.HOT_PINK)
            Toast.makeText(applicationContext, "Clicaram no Icone", Toast.LENGTH_SHORT).show()
        }
        binding.EditarPerfilAcImageViewCor5.setOnClickListener{
            perfilVM.setCorEmFoco(IconeCor.LEAF_GREEN)
            Toast.makeText(applicationContext, "Clicaram no Icone", Toast.LENGTH_SHORT).show()
        }
    }
    private fun setObservers(){
        perfilVM.corEmFoco.observe(this, Observer { cor ->
            cor?.let { binding.EditarPerfilAcShapeableImageViewIconePerfil.setBackgroundResource(it.colorRes) }
        })
        perfilVM.imagemEmFoco.observe(this, Observer { imagem ->
            imagem?.let { binding.EditarPerfilAcShapeableImageViewIconePerfil.setImageResource(it.drawableRes) }
            imagem?.let { binding.EditarPerfilAcImageViewCor1.setImageResource(it.drawableRes) }
            imagem?.let { binding.EditarPerfilAcImageViewCor2.setImageResource(it.drawableRes) }
            imagem?.let { binding.EditarPerfilAcImageViewCor3.setImageResource(it.drawableRes) }
            imagem?.let { binding.EditarPerfilAcImageViewCor4.setImageResource(it.drawableRes) }
            imagem?.let { binding.EditarPerfilAcImageViewCor5.setImageResource(it.drawableRes) }
        })
    }


    override fun onStop() {
        super.onStop()
        //binding.LoginAcTextViewErroEntrar.visibility = View.GONE // esconder o erro depois que sair da tela
        // não vai previsar por causa do finish
    }

}