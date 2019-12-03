package com.senyk.solvers.slae.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.senyk.solvers.slae.model.Algorithm
import com.senyk.solvers.slae.model.GaussMethod
import com.senyk.solvers.slae.model.GaussSeidelMethod
import com.senyk.solvers.slae.view_model.helper.SingleEventLiveData

class InputScreenViewModel : ViewModel() {
    private lateinit var solver: Algorithm

    private var _matrix = MutableLiveData<Array<DoubleArray>>()
    val matrix: LiveData<Array<DoubleArray>>
        get() = _matrix

    private lateinit var _results: DoubleArray
    val results: DoubleArray
        get() = _results

    private lateinit var _algorithmReport: String
    val algorithmReport: String
        get() = _algorithmReport

    private var _message = SingleEventLiveData<String>()
    val message: LiveData<String>
        get() = _message

    private var _isSolved = SingleEventLiveData<Boolean>()
    val isSolved: LiveData<Boolean>
        get() = _isSolved

    fun matrixSizeChanged(variablesCount: Int) {
        val coefficientsCount = variablesCount + 1
        _matrix.value =
            Array(variablesCount) { DoubleArray(coefficientsCount) { DEFAULT_COEFFICIENT } }
    }

    fun coefficientChanged(row: Int, col: Int, value: Double = DEFAULT_COEFFICIENT) {
        if (!matrix.value.isNullOrEmpty()) matrix.value!![row][col] = value
    }

    fun solve(methodIndex: Int) {
        solver = when (methodIndex) {
            GAUSS_METHOD -> GaussMethod()
            GAUSS_SEIDEL_METHOD -> GaussSeidelMethod()
            else -> throw UnsupportedOperationException(NO_SUCH_ALGORITHM)
        }
        if (matrix.value != null) {
            val isInputDataValid = solver.isSolvable(matrix.value!!.deepCopy())
            if (isInputDataValid == Algorithm.IS_VALID) {
                _results = solver.solve(matrix.value!!.deepCopy())
                _algorithmReport = solver.getAlgorithmReport()
                _isSolved.setValue(true)
            } else {
                _isSolved.setValue(false)
                _message.setValue(isInputDataValid)
            }
        }
    }

    private fun Array<DoubleArray>.deepCopy() = map { it.clone() }.toTypedArray()

    companion object {
        const val DEFAULT_COEFFICIENT = 0.0

        const val GAUSS_METHOD = 0
        const val GAUSS_SEIDEL_METHOD = 1

        const val NO_SUCH_ALGORITHM = "No such algorithm"
    }
}
