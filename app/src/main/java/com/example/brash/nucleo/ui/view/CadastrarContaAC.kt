package com.example.brash.nucleo.ui.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.brash.databinding.NucCadastrarAcBinding

class CadastrarContaAC : AppCompatActivity(), View.OnClickListener{

    private lateinit var binding : NucCadastrarAcBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = NucCadastrarAcBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setOnClickListeners()
    }

    private fun setOnClickListeners(){

    }

    override fun onClick(view : View) {

    }
}