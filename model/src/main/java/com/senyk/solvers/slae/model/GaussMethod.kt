package com.senyk.solvers.slae.model

import kotlin.math.abs

class GaussMethod : Algorithm() {
    private val reportString = StringBuilder()

    override fun getAlgorithmReport(): String = String(reportString)

    override fun isSolvable(matrix: Array<DoubleArray>): String {
        return "true"
    }

    override fun solve(matrix: Array<DoubleArray>): DoubleArray {
        val size = matrix.size
        val ia = matrix.size
        val ja = matrix[ia - 1].size
        val A = Array(ia) { DoubleArray(ia) }
        val B = DoubleArray(ia)
        val Old = Array(ia) { DoubleArray(ia) }
        System.arraycopy(matrix, 0, Old, 0, size)
        reportString.append("<h2>Початкова матриця:</h2><table>")
        for (i in 0 until ia) {
            for (j in 0 until ja) {
                if (j != ja - 1) {
                    A[i][j] = matrix[i][j]
                } else {
                    B[i] = matrix[i][j]
                }
            }
        }
        for (i in 0 until ia) {
            reportString.append("<tr>")
            for (j in 0 until ia + 1) {
                reportString.append("<td> |")
                reportString.append(matrix[i][j].toString())
                reportString.append("</td>")
            }
            reportString.append("</tr>")
        }
        reportString.append("</table>")
        val N = B.size
        for (k in 0 until N) {
            var max = k
            for (i in k + 1 until N) {
                if (abs(A[i][k]) > abs(A[max][k])) {
                    max = i
                }
            }
            val temp = A[k]
            A[k] = A[max]
            A[max] = temp
            val t = B[k]
            B[k] = B[max]
            B[max] = t

            for (i in k + 1 until N) {
                val factor = A[i][k] / A[k][k]
                B[i] -= factor * B[k]
                for (j in k until N) {
                    A[i][j] -= factor * A[k][j]
                    matrix[i][j] = A[i][j]

                }
                for (y in 0 until size) {
                    for (m in 0 until size) {
                        matrix[m][size] = B[m]
                        matrix[y][m] = A[y][m]
                    }
                }
                find(matrix)
            }
        }
        val solution = DoubleArray(N)
        for (i in N - 1 downTo 0) {
            var sum = 0.0
            for (j in i + 1 until N)
                sum += A[i][j] * solution[j]
            solution[i] = (B[i] - sum) / A[i][i]

        }
        reportString.append("<h3>Корені системи:</h3>")
        for (i in 0 until N) {
            reportString.append("<p>X")
            reportString.append((i + 1).toString())
            reportString.append("=")
            reportString.append(solution[i].toString())
            reportString.append("</p>")
        }
        var f = 0.0
        reportString.append("<h3>Похибка при розрахунку Y:</h3>")
        for ((c, j) in (0 until size).withIndex()) {
            for (i in 0 until size) {
                f += solution[i] * Old[j][i]
            }
            reportString.append("<p>")
            reportString.append("Похибка при розрахунку Y")
            reportString.append((c + 1).toString())
            reportString.append("=")
            reportString.append(abs(100 - f / Old[c][size] * 100).toString())
            f = 0.0
            reportString.append("%</p>")
        }
        return solution
    }

    private fun find(A: Array<DoubleArray>) {
        val size = A.size
        reportString.append("<h2>Різниця рядків</h2><table>")
        for (i in 0 until size) {
            reportString.append("<tr>")
            for (j in 0 until size + 1) {
                if (i == j) {
                    reportString.append("<th> |")
                    reportString.append(A[i][j].toString())
                    reportString.append("</th>")
                } else {
                    reportString.append("<td> |")
                    reportString.append(A[i][j].toString())
                    reportString.append("</td>")
                }
            }
            reportString.append("</tr>")
        }
        reportString.append("</table>")
    }
}
