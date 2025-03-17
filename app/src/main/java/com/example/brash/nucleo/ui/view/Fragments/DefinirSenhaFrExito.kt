package com.example.brash.nucleo.ui.view.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.brash.R
import com.example.brash.databinding.NucDefinirSenhaFrExitoBinding
import com.example.brash.nucleo.ui.view.LoginAC
import com.example.brash.nucleo.ui.viewModel.DefinirSenhaVM

//NOT USED

/**
 * Fragment shown after the user successfully resets their password.
 *
 * This fragment displays a success message and provides the option to log in again.
 */
class DefinirSenhaFrExito : Fragment(R.layout.nuc_definir_senha_fr_exito) {

    private var _binding : NucDefinirSenhaFrExitoBinding? = null
    private val binding get() = _binding!!

    private lateinit var definirSenhaVM : DefinirSenhaVM
    /**
     * Inflates the layout for the fragment and returns the root view.
     *
     * @param inflater The LayoutInflater to inflate the fragment's layout.
     * @param container The container that holds the fragment's view.
     * @param savedInstanceState A Bundle containing any saved instance state.
     * @return The root view of the inflated layout.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = NucDefinirSenhaFrExitoBinding.inflate(inflater, container, false)
        return binding.root
    }
    /**
     * Called when the view has been created. Sets up observers, click listeners,
     * and back press handling for navigation.
     *
     * @param view The root view of the fragment.
     * @param savedInstanceState The saved instance state.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        definirSenhaVM = ViewModelProvider(requireActivity())[DefinirSenhaVM::class.java]
        setObservers()
        setOnClickListeners()
        setOnBackPressedToLoginAc()
    }
    /**
     * Sets the click listeners for the buttons in the fragment.
     */
    private fun setOnClickListeners(){
        binding.DefinirSenhaAcButtonFazerLogin.setOnClickListener {
            definirSenhaVM.signOut()
            intentToLoginAc()
        }
    }
    /**
     * Navigates to the Login activity after the password reset is successful.
     */
    private fun intentToLoginAc(){
        val intent = Intent(requireActivity(), LoginAC::class.java)
        startActivity(intent)
        requireActivity().finish()
    }
    /**
     * Handles the back press action by navigating to the Login activity.
     */
    private fun setOnBackPressedToLoginAc(){

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                intentToLoginAc()
            }
        })

    }
    /**
     * Sets up any necessary observers for data changes. Currently, there are no observers in this fragment.
     */
    private fun setObservers(){

    }
    /**
     * Cleans up the binding to avoid memory leaks when the view is destroyed.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}