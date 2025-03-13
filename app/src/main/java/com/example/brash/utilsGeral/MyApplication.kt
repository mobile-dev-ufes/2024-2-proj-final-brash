package com.example.brash.utilsGeral

import android.app.Application

class MyApplication : Application() {

    lateinit var appSharedInformation: AppVM

    override fun onCreate() {
        super.onCreate()
        // Inicializa o AppVM diretamente com o contexto da aplicação
        appSharedInformation = AppVM(this)
    }
}
