package com.example.brash.nucleo.ui.view


import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.brash.R
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.HomeAC
import com.example.brash.databinding.NucConfiguracaoAcBinding
import com.example.brash.nucleo.ui.viewModel.ConfiguracaoVM
import com.example.brash.nucleo.utils.UtilsFoos
import com.example.brash.nucleo.utils.saveLanguagePreference


/**
 * Activity for managing user settings and preferences.
 *
 * This activity allows the user to change settings such as language, password,
 * and to sign out from the account.
 */
class ConfiguracaoAC : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: NucConfiguracaoAcBinding
    private lateinit var configuracaoVM: ConfiguracaoVM

    /**
     * Called when the activity is created.
     *
     * Initializes the ViewModel, layout binding, sets up click listeners, and observers.
     *
     * @param savedInstanceState The saved instance state from a previous activity state.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configuracaoVM = ViewModelProvider(this)[ConfiguracaoVM::class.java]

        binding = NucConfiguracaoAcBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setOnClickListeners()
        setObservers()


    }

    /**
     * Sets up the click listeners for buttons and other views.
     */
    private fun setOnClickListeners(){
        binding.ConfiguracaoAcTextViewTrocarSenha.setOnClickListener(this)
        binding.ConfiguracaoAcTextViewSairDaConta.setOnClickListener(this)
        binding.ConfiguracaoAcImageViewRetornar.setOnClickListener(this)
        binding.ConfiguracaoAcTextViewIdioma.setOnClickListener(this)
        binding.ConfiguracaoAcRadioButtonIdiomaPt.setOnClickListener(this)
        binding.ConfiguracaoAcRadioButtonIdiomaEn.setOnClickListener(this)

    }

    /**
     * Sets up the observers for the ViewModel LiveData properties.
     */
    private fun setObservers(){

    }

    /**
     * Handles the click actions for the views.
     *
     * @param view The view that was clicked.
     */
    override fun onClick(view : View) {

        when(view.id){
            R.id.ConfiguracaoAcTextViewTrocarSenha -> {
                //UtilsFoos.showToast(this, "Você clicou na mensgem de ir cadastrar")
                intentToDefinirSenhaActivity()
            }
            R.id.ConfiguracaoAcTextViewSairDaConta ->{
                configuracaoVM.signOut()
                intentToLoginActivity()
            }
            R.id.ConfiguracaoAcImageViewRetornar ->{
                finish()
            }

            R.id.ConfiguracaoAcTextViewIdioma -> {
                // Toggle visibility of the language radio group
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
                saveLanguagePreference(this, "pt")
                restartToHome()
            }

            R.id.ConfiguracaoAcRadioButtonIdiomaEn -> {
                saveLanguagePreference(this, "en")
                restartToHome()
            }

        }

    }
    /**
     * Updates the language preference radio group based on the current locale.
     */
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

    /**
     * Called when the activity is stopped. Hides any error message if displayed.
     */
    override fun onStop() {
        super.onStop()
        //binding.LoginAcTextViewErroEntrar.visibility = View.GONE // esconder o erro depois que sair da tela
        // não vai previsar por causa do finish
    }

    /**
     * Navigates to the login activity.
     */
    private fun intentToLoginActivity(){
        val intent = Intent(this, LoginAC::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        finish()
    }

    /**
     * Navigates to the password reset activity.
     */
    private fun intentToDefinirSenhaActivity(){
        val intent = Intent(this, DefinirSenhaAC::class.java)
        startActivity(intent)
    }

    /**
     * Navigates to the email definition activity.
     */
    private fun intentToDefinirEmailActivity(){
        val intent = Intent(this, DefinirEmailAC::class.java)
        startActivity(intent)
    }

    /**
     * Restarts the application and navigates to the home screen.
     */
    private fun restartToHome() {
        val intent = Intent(this, HomeAC::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        finish()
    }


}