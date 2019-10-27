package com.senyk.solvers.slae.view.screens.results.adapters

import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.senyk.solvers.slae.R
import com.senyk.solvers.slae.view_model.InputScreenViewModel
import kotlinx.android.synthetic.main.variable_output_field.view.*

class ResultsInputDataMatrixAdapter(private val matrix: Array<DoubleArray>) :
    RecyclerView.Adapter<ResultsInputDataMatrixAdapter.ResultsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultsViewHolder =
        ResultsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.equation_line, parent, false)
        )

    override fun onBindViewHolder(holder: ResultsViewHolder, position: Int) {
        val item = this.matrix[position]
        for (i in item.indices) {
            val outputFieldView = if (i != item.size - 1) {
                bindVariableInputField(holder, item[i], i, position)
            } else {
                bindResultInputField(holder, item[i])
            }
            holder.layout.addView(outputFieldView)
        }
    }

    private fun bindVariableInputField(
        holder: ResultsViewHolder,
        variableValue: Double,
        variableIndex: Int,
        position: Int
    ): View {
        val variableOutputField = LayoutInflater.from(holder.itemView.context)
            .inflate(R.layout.variable_output_field, null)
        bindCoefficientView(variableOutputField.variable_coef, variableValue)
        variableOutputField.variable_label.text =
            HtmlCompat.fromHtml(
                holder.layout.context.getString(
                    R.string.variable,
                    variableIndex + 1
                ), HtmlCompat.FROM_HTML_MODE_LEGACY
            )
        if (variableIndex != matrix[position].size - 2) {
            (variableOutputField as LinearLayout).addView(
                LayoutInflater.from(holder.itemView.context).inflate(R.layout.sum_linker, null)
            )
        }
        return variableOutputField
    }

    private fun bindResultInputField(
        holder: ResultsViewHolder,
        variableValue: Double
    ): View {
        val resultOutputField = LayoutInflater.from(holder.itemView.context)
            .inflate(R.layout.result_output_field, null)
        bindCoefficientView(resultOutputField.variable_coef, variableValue)
        return resultOutputField
    }

    private fun bindCoefficientView(view: TextView, variableValue: Double) {
        if (variableValue != InputScreenViewModel.DEFAULT_COEFFICIENT) {
            if ((variableValue * 10 % 10).toInt() == 0) {
                view.text = SpannableStringBuilder(variableValue.toInt().toString())
            } else {
                view.text = SpannableStringBuilder(variableValue.toString())
            }
        } else {
            view.text =
                SpannableStringBuilder(view.context.getString(R.string.default_coefficient))
        }
    }

    override fun getItemCount(): Int = this.matrix.size

    class ResultsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val layout: LinearLayout = itemView as LinearLayout
    }
}
