package com.example.brash.nucleo.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.brash.R
import com.example.brash.databinding.NucPerfilAcBinding
import com.example.brash.nucleo.ui.viewModel.PerfilVM
import com.example.brash.utilsGeral.AppVM
import com.example.brash.utilsGeral.MyApplication

/**
 * Activity responsible for displaying the user's profile information.
 *
 * This activity shows the user's profile icon, display name, and username.
 * It also provides functionality for navigating to the profile editing screen.
 */
class PerfilAC : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: NucPerfilAcBinding
    private lateinit var perfilVM: PerfilVM

    private lateinit var appVM: AppVM

    /**
     * Called when the activity is created.
     *
     * Initializes the ViewModel, layout binding, and app-specific view models.
     * Also sets up the click listeners and observers.
     *
     * @param savedInstanceState The saved instance state from a previous activity state.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize the Profile ViewModel
        perfilVM = ViewModelProvider(this)[PerfilVM::class.java]

        // Initialize binding for layout
        binding = NucPerfilAcBinding.inflate(layoutInflater)

        // Get the global AppVM instance
        appVM = (application as MyApplication).appSharedInformation

        setContentView(binding.root)

        // Set click listeners for buttons
        setOnClickListeners()

        // Set observers for LiveData
        setObservers()

        // Initialize the activity's UI with the logged-in user's data
        initAC()

    }

    /**
     * Initializes the activity by populating the UI with the user's data.
     * Sets the profile icon, display name, and username.
     */
    private fun initAC(){

        appVM.usuarioLogado.value?.let { usuario ->
            // Set profile icon and background color
            binding.PerfilAcShapeableImageViewIconePerfil.setImageResource(usuario.iconeDeUsuario.imagem.drawableRes)
            binding.PerfilAcShapeableImageViewIconePerfil.setBackgroundResource(usuario.iconeDeUsuario.cor.colorRes)

            // Set user's display name and username
            binding.PerfilAcTextViewNomeDeExibicao.text = usuario.nomeDeExibicao
            binding.PerfilAcTextViewNomeDeUsuario.text = getString(R.string.username_display, usuario.nomeDeUsuario)

        } ?: run {
            // Optionally show an error if user data is not available
            //Toast.makeText(this, "Erro ao carregar o ícone do usuário.", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Sets the click listeners for the buttons in the layout.
     */
    private fun setOnClickListeners(){
        binding.PerfilAcImageViewRetornar.setOnClickListener(this)
        binding.PerfilAcButtonEditarPerfil.setOnClickListener(this)
    }

    /**
     * Sets up observers for LiveData properties in the AppVM (application-level ViewModel).
     * Observes changes in the logged-in user's data and updates the UI accordingly.
     */
    private fun setObservers(){
        appVM.usuarioLogado.observe(this, Observer{
            binding.PerfilAcShapeableImageViewIconePerfil.setImageResource(it.iconeDeUsuario.imagem.drawableRes)
            binding.PerfilAcShapeableImageViewIconePerfil.setBackgroundResource(it.iconeDeUsuario.cor.colorRes)
            binding.PerfilAcTextViewNomeDeExibicao.text = it.nomeDeExibicao
            binding.PerfilAcTextViewNomeDeUsuario.text =
                getString(R.string.username_display, it.nomeDeUsuario)
        })
    }


    /**
     * Handles click events for the buttons in the layout.
     *
     * @param view The clicked view.
     */
    override fun onClick(view : View) {

        when(view.id){
            R.id.PerfilAcImageViewRetornar -> {
                // Close the activity when the back button is clicked
                finish()
            }
            R.id.PerfilAcButtonEditarPerfil -> {
                // Navigate to the profile editing screen when the edit button is clicked
                intentToEditarPerfilAC()
            }
        }

    }

    /**
     * Called when the activity is stopped.
     *
     * Performs any necessary cleanup. Currently does not need to hide error messages
     * since the activity will be finished (closed).
     */
    override fun onStop() {
        super.onStop()
        //binding.LoginAcTextViewErroEntrar.visibility = View.GONE // esconder o erro depois que sair da tela
        // não vai previsar por causa do finish
    }

    /**
     * Starts the profile editing activity.
     */
    private fun intentToEditarPerfilAC(){
        val intent = Intent(this, EditarPerfilAC::class.java)
        startActivity(intent)
    }

}