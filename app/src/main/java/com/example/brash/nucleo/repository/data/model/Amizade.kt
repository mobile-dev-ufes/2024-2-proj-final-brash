package com.example.brash.nucleo.repository.data.model


data class Amizade (
    val efetivada: Boolean,
    val requerente: Usuario,
    val requerido: Usuario
)