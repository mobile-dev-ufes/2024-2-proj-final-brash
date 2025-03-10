package com.example.brash.nucleo.data.remoto.entities


data class EmailRequestEntity(
    val to: String,
    val subject: String,
    val text: String
)