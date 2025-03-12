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
import com.example.brash.databinding.NucDefinirSenhaFrNovaSenhaBinding
import com.example.brash.nucleo.ui.view.LoginAC
import com.example.brash.nucleo.ui.viewModel.DefinirSenhaVM

class DefinirSenhaFrExito : Fragment(R.layout.nuc_definir_senha_fr_exito) {

    private var _binding : NucDefinirSenhaFrExitoBinding? = null
    private val binding get() = _binding!!

    private lateinit var definirSenhaVM : DefinirSenhaVM

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = NucDefinirSenhaFrExitoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        definirSenhaVM = ViewModelProvider(requireActivity()).get(DefinirSenhaVM::class.java)
        setObservers()
        setOnClickListeners()
        setOnBackPressedToLoginAc()
    }

    private fun setOnClickListeners(){
        binding.DefinirSenhaAcButtonFazerLogin.setOnClickListener {
            definirSenhaVM.signOut()
            intentToLoginAc()
        }
    }

    private fun intentToLoginAc(){
        val intent = Intent(requireActivity(), LoginAC::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    private fun setOnBackPressedToLoginAc(){

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                intentToLoginAc()
            }
        })

    }

    private fun setObservers(){

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}