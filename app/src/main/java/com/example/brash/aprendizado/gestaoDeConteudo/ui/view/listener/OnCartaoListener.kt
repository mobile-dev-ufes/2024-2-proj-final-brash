package com.example.brash.aprendizado.gestaoDeConteudo.ui.view.listener

import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Cartao


interface OnCartaoListener {
    fun onClick(c: Cartao)
}