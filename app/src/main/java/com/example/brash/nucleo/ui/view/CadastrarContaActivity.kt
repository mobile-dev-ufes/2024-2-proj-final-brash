package com.example.brash.nucleo.ui.view

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.example.brash.R
import com.example.brash.databinding.ActivityCadastrarContaBinding
import com.example.brash.nucleo.ui.view.Fragments.CadastrarContaFormFragment

class CadastrarContaActivity : AppCompatActivity(), View.OnClickListener{

    private lateinit var binding : ActivityCadastrarContaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCadastrarContaBinding.inflate(layoutInflater)
        setContentView(binding.root)


        loadFragment()
        setOnClickListeners()
    }

    private fun loadFragment(){

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<CadastrarContaFormFragment>(R.id.CadastrarContaActivityFragmentForm)
        }

    }

    private fun setOnClickListeners(){}

    override fun onClick(view : View) {}
}