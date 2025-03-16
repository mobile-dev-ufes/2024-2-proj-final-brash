package com.example.brash.nucleo.domain.model

import com.example.brash.nucleo.utils.IconeCor
import com.example.brash.nucleo.utils.IconeImagem

/**
 * Represents the profile icon of the user, including its color and image.
 *
 * @param cor The color of the user icon. Defaults to [IconeCor.DEEP_PURPLE].
 * @param imagem The image of the user icon. Defaults to [IconeImagem.PADRAO].
 */
data class IconeDeUsuario (
    val cor: IconeCor = IconeCor.DEEP_PURPLE,
    val imagem: IconeImagem = IconeImagem.PADRAO
)