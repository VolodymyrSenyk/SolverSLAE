package com.senyk.solvers.slae.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.senyk.solvers.slae.model.Algorithm
import com.senyk.solvers.slae.model.GaussMethod
import com.senyk.solvers.slae.model.GaussSeidelMethod

class CalculatorViewModel : ViewModel() {
    private lateinit var solver: Algorithm

    var matrix: MutableLiveData<Array<DoubleArray>> = MutableLiveData()
    var results: MutableLiveData<DoubleArray> = MutableLiveData()

    init {
        matrix.value = Array(DEFAULT_MATRIX_SIZE) {DoubleArray(DEFAULT_MATRIX_SIZE) {DEFAULT_COEFFICIENT} }
    }

    fun matrixSizeChanged(variablesCount: Int = matrix.value!![0].size-1) {
        var coefficientsCount = variablesCount
        coefficientsCount++
        matrix.value = Array(variablesCount) { DoubleArray(coefficientsCount) { DEFAULT_COEFFICIENT } }
    }

    fun getInputDataMatrix() : Array<DoubleArray> {
        return matrix.value!!
    }

    fun coefficientChanged(row: Int, col: Int, value: Double = DEFAULT_COEFFICIENT) {
        matrix.value!![row][col] = value
    }

    fun solve(methodIndex: Int, matrix: Array<DoubleArray> = this.matrix.value!!) {
        solver = when(methodIndex) {
            GAUSS_METHOD -> GaussMethod()
            GAUSS_SEIDEL_METHOD -> GaussSeidelMethod()
            else -> throw UnsupportedOperationException(NO_SUCH_METHOD)
        }
        results.value = solver.solve(matrix)
    }

    companion object{
        const val DEFAULT_MATRIX_SIZE = 1
        const val DEFAULT_COEFFICIENT = 0.0

        const val GAUSS_METHOD = 0
        const val GAUSS_SEIDEL_METHOD = 1

        const val NO_SUCH_METHOD = "No such method"
    }
}
