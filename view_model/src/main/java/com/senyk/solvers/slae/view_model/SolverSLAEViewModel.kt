package com.senyk.solvers.slae.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.senyk.solvers.slae.model.Algorithm
import com.senyk.solvers.slae.model.GaussMethod
import com.senyk.solvers.slae.model.GaussSeidelMethod

class SolverSLAEViewModel : ViewModel() {
    private lateinit var solver: Algorithm

    lateinit var matrix: Array<DoubleArray>
    var matrixSize: MutableLiveData<Pair<Int, Int>> = MutableLiveData()
    var results: MutableLiveData<DoubleArray> = MutableLiveData()

    init {
        matrixSizeChanged(DEFAULT_MATRIX_SIZE, DEFAULT_MATRIX_SIZE)
    }

    fun matrixSizeChanged(equationsCount: Int, variablesCount: Int) {
        matrixSize.value = Pair(equationsCount, variablesCount)
        matrix = Array(equationsCount) {DoubleArray(variablesCount+1) {0.0} }
    }

    fun cellEdited(row: Int, col: Int, value: Double) {
        matrix[row][col] = value
    }

    @Throws(UnsupportedOperationException::class)
    fun solve(methodIndex: Int) {
        solver = when(methodIndex) {
            GAUSS -> GaussMethod()
            GAUSS_SEIDEL -> GaussSeidelMethod()
            else -> throw UnsupportedOperationException(NO_SUCH_METHOD)
        }
        results.value = solver.solve(matrix)
        matrixSizeChanged(DEFAULT_MATRIX_SIZE, DEFAULT_MATRIX_SIZE)
    }

    companion object{
        const val GAUSS = 0
        const val GAUSS_SEIDEL = 1
        const val DEFAULT_MATRIX_SIZE = 2
        const val NO_SUCH_METHOD = "No such method!"
    }
}
