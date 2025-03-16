package com.example.brash.utilsGeral

import android.app.Application
import com.example.brash.nucleo.data.remoto.services.AccountService
import com.example.brash.nucleo.data.remoto.services.impl.AccountServiceImpl
import com.example.brash.nucleo.di.AppModule
import com.example.brash.nucleo.di.AppModuleImpl

class MyApplication : Application() {

    companion object{
        lateinit var appModule: AppModule
    }

    lateinit var appSharedInformation: AppVM

    override fun onCreate() {
        super.onCreate()
        appModule = AppModuleImpl(this)
        // Inicializa o AppVM diretamente com o contexto da aplicação
        appSharedInformation = AppVM(this)
    }
}
