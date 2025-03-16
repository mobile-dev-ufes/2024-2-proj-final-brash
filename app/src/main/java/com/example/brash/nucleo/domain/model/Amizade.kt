package com.example.brash.nucleo.domain.model


/**
 * Represents a friendship relationship between two users.
 *
 * @param efetivada A boolean indicating whether the friendship has been confirmed.
 *                  If true, the friendship is confirmed; if false, it's still pending.
 * @param requerente The user who initiated the friendship request.
 * @param requerido The user who received the friendship request.
 */
data class Amizade (
    val efetivada: Boolean,
    val requerente: Usuario,
    val requerido: Usuario
)