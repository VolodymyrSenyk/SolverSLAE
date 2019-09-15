package com.senyk.solvers.slae.model

import kotlin.math.abs

class GaussSeidelMethod : Algorithm() {
    override fun solve(matrix: Array<DoubleArray>): DoubleArray {
        var result = DoubleArray(matrix.size) { 0.0 }
        while (true) {
            val currentVariableValues = DoubleArray(matrix.size)
            for (i in matrix.indices) {
                currentVariableValues[i] = matrix[i][matrix.size]
                for (j in matrix.indices) {
                    if (i > j) {
                        currentVariableValues[i] -= matrix[i][j] * currentVariableValues[j]
                    }
                    if (i < j) {
                        currentVariableValues[i] -= matrix[i][j] * result[j]
                    }
                }
                currentVariableValues[i] /= matrix[i][i]
            }
            var error = 0.0
            for (aMatrix in matrix) {
                var errorTemp = 0.0
                for (j in matrix.indices) {
                    errorTemp += aMatrix[j] * currentVariableValues[j]
                }
                error += abs(errorTemp - aMatrix[matrix.size])
            }
            if (error < EPS) {
                break
            }
            result = currentVariableValues
        }
        return result
    }

    companion object {
        const val EPS = 0.05
    }
}
