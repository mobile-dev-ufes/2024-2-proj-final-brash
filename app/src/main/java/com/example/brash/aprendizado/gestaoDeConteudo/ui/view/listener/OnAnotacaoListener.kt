package com.example.brash.aprendizado.gestaoDeConteudo.ui.view.listener

import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Anotacao
import com.example.brash.aprendizado.gestaoDeConteudo.domain.model.Cartao


interface OnAnotacaoListener {
    fun onClick(anotacao: Anotacao)
}