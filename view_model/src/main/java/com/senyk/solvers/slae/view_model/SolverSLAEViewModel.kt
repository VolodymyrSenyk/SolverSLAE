package com.senyk.solvers.slae.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.senyk.solvers.slae.model.Algorithm
import com.senyk.solvers.slae.model.GaussMethod
import com.senyk.solvers.slae.model.GaussSeidelMethod
import java.lang.UnsupportedOperationException

class SolverSLAEViewModel : ViewModel() {
    private lateinit var solver: Algorithm

    var matrixSize: MutableLiveData<Pair<Int, Int>> = MutableLiveData()
    var results: MutableLiveData<DoubleArray> = MutableLiveData()

    init {
        matrixSizeChanged(DEFAULT_MATRIX_SIZE, DEFAULT_MATRIX_SIZE)
    }

    fun matrixSizeChanged(equationsCount: Int, variablesCount: Int) {
        matrixSize.value = Pair(equationsCount, variablesCount)
    }

    @Throws(UnsupportedOperationException::class)
    fun solve(methodIndex: Int, matrix: Array<DoubleArray>) {
        solver = when(methodIndex) {
            GAUSS -> GaussMethod()
            GAUSS_SEIDEL -> GaussSeidelMethod()
            else -> throw UnsupportedOperationException(NO_SUCH_METHOD)
        }
        results.value = solver.solve(matrix)
        matrixSizeChanged(DEFAULT_MATRIX_SIZE, DEFAULT_MATRIX_SIZE)
    }

    companion object{
        const val GAUSS = 1
        const val GAUSS_SEIDEL = 2
        const val DEFAULT_MATRIX_SIZE = 3
        const val NO_SUCH_METHOD = "No such method!"
    }
}
