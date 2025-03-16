package com.example.brash.utilsGeral

import android.app.Application
import com.example.brash.nucleo.di.AppModule
import com.example.brash.nucleo.di.AppModuleImpl

/**
 * Custom Application class for initializing global dependencies and shared information.
 */
class MyApplication : Application() {

    companion object{
        /**
         * Application-wide module instance for dependency injection.
         */
        lateinit var appModule: AppModule
    }

    /**
     * Shared application-level information.
     */
    lateinit var appSharedInformation: AppVM

    /**
     * Called when the application is starting. Used to initialize global dependencies.
     */
    override fun onCreate() {
        super.onCreate()
        appModule = AppModuleImpl(this)
        // Initializes AppVM directly with the application context
        appSharedInformation = AppVM(this)
    }
}
