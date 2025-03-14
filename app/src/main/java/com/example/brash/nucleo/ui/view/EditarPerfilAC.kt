package com.example.brash.nucleo.ui.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.brash.databinding.NucEditarPerfilAcBinding
//import com.example.brash.databinding.ActivityLoginBinding
import com.example.brash.nucleo.domain.model.IconeDeUsuario
import com.example.brash.nucleo.domain.model.Usuario
import com.example.brash.nucleo.ui.viewModel.PerfilVM
import com.example.brash.nucleo.utils.IconeCor
import com.example.brash.nucleo.utils.IconeImagem
import com.example.brash.utilsGeral.AppVM
import com.example.brash.utilsGeral.MyApplication

class EditarPerfilAC : AppCompatActivity() {

    private lateinit var binding: NucEditarPerfilAcBinding
    private lateinit var perfilVM: PerfilVM

    private lateinit var appVM: AppVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        perfilVM = ViewModelProvider(this).get(PerfilVM::class.java)
        appVM = (application as MyApplication).appSharedInformation
        binding = NucEditarPerfilAcBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setOnClickListeners()
        setObservers()

        initView()
    }

    fun initView(){
        appVM.usuarioLogado.value?.let { usuario ->
            perfilVM.setImagemEmFoco(usuario.iconeDeUsuario.imagemPath)
            perfilVM.setCorEmFoco(usuario.iconeDeUsuario.cor)
            binding.EditarPerfilAcShapeableImageViewIconePerfil.setImageResource(usuario.iconeDeUsuario.imagemPath.drawableRes)
            binding.EditarPerfilAcShapeableImageViewIconePerfil.setBackgroundResource(usuario.iconeDeUsuario.cor.colorRes)
            binding.EditarPerfilInputNomeDeExibicao.setText(usuario.nomeDeExibicao)
            binding.EditarPerfilInputNomeDeUsuario.setText(usuario.nomeDeUsuario)
        } ?: run {
            Toast.makeText(this, "Erro ao carregar o ícone do usuário.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setOnClickListeners(){
        binding.EditarPerfilAcImageViewRetornar.setOnClickListener {
            finish()
        }
        binding.EditarPerfilButtonConfirmar.setOnClickListener{
            // TODO:: Obter todas as informações

            appVM.updateUsuarioLogado(
                Usuario(nomeDeUsuario = binding.EditarPerfilInputNomeDeUsuario.text.toString(),
                        nomeDeExibicao = binding.EditarPerfilInputNomeDeExibicao.text.toString(),
                        iconeDeUsuario = IconeDeUsuario(imagemPath = perfilVM.imagemEmFoco.value!!, cor = perfilVM.corEmFoco.value!!)
                )
            )
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
        appVM.usuarioLogado.observe(this, Observer{
            binding.EditarPerfilAcShapeableImageViewIconePerfil.setImageResource(it.iconeDeUsuario.imagemPath.drawableRes)
            binding.EditarPerfilAcShapeableImageViewIconePerfil.setBackgroundResource(it.iconeDeUsuario.cor.colorRes)
        })
    }


    override fun onStop() {
        super.onStop()
        //binding.LoginAcTextViewErroEntrar.visibility = View.GONE // esconder o erro depois que sair da tela
        // não vai previsar por causa do finish
    }

}