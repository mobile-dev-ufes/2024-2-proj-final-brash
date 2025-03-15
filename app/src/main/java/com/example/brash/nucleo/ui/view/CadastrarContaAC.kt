package com.example.brash.nucleo.ui.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import com.example.brash.R
import com.example.brash.databinding.ActivityCadastrarContaBinding
import com.example.brash.databinding.NucCadastrarAcBinding
import com.example.brash.nucleo.ui.view.Fragments.CadastrarFrExito
import com.example.brash.nucleo.ui.view.Fragments.CadastrarFrForm
import com.example.brash.nucleo.ui.viewModel.CadastrarContaVM
import com.example.brash.nucleo.ui.viewModel.LoginVM
import com.example.brash.nucleo.ui.viewModel.viewModelFactory
import com.example.brash.utilsGeral.MyApplication

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