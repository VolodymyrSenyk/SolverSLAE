package com.senyk.solvers.slae.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        when (modelClass) {
            CalculatorViewModel::class.java -> return CalculatorViewModel() as T
            else -> throw ClassNotFoundException(NO_SUCH_CLASS)
        }
    }

    companion object {
        const val NO_SUCH_CLASS = "No such class"
    }
}
