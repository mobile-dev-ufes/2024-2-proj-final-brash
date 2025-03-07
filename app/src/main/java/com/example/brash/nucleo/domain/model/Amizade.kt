package com.example.brash.nucleo.domain.model


data class Amizade (
    val efetivada: Boolean,
    val requerente: Usuario,
    val requerido: Usuario
)