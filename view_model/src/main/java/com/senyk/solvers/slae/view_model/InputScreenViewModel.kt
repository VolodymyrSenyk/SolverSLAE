package com.senyk.solvers.slae.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.senyk.solvers.slae.model.Algorithm
import com.senyk.solvers.slae.model.GaussMethod
import com.senyk.solvers.slae.model.GaussSeidelMethod

class InputScreenViewModel : ViewModel() {
    private lateinit var solver: Algorithm

    private var _matrix = MutableLiveData<Array<DoubleArray>>()
    val matrix: LiveData<Array<DoubleArray>>
        get() = _matrix

    private var _results = MutableLiveData<DoubleArray>()
    val results: LiveData<DoubleArray>
        get() = _results

    private var _algorithmReport = MutableLiveData<String>()
    val algorithmReport: LiveData<String>
        get() = _algorithmReport

    fun matrixSizeChanged(variablesCount: Int) {
        var coefficientsCount = variablesCount
        coefficientsCount++
        _matrix.value =
            Array(variablesCount) { DoubleArray(coefficientsCount) { DEFAULT_COEFFICIENT } }
    }

    fun coefficientChanged(row: Int, col: Int, value: Double = DEFAULT_COEFFICIENT) {
        matrix.value!![row][col] = value
    }

    fun solve(methodIndex: Int) {
        solver = when (methodIndex) {
            GAUSS_METHOD -> GaussMethod()
            GAUSS_SEIDEL_METHOD -> GaussSeidelMethod()
            else -> throw UnsupportedOperationException(NO_SUCH_ALGORITHM)
        }
        _results.value = solver.solve(matrix.value!!)
        _algorithmReport.value = solver.getAlgorithmReport()
    }

    companion object {
        const val DEFAULT_COEFFICIENT = 0.0

        const val GAUSS_METHOD = 0
        const val GAUSS_SEIDEL_METHOD = 1

        const val NO_SUCH_ALGORITHM = "No such algorithm"
    }
}
