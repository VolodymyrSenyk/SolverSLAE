package com.senyk.solvers.slae.model

abstract class Algorithm {
    abstract fun getAlgorithmReport(): String
    abstract fun isSolvable(matrix: Array<DoubleArray>): String
    abstract fun solve(matrix: Array<DoubleArray>): DoubleArray

    companion object {
        const val IS_VALID = "true"
    }
}
