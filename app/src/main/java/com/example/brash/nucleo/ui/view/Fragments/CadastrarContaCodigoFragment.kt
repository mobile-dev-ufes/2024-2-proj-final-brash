package com.example.brash.nucleo.ui.view.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.brash.R

class CadastrarContaCodigoFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        super.onCreateView(inflater, container, savedInstanceState)
        val fragView = inflater.inflate(R.layout.fragment_cadastrar_conta_codigo, container, false)
        return fragView

    }
}