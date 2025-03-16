package com.example.brash.nucleo.ui.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.brash.databinding.NucEditarPerfilAcBinding
import com.example.brash.nucleo.ui.viewModel.PerfilVM
import com.example.brash.nucleo.utils.IconeCor
import com.example.brash.nucleo.utils.IconeImagem
import com.example.brash.utilsGeral.AppVM
import com.example.brash.utilsGeral.MyApplication

/**
 * Activity for editing the user's profile.
 *
 * This activity allows the user to modify their display name, username,
 * and profile icon (both image and color).
 */
class EditarPerfilAC : AppCompatActivity() {

    private lateinit var binding: NucEditarPerfilAcBinding
    private lateinit var perfilVM: PerfilVM

    private lateinit var appVM: AppVM

    /**
     * Called when the activity is created.
     *
     * Initializes the ViewModel, layout binding, and sets up the observers for LiveData.
     * Also populates the profile information if the user is logged in.
     *
     * @param savedInstanceState The saved instance state from a previous activity state.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        perfilVM = ViewModelProvider(this)[PerfilVM::class.java]
        appVM = (application as MyApplication).appSharedInformation
        binding = NucEditarPerfilAcBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setOnClickListeners()
        setObservers()

        initView()
    }

    /**
     * Initializes the view with the current user's profile data.
     *
     * Populates the user's display name, username, and profile icon if available.
     */
    private fun initView(){
        appVM.usuarioLogado.value?.let { usuario ->
            perfilVM.setImagemEmFoco(usuario.iconeDeUsuario.imagem)
            perfilVM.setCorEmFoco(usuario.iconeDeUsuario.cor)
            binding.EditarPerfilAcShapeableImageViewIconePerfil.setImageResource(usuario.iconeDeUsuario.imagem.drawableRes)
            binding.EditarPerfilAcShapeableImageViewIconePerfil.setBackgroundResource(usuario.iconeDeUsuario.cor.colorRes)
            binding.EditarPerfilInputNomeDeExibicao.setText(usuario.nomeDeExibicao)
            binding.EditarPerfilInputNomeDeUsuario.setText(usuario.nomeDeUsuario)
        } ?: run {
            // Handle case where the user icon is not available
        }
    }

    /**
     * Sets the click listeners for various UI elements.
     */
    private fun setOnClickListeners(){
        binding.EditarPerfilAcImageViewRetornar.setOnClickListener {
            finish()
        }
        binding.EditarPerfilButtonConfirmar.setOnClickListener{

            appVM.updateUsuarioLogado( binding.EditarPerfilInputNomeDeUsuario.text.toString(),
                binding.EditarPerfilInputNomeDeExibicao.text.toString(),
                perfilVM.corEmFoco.value!!,
                perfilVM.imagemEmFoco.value!! ){
                finish()
            }


        }
        binding.EditarPerfilAcImageViewIcone1.setOnClickListener{
            perfilVM.setImagemEmFoco(IconeImagem.PADRAO)
            //Toast.makeText(applicationContext, "Clicaram no Icone", Toast.LENGTH_SHORT).show()
        }
        binding.EditarPerfilAcImageViewIcone2.setOnClickListener{
            perfilVM.setImagemEmFoco(IconeImagem.LUZ)
            //Toast.makeText(applicationContext, "Clicaram no Icone", Toast.LENGTH_SHORT).show()
        }
        binding.EditarPerfilAcImageViewIcone3.setOnClickListener{
            perfilVM.setImagemEmFoco(IconeImagem.CARRO)
            //Toast.makeText(applicationContext, "Clicaram no Icone", Toast.LENGTH_SHORT).show()
        }
        binding.EditarPerfilAcImageViewIcone4.setOnClickListener{
            perfilVM.setImagemEmFoco(IconeImagem.FELIZ)
            //Toast.makeText(applicationContext, "Clicaram no Icone", Toast.LENGTH_SHORT).show()
        }
        binding.EditarPerfilAcImageViewCor1.setOnClickListener{
            perfilVM.setCorEmFoco(IconeCor.DEEP_PURPLE)
            //Toast.makeText(applicationContext, "Clicaram no Icone", Toast.LENGTH_SHORT).show()
        }
        binding.EditarPerfilAcImageViewCor2.setOnClickListener{
            perfilVM.setCorEmFoco(IconeCor.SOFT_PINK)
            //Toast.makeText(applicationContext, "Clicaram no Icone", Toast.LENGTH_SHORT).show()
        }
        binding.EditarPerfilAcImageViewCor3.setOnClickListener{
            perfilVM.setCorEmFoco(IconeCor.WHITE)
            //Toast.makeText(applicationContext, "Clicaram no Icone", Toast.LENGTH_SHORT).show()
        }
        binding.EditarPerfilAcImageViewCor4.setOnClickListener{
            perfilVM.setCorEmFoco(IconeCor.HOT_PINK)
            //Toast.makeText(applicationContext, "Clicaram no Icone", Toast.LENGTH_SHORT).show()
        }
        binding.EditarPerfilAcImageViewCor5.setOnClickListener{
            perfilVM.setCorEmFoco(IconeCor.LEAF_GREEN)
            //Toast.makeText(applicationContext, "Clicaram no Icone", Toast.LENGTH_SHORT).show()
        }

    }

    /**
     * Sets up the observers for the ViewModel LiveData properties.
     *
     * Observes changes in the selected icon image and color, and updates the UI accordingly.
     */
    private fun setObservers(){
        perfilVM.corEmFoco.observe(this, Observer { cor ->
            cor?.let {
                binding.EditarPerfilAcShapeableImageViewIconePerfil.setBackgroundResource(it.colorRes)
                binding.EditarPerfilAcImageViewIcone1.setBackgroundResource(it.colorRes)
                binding.EditarPerfilAcImageViewIcone2.setBackgroundResource(it.colorRes)
                binding.EditarPerfilAcImageViewIcone3.setBackgroundResource(it.colorRes)
                binding.EditarPerfilAcImageViewIcone4.setBackgroundResource(it.colorRes)
            }
        })
        perfilVM.imagemEmFoco.observe(this, Observer { imagem ->
            imagem?.let {
                binding.EditarPerfilAcShapeableImageViewIconePerfil.setImageResource(it.drawableRes)
                binding.EditarPerfilAcImageViewCor1.setImageResource(it.drawableRes)
                binding.EditarPerfilAcImageViewCor2.setImageResource(it.drawableRes)
                binding.EditarPerfilAcImageViewCor3.setImageResource(it.drawableRes)
                binding.EditarPerfilAcImageViewCor4.setImageResource(it.drawableRes)
                binding.EditarPerfilAcImageViewCor5.setImageResource(it.drawableRes)
            }
        })
        appVM.usuarioLogado.observe(this, Observer{
            binding.EditarPerfilAcShapeableImageViewIconePerfil.setImageResource(it.iconeDeUsuario.imagem.drawableRes)
            binding.EditarPerfilAcShapeableImageViewIconePerfil.setBackgroundResource(it.iconeDeUsuario.cor.colorRes)
        })
    }


    /**
     * Called when the activity is stopped.
     *
     * Handles cleanup tasks. No additional actions are needed here as the activity is finished.
     */
    override fun onStop() {
        super.onStop()
        //binding.LoginAcTextViewErroEntrar.visibility = View.GONE // esconder o erro depois que sair da tela
        // não vai previsar por causa do finish
    }

}