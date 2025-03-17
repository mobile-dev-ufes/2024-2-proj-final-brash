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
 * Activity for resetting the logged user's password.
 *
 * This activity allows the user to reset their password by providing their email
 * and receiving instructions on how to proceed with changing their password.
 */
class DefinirSenhaAC : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: NucRedefinirSenhaAcBinding
    private lateinit var definirSenhaVM: DefinirSenhaVM

    /**
     * Called when the activity is created.
     *
     * Initializes the ViewModel, layout binding, and sets up the click listeners and observers.
     * Populates the email field with the current user's email and disables editing.
     *
     * @param savedInstanceState The saved instance state from a previous activity state.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        definirSenhaVM = ViewModelProvider(this, viewModelFactory {
            DefinirSenhaVM(application, MyApplication.appModule.accountService)
        }).get(DefinirSenhaVM::class.java)

        binding = NucRedefinirSenhaAcBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set a fixed email and disable editing
        val userEmail = definirSenhaVM.currentUserEmail().toString()

        binding.RedefinirSenhaAcTextInputLayoutNome.suffixText = null
        binding.RedefinirSenhaAcTextInputLayoutNome.endIconMode = com.google.android.material.textfield.TextInputLayout.END_ICON_NONE
        binding.RedefinirSenhaAcTextInputLayoutNome.setBoxBackgroundColorResource(R.color.medium_medium_gray)

        binding.RedefinirSenhaAcTextInputEditTextEmail.setTextColor(resources.getColor(R.color.medium_gray))
        binding.RedefinirSenhaAcTextInputEditTextEmail.setText(userEmail)
        binding.RedefinirSenhaAcTextInputEditTextEmail.isEnabled = false
        binding.RedefinirSenhaAcTextInputEditTextEmail.isFocusableInTouchMode = false
        binding.RedefinirSenhaAcTextInputEditTextEmail.isFocusable = false

        setOnClickListeners()
        setObservers()
        setOnBackPressedToLoginAc()
    }

    /**
     * Sets up the back press behavior to return to the login screen.
     */
    private fun setOnBackPressedToLoginAc(){
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        })
    }

    /**
     * Sets the click listeners for the cancel and submit buttons.
     */
    private fun setOnClickListeners(){
        binding.RedefinirSenhaAcButtonCancelar.setOnClickListener(this)
        binding.RedefinirSenhaAcButtonEnviar.setOnClickListener(this)
    }

    /**
     * Sets up the observers for the ViewModel LiveData properties.
     *
     * Observes changes to error messages and displays them in the UI.
     */
    private fun setObservers(){
        definirSenhaVM.erroMessageLD.observe(this, Observer{
            binding.LoginAcTextViewErroEntrar.text = it
            binding.LoginAcTextViewErroEntrar.visibility = View.VISIBLE
        })
    }

    /**
     * Handles button clicks.
     *
     * @param view The view that was clicked.
     */
    override fun onClick(view : View) {
        when(view.id){
            R.id.RedefinirSenhaAcButtonEnviar -> {
                val email = binding.RedefinirSenhaAcTextInputEditTextEmail.text.toString()

                definirSenhaVM.updateUsersPassword(email, {
                    Toast.makeText(applicationContext, R.string.nuc_email_alterar_senha, Toast.LENGTH_LONG).show()
                    finish()
                })
            }
            R.id.RedefinirSenhaAcButtonCancelar -> {
                finish()
            }
        }

    }
}