package com.example.brash.nucleo.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Creates a custom [ViewModelProvider.Factory] to instantiate a [ViewModel] class.
 *
 * This function simplifies the creation of a ViewModel when the ViewModel requires custom initialization.
 *
 * @param VM The type of ViewModel to be created.
 * @param initializer A lambda function used to initialize the ViewModel.
 * @return A [ViewModelProvider.Factory] that can create instances of the specified [ViewModel].
 */
fun <VM: ViewModel> viewModelFactory(initializer: () -> VM): ViewModelProvider.Factory{
    return object : ViewModelProvider.Factory{
        override fun <T: ViewModel> create(modelClass: Class<T>) : T {
            return initializer() as T
        }
    }
}