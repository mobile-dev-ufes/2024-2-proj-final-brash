package com.example.brash.nucleo.ui.view.Fragments
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.example.brash.R
import com.example.brash.databinding.NucCadastrarFrExitoBinding
import com.example.brash.databinding.NucCadastrarFrFormBinding
import com.example.brash.nucleo.ui.view.LoginAC


class CadastrarFrExito : Fragment(R.layout.nuc_cadastrar_fr_exito) {

    private var _binding : NucCadastrarFrExitoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = NucCadastrarFrExitoBinding.inflate(inflater, container, false)
        binding.CadastrarContaAcButtonFazerLogin.text = "teste de fragmento exito"

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setNewOnBackPressed()
        setObservers()
        setOnClickListeners()
    }

    private fun setObservers(){

        ///TODO!

    }

    private fun setOnClickListeners(){

        binding.CadastrarContaAcButtonFazerLogin.setOnClickListener({
            intentToLoginAc()
        })

    }

    private fun intentToLoginAc(){
        val intent = Intent(requireActivity(), LoginAC::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    private fun setNewOnBackPressed(){

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                intentToLoginAc()
            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}