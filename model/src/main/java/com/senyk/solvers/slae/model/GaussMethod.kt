package com.senyk.solvers.slae.model

import kotlin.math.abs

class GaussMethod : Algorithm() {
    override fun solve(matrix: Array<DoubleArray>): DoubleArray {
        val A = Array(matrix.size) { DoubleArray(matrix.size) }
        val B = DoubleArray(matrix.size)
        for (i in matrix.indices) {
            for (j in matrix[0].indices) {
                if (j != matrix[0].size - 1) {
                    A[i][j] = matrix[i][j]
                } else {
                    B[i] = matrix[i][j]
                }
            }
        }
        for (k in matrix.indices) {
            // find pivot row
            var max = k
            for (i in k + 1 until matrix.size) {
                if (abs(A[i][k]) > abs(A[max][k])) {
                    max = i
                }
            }
            // swap row in A matrix
            val temp = A[k]
            A[k] = A[max]
            A[max] = temp

            // swap corresponding values in constants matrix
            val t = B[k]
            B[k] = B[max]
            B[max] = t

            // pivot within A and B
            for (i in k + 1 until matrix.size) {
                val factor = A[i][k] / A[k][k]
                B[i] -= factor * B[k]
                for (j in k until matrix.size) {
                    A[i][j] -= factor * A[k][j]
                }
            }
        }

        // Print row echelon form back substitution
        val result = DoubleArray(matrix.size)
        for (i in matrix.indices.reversed()) {
            var sum = 0.0
            for (j in i + 1 until matrix.size) {
                sum += A[i][j] * result[j]
            }
            result[i] = (B[i] - sum) / A[i][i]
        }
        // Print solution
        return result
    }
}
