package com.example.brash.nucleo.ui.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.brash.databinding.NucCadastrarAcBinding

/**
 * Activity for managing user account registration.
 *
 * This activity allows the user to complete the account registration process
 * by displaying the necessary forms or success message.
 */
class CadastrarContaAC : AppCompatActivity(), View.OnClickListener{

    private lateinit var binding : NucCadastrarAcBinding

    /**
     * Called when the activity is created.
     *
     * Initializes the layout binding, and sets up click listeners.
     *
     * @param savedInstanceState The saved instance state from a previous activity state.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = NucCadastrarAcBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setOnClickListeners()
    }

    /**
     * Sets up the click listeners for the views.
     */
    private fun setOnClickListeners(){

    }

    /**
     * Handles the click actions for the views.
     *
     * @param view The view that was clicked.
     */

    override fun onClick(view : View) {

    }
}