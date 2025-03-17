package com.example.brash.nucleo.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.brash.R
import com.example.brash.aprendizado.gestaoDeConteudo.ui.view.HomeAC
import com.example.brash.databinding.NucLoginAcBinding
import com.example.brash.nucleo.ui.viewModel.LoginVM
import com.example.brash.nucleo.ui.viewModel.viewModelFactory
import com.example.brash.nucleo.utils.UtilsFoos
import com.example.brash.nucleo.utils.getSavedLanguage
import com.example.brash.nucleo.utils.saveLanguagePreference
import com.example.brash.nucleo.utils.setAppLocale
import com.example.brash.utilsGeral.MyApplication

/**
 * Activity responsible for managing the user login process.
 *
 * This activity allows the user to sign in, navigate to password reset or account creation,
 * and change the application's language preference.
 */
class LoginAC : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: NucLoginAcBinding
    private lateinit var loginVM: LoginVM

    /**
     * Called when the activity is created.
     *
     * Initializes the ViewModel, layout binding, and language settings.
     * Also sets the click listeners for various buttons and observes LiveData for error messages.
     *
     * @param savedInstanceState The saved instance state from a previous activity state.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get saved language preference and set locale
        val language = getSavedLanguage(this)
        setAppLocale(this, language)

        // Initialize ViewModel for login
        loginVM = ViewModelProvider(this, viewModelFactory {
            LoginVM(application, MyApplication.appModule.accountService)
        }).get(LoginVM::class.java)

        // Check if user is already stored
        loginVM.userStored({
            intentToHomeActivity()
        })

        // Initialize layout binding
        binding = NucLoginAcBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set click listeners
        setOnClickListeners()

        // Set observers for error messages
        setObservers()
    }

    /**
     * Sets the click listeners for the buttons in the layout.
     */
    private fun setOnClickListeners(){
        binding.LoginAcTextViewEsqueceuSenha.setOnClickListener(this)
        binding.LoginAcButtonEntrar.setOnClickListener(this)
        binding.LoginAcButtonCriar.setOnClickListener(this)
        binding.LoginAcTextViewIdioma.setOnClickListener(this)
        binding.LoginAcRadioButtonIdiomaPt.setOnClickListener(this)
        binding.LoginAcRadioButtonIdiomaEn.setOnClickListener(this)
    }

    /**
     * Sets up observers for LiveData properties in the Login ViewModel.
     * Observes changes in the error message and updates the UI accordingly.
     */
    private fun setObservers(){
        loginVM.erroMessageLD.observe(this, Observer{
            binding.LoginAcTextViewErroEntrar.text = it
            binding.LoginAcTextViewErroEntrar.visibility = View.VISIBLE
        })
    }

    /**
     * Handles click events for various views in the layout.
     *
     * @param view The clicked view.
     */
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
                // Navigate to account registration activity
                intentToCadastrarContaAC()
            }
            R.id.LoginAcTextViewEsqueceuSenha -> {
                // Navigate to password reset activity
                intentToDefinirSenha()
            }
            R.id.LoginAcTextViewIdioma -> {
                // Toggle visibility of language selection options
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
                saveLanguagePreference(this, "pt")
                restartToLoginAc()
            }

            R.id.LoginAcRadioButtonIdiomaEn -> {
                saveLanguagePreference(this, "en")
                restartToLoginAc()
            }
        }
    }

    /**
     * Restarts the login activity to apply the language change.
     */
    private fun restartToLoginAc() {
        val intent = Intent(this, LoginAC::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        finish()
    }

    /**
     * Updates the radio buttons based on the current language setting.
     */
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
     * Starts the home activity.
     */
    private fun intentToHomeActivity(){
        val intent = Intent(this, HomeAC::class.java)
        startActivity(intent)
        finish()
    }

    /**
     * Starts the account registration activity.
     */
    private fun intentToCadastrarContaAC(){
        val intent = Intent(this, CadastrarContaAC::class.java)
        startActivity(intent)
        //TODO:: aqui precisa de finish?
    }

    /**
     * Starts the password reset activity.
     */
    private fun intentToDefinirSenha(){
        val intent = Intent(this, RedefinirSenhaAC::class.java)
        startActivity(intent)
        //TODO:: aqui precisa de finish?
    }
}