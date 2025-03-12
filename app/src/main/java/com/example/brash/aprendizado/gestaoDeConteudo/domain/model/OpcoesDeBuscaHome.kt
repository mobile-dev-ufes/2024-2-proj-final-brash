package com.example.brash.aprendizado.gestaoDeConteudo.domain.model

import com.example.brash.aprendizado.gestaoDeConteudo.utils.FiltroDeBuscaHome
import com.example.brash.aprendizado.gestaoDeConteudo.utils.OrdemDeBuscaHome

data class OpcoesDeBuscaHome(
    val ordenar: OrdemDeBuscaHome = OrdemDeBuscaHome.ALFANUMERICO,
    val filtrar: FiltroDeBuscaHome = FiltroDeBuscaHome.TODOS
)