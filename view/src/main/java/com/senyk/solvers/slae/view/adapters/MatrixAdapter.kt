package com.senyk.solvers.slae.view.adapters

import android.text.Editable
import android.text.Html
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.senyk.solvers.slae.R
import com.senyk.solvers.slae.view_model.SolverSLAEViewModel
import kotlinx.android.synthetic.main.variable_field.view.*

class MatrixAdapter(rows: Int, cols: Int, private val model: SolverSLAEViewModel) :
    RecyclerView.Adapter<MatrixAdapter.EquationViewHolder>() {
    private var items: Array<DoubleArray> = Array(rows) { DoubleArray(cols) { 0.0 } }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EquationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.equation, parent, false)
        return EquationViewHolder(view)
    }

    override fun onBindViewHolder(holder: EquationViewHolder, position: Int) {
        val item = this.items[position]
        for (i in item.indices) {
            val variable = if (i == item.size - 1) {
                LayoutInflater.from(holder.itemView.context)
                    .inflate(R.layout.result_field, null)
            } else {
                LayoutInflater.from(holder.itemView.context)
                    .inflate(R.layout.variable_field, null)
            }
            val coefficient = variable.variable_coef
            coefficient.text = SpannableStringBuilder(item[i].toString())

            coefficient.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    text: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    return
                }

                override fun onTextChanged(
                    text: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    return
                }

                override fun afterTextChanged(text: Editable?) {
                    if (text.toString().isNotEmpty()) {
                        model.cellEdited(position, i, text.toString().toDouble())
                    }
                }
            })

            if (i != item.size - 1) {
                val label = variable.variable_label
                label.text = Html.fromHtml(
                    holder.layout.context.getString(
                        R.string.variable,
                        i + 1
                    )
                )
            }
            holder.layout.addView(variable)
        }
    }

    override fun getItemCount(): Int {
        return this.items.size
    }

    class EquationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val layout: LinearLayout = itemView as LinearLayout
    }
}
