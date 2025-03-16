package com.example.brash.aprendizado.gestaoDeConteudo.ui.view


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Cartao
import com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel.HomeVM
import com.example.brash.aprendizado.gestaoDeConteudo.ui.viewModel.RevisaoCartaoVM
import com.example.brash.databinding.GtcRevisaoAcBinding
import com.example.brash.databinding.GtcRevisaoCartaoAcBinding
import com.example.brash.utilsGeral.AppVM
import com.example.brash.utilsGeral.MyApplication


class RevisaoCartaoAC : AppCompatActivity(), View.OnClickListener{

    private lateinit var binding: GtcRevisaoCartaoAcBinding

    private lateinit var revisaoCartaoVM: RevisaoCartaoVM
    private lateinit var appVM: AppVM



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = GtcRevisaoCartaoAcBinding.inflate(layoutInflater)
        setContentView(binding.root)
        revisaoCartaoVM = ViewModelProvider(this)[RevisaoCartaoVM::class.java]
        appVM = (application as MyApplication).appSharedInformation

        appVM.baralhoEmAC.value?.let {
            revisaoCartaoVM.setBaralhoOwner(it)
        } ?: run {
            //Toast.makeText(applicationContext, "Erro: Baralho não encontrado para revisão.", Toast.LENGTH_SHORT).show()
        }

        setOnClickListeners()
        setObservers()
    }

    private fun setOnClickListeners(){
        binding.RevisaoAcImageViewRetornar.setOnClickListener {
            finish()
        }
    }

    private fun setObservers(){

    }

    override fun onClick(view : View) {
    }

    override fun onStop() {
        super.onStop()

    }

}