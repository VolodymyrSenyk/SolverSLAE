package com.senyk.solvers.slae.view.screens.results.adapters

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.senyk.solvers.slae.R
import kotlinx.android.synthetic.main.result.view.*

class ResultsInputDataMatrixAdapter(private val matrix: Array<DoubleArray>) :
    RecyclerView.Adapter<ResultsInputDataMatrixAdapter.ResultsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultsViewHolder {
        return ResultsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.equation_input, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ResultsViewHolder, position: Int) {
        val item = this.matrix[position]
        for (i in item.indices) {
            val variable = LayoutInflater.from(holder.itemView.context).inflate(R.layout.result, null)
            val result = variable.result
            if (i != item.size - 1) {
                result.text = Html.fromHtml(holder.layout.context.getString(R.string.var_result,i+1, item[i]))
            } else {
                result.text = Html.fromHtml(holder.layout.context.getString(R.string.result,item[i]))
            }
            holder.layout.addView(variable)
        }
    }

    override fun getItemCount(): Int {
        return this.matrix.size
    }

    class ResultsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val layout: LinearLayout = itemView as LinearLayout
    }
}
