package com.senyk.solvers.slae.view.screens.results.adapters

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.senyk.solvers.slae.R
import kotlinx.android.synthetic.main.checking_multiply.view.*
import kotlinx.android.synthetic.main.checking_single_num.view.*
import kotlinx.android.synthetic.main.variable_output_field.view.variable_coef

class RootsCheckAdapter(
    private val coefficients: Array<DoubleArray>,
    private val roots: DoubleArray
) : RecyclerView.Adapter<RootsCheckAdapter.ResultsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultsViewHolder =
        ResultsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.equation_line,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ResultsViewHolder, position: Int) {
        bindFirstStep(holder, position)
        val linkerFirstToSecondStep = LayoutInflater.from(holder.itemView.context)
            .inflate(R.layout.equ_linker, null)
        holder.layout.addView(linkerFirstToSecondStep)
        bindSecondStep(holder, position)
        val linkerSecondStepToResult = LayoutInflater.from(holder.itemView.context)
            .inflate(R.layout.equ_linker, null)
        holder.layout.addView(linkerSecondStepToResult)
        bindResult(holder, position)
    }

    private fun bindFirstStep(
        holder: ResultsViewHolder,
        position: Int
    ) {
        for (i in 0..coefficients[position].size - 2) {
            val multiplicationView = LayoutInflater.from(holder.itemView.context)
                .inflate(R.layout.checking_multiply, null)
            multiplicationView.variable_coef.text = coefficients[position][i].toFormattedString()
            multiplicationView.root.text = roots[i].toFormattedString()
            holder.layout.addView(multiplicationView)
            if (i < coefficients[position].size - 2) {
                val linker = LayoutInflater.from(holder.itemView.context)
                    .inflate(R.layout.sum_linker, null)
                holder.layout.addView(linker)
            }
        }
    }

    private fun bindSecondStep(
        holder: ResultsViewHolder,
        position: Int
    ) {
        for (i in 0..coefficients[position].size - 2) {
            val multiplicationResView = LayoutInflater.from(holder.itemView.context)
                .inflate(R.layout.checking_single_num, null)
            multiplicationResView.result.text =
                (coefficients[position][i].times(roots[i])).toFormattedString()
            holder.layout.addView(multiplicationResView)
            if (i < coefficients[position].size - 2) {
                val linker = LayoutInflater.from(holder.itemView.context)
                    .inflate(R.layout.sum_linker, null)
                holder.layout.addView(linker)
            }
        }
    }

    private fun bindResult(
        holder: ResultsViewHolder,
        position: Int
    ) {
        var result = 0.0
        for (i in 0..coefficients[position].size - 2) {
            result = result.plus(coefficients[position][i].times(roots[i]))
        }
        val resultView = LayoutInflater.from(holder.itemView.context)
            .inflate(R.layout.checking_single_num, null)
        resultView.result.text = result.toString()
        resultView.result.setTypeface(resultView.result.typeface, Typeface.BOLD)
        holder.layout.addView(resultView)
    }

    override fun getItemCount(): Int = this.coefficients.size

    private fun Double.toFormattedString(): String = if ((this * 10).rem(10).toInt() == 0) {
        this.toInt().toString()
    } else {
        this.toString()
    }

    class ResultsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val layout: LinearLayout = itemView as LinearLayout
    }
}
