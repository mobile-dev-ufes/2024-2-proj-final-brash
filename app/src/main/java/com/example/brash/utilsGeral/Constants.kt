package com.example.brash.utilsGeral

class Constants {
    companion object{
        const val CHAVE_ACESSO_STR = "CHAVE_ACESSO"
        const val CHAVE_NOME = "NOME_USUARIO"
        const val CHAVE_SENHA = "NOME_SENHA"
        const val EMAIL_API_BASE_URL = "https://email-service-brash.onrender.com"
    }

    object BD_MSGS {
        val SUCCESS = 1
        val FAIL = 0
        val CONSTRAINT = 2
        val NOT_FOUND = 3
    }
}