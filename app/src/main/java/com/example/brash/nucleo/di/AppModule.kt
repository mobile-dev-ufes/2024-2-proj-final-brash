package com.example.brash.nucleo.di

import android.content.Context
import com.example.brash.nucleo.data.remoto.services.AccountService
import com.example.brash.nucleo.data.remoto.services.impl.AccountServiceImpl

/**
 * Interface that defines the AppModule providing application-level dependencies.
 */
interface AppModule {
    /**
     * Provides the [AccountService] instance for handling account-related operations.
     */
    val accountService: AccountService
}

/**
 * Implementation of the [AppModule] interface.
 *
 * This class provides dependencies like the [AccountService] for the application.
 * It is initialized with the [Context] of the application and lazily initializes
 * the necessary services when required.
 *
 * @param appContext The context of the application used for initializing services.
 */
class AppModuleImpl (
    private val appContext: Context
): AppModule {
    /**
     * Lazily initializes the [AccountService] to manage user account-related tasks.
     */
    override val accountService : AccountService by lazy {
        AccountServiceImpl()
    }
}