package com.example.brash.aprendizado.gestaoDeConteudo.domain.model

import com.example.brash.aprendizado.gestaoDeConteudo.utils.FiltroDeBuscaListarCartao

data class OpcoesDeBuscaListarCartao(
    val filtrar: FiltroDeBuscaListarCartao = FiltroDeBuscaListarCartao.PERGUNTA
)