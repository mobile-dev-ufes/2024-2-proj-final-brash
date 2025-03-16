package com.example.brash.utilsGeral

/**
 * Class containing constant values used throughout Brash.
 */
class Constants {
    companion object{
        /** Key for access credentials. */
        const val CHAVE_ACESSO_STR = "CHAVE_ACESSO"
        /** Key for storing the user's name. */
        const val CHAVE_NOME = "NOME_USUARIO"
        /** Key for storing the user's password. */
        const val CHAVE_SENHA = "NOME_SENHA"
        /** Base URL for the email API service. */
        const val EMAIL_API_BASE_URL = "https://email-service-brash.onrender.com"
    }

    /**
     * Object containing database message status codes.
     */
    object BD_MSGS {
        /** Operation was successful. */
        const val SUCCESS = 1

        /** Operation failed. */
        const val FAIL = 0

        /** Constraint violation occurred. */
        const val CONSTRAINT = 2

        /** Requested item was not found. */
        const val NOT_FOUND = 3
    }
}