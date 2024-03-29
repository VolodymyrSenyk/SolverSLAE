package com.senyk.solvers.slae.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when (modelClass) {
            InputScreenViewModel::class.java -> InputScreenViewModel() as T
            ResultsScreenViewModel::class.java -> ResultsScreenViewModel() as T
            else -> throw ClassNotFoundException(NO_SUCH_CLASS)
        }
    }

    companion object {
        const val NO_SUCH_CLASS = "No such ViewModel class"
    }
}
