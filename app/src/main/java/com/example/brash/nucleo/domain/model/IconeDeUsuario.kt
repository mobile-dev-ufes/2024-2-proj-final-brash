package com.example.brash.nucleo.domain.model

import com.example.brash.nucleo.utils.IconeCor
import com.example.brash.nucleo.utils.IconeImagem


data class IconeDeUsuario (
    val idIconeDeUsuario: Long = 0,
    val cor: IconeCor = IconeCor.DEEP_PURPLE,
    val imagemPath: IconeImagem = IconeImagem.PADRAO
)