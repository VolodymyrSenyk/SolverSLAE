package com.senyk.solvers.slae.model

import kotlin.math.abs

class GaussSeidelMethod : Algorithm() {
    private val reportString = StringBuilder()

    override fun getAlgorithmReport(): String = String(reportString)

    override fun isSolvable(matrix: Array<DoubleArray>): String {
        return IS_VALID
    }

    override fun solve(matrix: Array<DoubleArray>): DoubleArray {
        val eps = 0.0001
        val size = matrix.size
        val maxM = IntArray(size)
        var max = 0.0
        val matrixDominate = Array(size) { DoubleArray(size + 1) }
        reportString.append("<h2>Початкова матриця:</h2><table>")
        for (i in 0 until size) {
            reportString.append("<tr>")
            for (j in 0 until size + 1) {
                reportString.append("<td> |")
                reportString.append(matrix[i][j].toString())
                reportString.append("</td>")
            }
            reportString.append("</tr>")
        }
        reportString.append("</table>")
        reportString.append("<h2>Перестановка рядків:</h2><table>")
        for (i in 0 until size) {
            for (j in 0 until size) {
                if (abs(matrix[j][i]) > max) {
                    maxM[i] = j
                    max = abs(matrix[j][i])
                }

            }
            reportString.append("<tr>")
            for (j in 0 until size + 1) {
                matrixDominate[i][j] = matrix[maxM[i]][j]
                if (i == j) {
                    reportString.append("<th> |")
                    reportString.append(matrixDominate[i][j].toString())
                    reportString.append("</th>")
                } else {
                    reportString.append("<td> |")
                    reportString.append(matrixDominate[i][j].toString())
                    reportString.append("</td>")
                    matrix[maxM[i]][j] = 0.0
                    max = 0.0

                }
            }
            reportString.append("</tr>")
        }
        reportString.append("</table>")
        var previousVariableValues = DoubleArray(size)
        java.util.Arrays.fill(previousVariableValues, 0.0)
        var iterations = 1.0
        var timeX = 1
        reportString.append("<p>Ітерація №1</p>")
        while (true) {
            val currentVariableValues = DoubleArray(size)
            val C = IntArray(size)
            for (i in 0 until size) {
                currentVariableValues[i] = matrixDominate[i][size]

                for (j in 0 until size) {
                    if (i < j || i > j) {
                        currentVariableValues[i] -= matrixDominate[i][j] * previousVariableValues[j]
                    }
                }
                currentVariableValues[i] /= matrixDominate[i][i]
                C[0] = i
                if (timeX > size) {
                    timeX = 1
                    iterations++
                    reportString.append("<p>Ітерація №")
                    reportString.append(iterations.toString())
                    reportString.append("</p>")
                }
                reportString.append("<p> X")
                reportString.append(timeX.toString())
                reportString.append("=")
                timeX++
                reportString.append(currentVariableValues[i].toString())
                reportString.append("</p>")
            }

            var error = 0.0

            for (aMatrix in matrixDominate) {
                error += abs(currentVariableValues[C[0]] - previousVariableValues[C[0]])

                var errorTemp = 0.0
                for (j in 0 until size) {
                    errorTemp += aMatrix[j] * currentVariableValues[j]
                }

                error += abs(errorTemp - aMatrix[size])
            }
            if (error < eps) {
                break
            }
            previousVariableValues = currentVariableValues

        }
        var f = 0.0
        reportString.append("<h3>Корені системи:</h3>")
        for (i in 0 until size) {
            reportString.append("<p>X")
            reportString.append((i + 1).toString())
            reportString.append("=")
            reportString.append(previousVariableValues[i].toString())
            reportString.append("</p>")
        }
        reportString.append("<h3>Похибка при розрахунку Y:</h3>")
        for ((c, j) in (0 until size).withIndex()) {
            for (i in 0 until size) {
                f += previousVariableValues[i] * matrixDominate[j][i]
            }
            reportString.append("<p>")
            reportString.append("Похибка при розрахунку Y")
            reportString.append((c + 1).toString())
            reportString.append("=")
            reportString.append(abs(100 - f / matrixDominate[c][size] * 100).toString())
            f = 0.0
            reportString.append("%</p>")
        }
        return previousVariableValues

    }
}
