package com.example.brash.aprendizado.gestaoDeConteudo.ui.view


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel.HomeVM
import com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel.RevisaoVM
import com.example.brash.databinding.GtcRevisaoAcBinding
import com.example.brash.databinding.GtcRevisaoCartaoAcBinding


class RevisaoCartaoAC : AppCompatActivity(), View.OnClickListener{

    private lateinit var binding: GtcRevisaoCartaoAcBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = GtcRevisaoCartaoAcBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setOnClickListeners()
        setObservers()
    }

    private fun setOnClickListeners(){


    }

    private fun setObservers(){
    }

    override fun onClick(view : View) {
    }

    override fun onStop() {
        super.onStop()

    }

}