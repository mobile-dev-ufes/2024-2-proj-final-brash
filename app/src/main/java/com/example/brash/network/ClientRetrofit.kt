package com.example.brash.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//NOT USED YET

class ClientRetrofit {

    companion object{
        private val retrofitCache = mutableMapOf<String, Retrofit>()

        private fun getClientInstance(baseUrl: String) : Retrofit {
            return retrofitCache.getOrPut(baseUrl){
                Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(OkHttpClient.Builder().build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
        }

        fun <S> createService(serviceClass : Class<S>, baseUrl: String) : S{
            return getClientInstance(baseUrl).create(serviceClass)
        }
    }

}