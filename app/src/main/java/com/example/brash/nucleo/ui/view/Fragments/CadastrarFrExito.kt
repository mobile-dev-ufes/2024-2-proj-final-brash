package com.example.brash.nucleo.ui.view.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.example.brash.R
import com.example.brash.databinding.NucCadastrarFrExitoBinding


/**
 * Fragment that displays a success message after a user has successfully registered.
 * Provides the option for the user to navigate to the login screen.
 */
class CadastrarFrExito : Fragment(R.layout.nuc_cadastrar_fr_exito) {

    private var _binding : NucCadastrarFrExitoBinding? = null
    private val binding get() = _binding!!

    /**
     * Inflates the layout for the fragment and sets up the binding.
     *
     * @param inflater The LayoutInflater to inflate the fragment's layout.
     * @param container The container that holds the fragment's view.
     * @param savedInstanceState A Bundle containing any saved instance state.
     * @return The root view of the inflated layout.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = NucCadastrarFrExitoBinding.inflate(inflater, container, false)
        //binding.CadastrarContaAcButtonFazerLogin.text = "teste de fragmento exito"

        return binding.root
    }

    /**
     * Called when the view has been created. Sets the behavior for back press and button clicks.
     *
     * @param view The root view of the fragment.
     * @param savedInstanceState The saved instance state.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setOnBackPressedToLoginAc()
        setObservers()
        setOnClickListeners()
    }

    /**
     * Sets up observers for the fragment's data. Currently no observers are set.
     */
    private fun setObservers(){

    }

    /**
     * Sets the click listener for the "Fazer Login" button.
     */
    private fun setOnClickListeners(){

        binding.CadastrarContaAcButtonFazerLogin.setOnClickListener({
            intentToLoginAc()
        })

    }

    /**
     * Finishes the activity and navigates to the login screen.
     */
    private fun intentToLoginAc(){
        //val intent = Intent(requireActivity(), LoginAC::class.java)
        //startActivity(intent)
        requireActivity().finish()
    }

    /**
     * Sets up the back button behavior to navigate to the login screen.
     * When the back button is pressed, the user is redirected to the login screen.
     */
    private fun setOnBackPressedToLoginAc(){

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                intentToLoginAc()
            }
        })

    }

    /**
     * Cleans up the binding when the fragment's view is destroyed to prevent memory leaks.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}