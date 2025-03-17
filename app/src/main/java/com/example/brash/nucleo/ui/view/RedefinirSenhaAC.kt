package com.example.brash.nucleo.ui.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.brash.R
import com.example.brash.databinding.NucRedefinirSenhaAcBinding
import com.example.brash.nucleo.ui.viewModel.DefinirSenhaVM
import com.example.brash.nucleo.ui.viewModel.viewModelFactory
import com.example.brash.utilsGeral.MyApplication

/**
 * Activity responsible for managing the password reset process.
 *
 * This activity allows the user to reset their password by entering their email address,
 * and it communicates with the `DefinirSenhaVM` ViewModel to handle the password change logic.
 */
class RedefinirSenhaAC : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: NucRedefinirSenhaAcBinding
    private lateinit var definirSenhaVM: DefinirSenhaVM

    /**
     * Called when the activity is created.
     *
     * Initializes the ViewModel, sets up the layout binding, and configures the necessary listeners and observers.
     *
     * @param savedInstanceState The saved instance state from a previous activity state.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize the ViewModel with dependency injection
        definirSenhaVM = ViewModelProvider(this, viewModelFactory {
            DefinirSenhaVM(application, MyApplication.appModule.accountService)
        }).get(DefinirSenhaVM::class.java)

        // Initialize binding and set the layout
        binding = NucRedefinirSenhaAcBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set click listeners for buttons
        setOnClickListeners()

        // Set observers for LiveData
        setObservers()

        // Handle the back button press to return to the previous screen
        setOnBackPressedToLoginAc()
    }

    /**
     * Sets up a callback to handle the back button press and navigate back to the previous screen.
     */
    private fun setOnBackPressedToLoginAc(){
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        })
    }

    /**
     * Sets the onClickListeners for the buttons in the layout.
     */
    private fun setOnClickListeners(){
        binding.RedefinirSenhaAcButtonCancelar.setOnClickListener(this)
        binding.RedefinirSenhaAcButtonEnviar.setOnClickListener(this)
    }

    /**
     * Sets up observers for LiveData properties in the ViewModel.
     * Specifically, listens for error messages to display them in the UI.
     */
    private fun setObservers(){
        definirSenhaVM.erroMessageLD.observe(this, Observer{
            binding.LoginAcTextViewErroEntrar.text = it
            binding.LoginAcTextViewErroEntrar.visibility = View.VISIBLE
        })
    }

    /**
     * Handles click events for buttons in the layout.
     *
     * @param view The clicked view.
     */
    override fun onClick(view : View) {
        when(view.id){
            R.id.RedefinirSenhaAcButtonEnviar -> {
                // Get the email entered by the user
                val email = binding.RedefinirSenhaAcTextInputEditTextEmail.text.toString()

                // Attempt to update the user's password and show a success message
                definirSenhaVM.updateUsersPassword(email, {
                    Toast.makeText(applicationContext, R.string.nuc_email_alterar_senha, Toast.LENGTH_LONG).show()
                    finish() // Close the activity after success
                })
            }
            R.id.RedefinirSenhaAcButtonCancelar -> {
                // Cancel the password reset and close the activity
                finish()
            }
        }
    }
}