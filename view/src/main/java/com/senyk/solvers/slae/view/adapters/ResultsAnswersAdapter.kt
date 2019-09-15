package com.senyk.solvers.slae.view.adapters

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.senyk.solvers.slae.R
import com.senyk.solvers.slae.view.fragments.ResultsFragment
import kotlinx.android.synthetic.main.result.view.*

class ResultsAnswersAdapter(private val matrix: Array<DoubleArray>) :
    RecyclerView.Adapter<ResultsAnswersAdapter.ResultsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultsViewHolder {
        return ResultsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.equation, parent, false))
    }

    override fun onBindViewHolder(holder: ResultsViewHolder, position: Int) {
        val item = this.matrix[position]
        for (i in item.indices) {
            if (item[i] != DEFAULT_COEFFICIENT) {
                val variable =
                    LayoutInflater.from(holder.itemView.context).inflate(R.layout.result, null)
                variable.result.text = Html.fromHtml(
                    holder.itemView.context.getString(
                        R.string.var_result,
                        position * ResultsFragment.NUM_OF_RESULTS_IN_STRING + i + 1,
                        item[i]
                    )
                )
                holder.layout.addView(variable)
            }
        }
    }

    override fun getItemCount(): Int {
        return this.matrix.size
    }

    class ResultsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val layout: LinearLayout = itemView as LinearLayout
    }

    companion object {
        const val DEFAULT_COEFFICIENT = 0.0
    }
}
