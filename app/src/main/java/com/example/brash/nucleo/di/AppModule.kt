package com.example.brash.nucleo.di

import android.content.Context
import com.example.brash.nucleo.data.remoto.services.AccountService
import com.example.brash.nucleo.data.remoto.services.impl.AccountServiceImpl

interface AppModule{
    val accountService: AccountService
}

class AppModuleImpl (
    private val appContext: Context
): AppModule {
    override val accountService : AccountService by lazy {
        AccountServiceImpl()
    }
}