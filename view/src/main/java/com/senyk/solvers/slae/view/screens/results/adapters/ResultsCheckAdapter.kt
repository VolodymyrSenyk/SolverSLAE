package com.senyk.solvers.slae.view.screens.results.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.senyk.solvers.slae.R

class ResultsCheckAdapter(coefficients: Array<DoubleArray>, private val roots: DoubleArray) :
    RecyclerView.Adapter<ResultsCheckAdapter.ResultsViewHolder>() {

    private var data: Array<DoubleArray> = Array(coefficients.size) { DoubleArray(roots.size + 1) }

    init {
        for (i in data.indices) {
            for (j in data[i].indices) {
                if (j == data[i].size - 1) {
                    data[i][j] = getResult(coefficients[i], roots)
                } else {
                    data[i][j] = coefficients[i][j]
                }
            }
        }
    }

    private fun getResult(coefficients: DoubleArray, roots: DoubleArray): Double {
        var res = 0.0
        for (i in coefficients.indices) {
            if (i != coefficients.size - 1) {
                res = res.plus(coefficients[i] * roots[i])
            }
        }
        return res
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultsViewHolder {
        return ResultsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.equation_input,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ResultsViewHolder, position: Int) {
        /*val item = this.data[position]
        for (i in item.indices) {
            if (i != item.size - 1) {
                val variable =
                    LayoutInflater.from(holder.itemView.context).inflate(R.layout.variable_output_field, null)
                        .result
                variable.text = Html.fromHtml(
                    holder.layout.context.getString(
                        R.string.var_check,
                        item[i],
                        roots[i]
                    )
                )
                holder.layout.addView(variable)
            } else {
                val asNext =
                    LayoutInflater.from(holder.itemView.context).inflate(R.layout.equ_linker, null)
                holder.layout.addView(asNext)

                for (ii in item.indices) {
                    if (ii != item.size - 1) {
                        val variable =
                            LayoutInflater.from(holder.itemView.context)
                                .inflate(R.layout.variable_output_field, null).result
                        variable.text = Html.fromHtml(
                            holder.layout.context.getString(
                                R.string.result,
                                item[ii] * roots[ii]
                            )
                        )
                        holder.layout.addView(variable)
                    } else {
                        val asResult =
                            LayoutInflater.from(holder.itemView.context)
                                .inflate(R.layout.equ_linker, null)
                        holder.layout.addView(asResult)

                        val resultField =
                            LayoutInflater.from(holder.itemView.context)
                                .inflate(R.layout.variable_output_field, null)
                        val result =
                            resultField.result
                        result.text =
                            Html.fromHtml(holder.layout.context.getString(R.string.result, item[ii]))
                        holder.layout.addView(resultField)
                    }
                }
            }
        }*/
    }

    override fun getItemCount(): Int {
        return this.data.size
    }

    class ResultsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val layout: LinearLayout = itemView as LinearLayout
    }
}
