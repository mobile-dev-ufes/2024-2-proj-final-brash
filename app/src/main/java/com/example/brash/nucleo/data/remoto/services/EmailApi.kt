package com.example.brash.nucleo.data.remoto.services

import com.example.brash.nucleo.data.remoto.entities.EmailRequestEntity
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface EmailApi {
    @POST("/send-email")
    fun sendEmail(@Body emailRequest: EmailRequestEntity): Call<Void>
}