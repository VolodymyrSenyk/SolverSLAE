package com.senyk.solvers.slae.view.screens.input.adapters

import android.text.Editable
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.senyk.solvers.slae.R
import com.senyk.solvers.slae.view.helpers.CoefficientChangeListener
import com.senyk.solvers.slae.view_model.InputScreenViewModel
import kotlinx.android.synthetic.main.variable_input_field.view.*

class InputDataMatrixAdapter(
    private val coefficientChanged: (Int, Int, Double) -> Unit
) : RecyclerView.Adapter<InputDataMatrixAdapter.EquationViewHolder>() {

    private var matrix: Array<DoubleArray> = arrayOf(doubleArrayOf(DEFAULT_COEFFICIENT))

    fun setNewData(newMatrix: Array<DoubleArray>) {
        this.matrix = newMatrix
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EquationViewHolder =
        EquationViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.equation_line,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: EquationViewHolder, position: Int) {
        val item = this.matrix[position]
        holder.layout.removeAllViews()
        for (i in item.indices) {
            val inputFieldView = if (i != item.size - 1) {
                bindVariableInputField(holder, item[i], i, position)
            } else {
                bindResultInputField(holder, item[i], i, position)
            }
            holder.layout.addView(inputFieldView)
        }
    }

    private fun bindVariableInputField(
        holder: EquationViewHolder,
        variableValue: Double,
        variableIndex: Int,
        position: Int
    ): View {
        val variableInputField = LayoutInflater.from(holder.itemView.context)
            .inflate(R.layout.variable_input_field, null)
        bindCoefficientView(
            variableInputField.variable_coef,
            variableValue,
            variableIndex,
            position
        )
        variableInputField.variable_label.text =
            HtmlCompat.fromHtml(
                holder.layout.context.getString(
                    R.string.variable,
                    variableIndex + 1
                ), HtmlCompat.FROM_HTML_MODE_LEGACY
            )
        if (variableIndex != matrix[position].size - 2) {
            (variableInputField as LinearLayout).addView(
                LayoutInflater.from(holder.itemView.context).inflate(R.layout.sum_linker, null)
            )
        }
        return variableInputField
    }

    private fun bindResultInputField(
        holder: EquationViewHolder,
        variableValue: Double,
        variableIndex: Int,
        position: Int
    ): View {
        val resultInputField = LayoutInflater.from(holder.itemView.context)
            .inflate(R.layout.result_input_field, null)
        bindCoefficientView(resultInputField.variable_coef, variableValue, variableIndex, position)
        return resultInputField
    }

    private fun bindCoefficientView(
        view: EditText,
        variableValue: Double,
        variableIndex: Int,
        position: Int
    ) {
        view.addTextChangedListener(object : CoefficientChangeListener() {
            override fun afterTextChanged(text: Editable?) {
                if (text.toString().isNotEmpty()) {
                    if (text.toString().contains(Regex("[0-9]"))) {
                        coefficientChanged(
                            position,
                            variableIndex,
                            text.toString().toDouble()
                        )
                    }
                } else {
                    coefficientChanged(
                        position, variableIndex,
                        DEFAULT_COEFFICIENT
                    )
                }
            }
        })
        if (variableValue != InputScreenViewModel.DEFAULT_COEFFICIENT) {
            view.text = SpannableStringBuilder(variableValue.toString())
        }
    }

    override fun getItemCount(): Int = this.matrix.size

    class EquationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val layout: LinearLayout = itemView as LinearLayout
    }

    companion object {
        const val DEFAULT_COEFFICIENT = 0.0
    }

}
