package ru.cookedapp.cooked.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider


class ViewModelFactory
@Inject constructor(
    private val viewModels: MutableMap<Class<out ViewModel>, Provider<ViewModel>>
    ) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(vmClass: Class<T>): T {
        val viewModelProvider = viewModels[vmClass]
            ?: throw IllegalArgumentException("ViewModel class $vmClass not found.")
        return viewModelProvider.get() as T
    }

}
