package com.senyk.solvers.slae.model

import kotlin.math.abs

class GaussSeidelMethod : Algorithm() {
    override fun solve(matrix: Array<DoubleArray>): DoubleArray {
        val size = matrix.size
        var previousVariableValues = DoubleArray(size) {0.0}
        while (true) {
            val currentVariableValues = DoubleArray(size)
            for (i in 0 until size) {
                currentVariableValues[i] = matrix[i][size]
                for (j in 0 until size) {
                    if (i > j) {
                        currentVariableValues[i] -= matrix[i][j] * currentVariableValues[j]
                    }
                    if (i < j) {
                        currentVariableValues[i] -= matrix[i][j] * previousVariableValues[j]
                    }
                }
                currentVariableValues[i] /= matrix[i][i]
                var error = 0.0
                for (aMatrix in matrix) {
                    error += abs(currentVariableValues[i] - previousVariableValues[i])
                    var errorTemp = 0.0
                    for (j in 0 until size) {
                        errorTemp += aMatrix[j] * currentVariableValues[j]
                    }
                    error += abs(errorTemp - aMatrix[size])
                }
                if (error < EPS) {
                    break
                }
                previousVariableValues = currentVariableValues
            }
            return previousVariableValues
        }
    }

    companion object {
        const val EPS = 0.001
    }
}
