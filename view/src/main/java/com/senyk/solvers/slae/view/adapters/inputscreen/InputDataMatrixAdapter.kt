package com.senyk.solvers.slae.view.adapters.inputscreen

import android.text.Editable
import android.text.Html
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.senyk.solvers.slae.R
import com.senyk.solvers.slae.view.helpers.CoefficientChangeListener
import com.senyk.solvers.slae.view_model.InputScreenViewModel
import kotlinx.android.synthetic.main.variable_input_field.view.*

class InputDataMatrixAdapter(
    private val matrix: Array<DoubleArray>,
    private val viewModel: InputScreenViewModel
) : RecyclerView.Adapter<InputDataMatrixAdapter.EquationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EquationViewHolder {
        return EquationViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.equation_input,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: EquationViewHolder, position: Int) {
        val item = this.matrix[position]
        for (i in item.indices) {
            val variable = if (i == item.size - 1) {
                LayoutInflater.from(holder.itemView.context)
                    .inflate(R.layout.result_input_field, null)
            } else {
                LayoutInflater.from(holder.itemView.context)
                    .inflate(R.layout.variable_input_field, null)
            }

            val coefficient = variable.variable_coef
            coefficient.addTextChangedListener(object : CoefficientChangeListener() {
                override fun afterTextChanged(text: Editable?) {
                    if (text.toString().isNotEmpty()) {
                        if (text.toString().contains(Regex("[0-9]"))) {
                            viewModel.coefficientChanged(position, i, text.toString().toDouble())
                        }
                    } else {
                        viewModel.coefficientChanged(position, i)
                    }
                }
            })

            if (item[i] != InputScreenViewModel.DEFAULT_COEFFICIENT) {
                coefficient.text = SpannableStringBuilder(item[i].toString())
            }

            if (i != item.size - 1) {
                val label = variable.variable_label
                label.text =
                    Html.fromHtml(holder.layout.context.getString(R.string.variable, i + 1))
            }

            holder.layout.addView(variable)
        }
    }

    override fun getItemCount(): Int {
        return this.matrix.size
    }

    class EquationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val layout: LinearLayout = itemView as LinearLayout
    }

}