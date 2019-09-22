package com.senyk.solvers.slae.view.adapters

import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.senyk.solvers.slae.R
import kotlinx.android.synthetic.main.result.view.*

class ResultsAdapter(private val results: DoubleArray) :
    RecyclerView.Adapter<ResultsAdapter.ResultsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultsViewHolder {
        return ResultsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.equation,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ResultsViewHolder, position: Int) {
        val item = this.results[position]
        val variable = LayoutInflater.from(holder.itemView.context).inflate(R.layout.result, null)
        val result = variable.result
        Log.e("TAG", item.toString())
        result.text =
            Html.fromHtml(holder.layout.context.getString(R.string.var_answer, position + 1, item))
        holder.layout.addView(variable)
    }

    override fun getItemCount(): Int {
        return this.results.size
    }

    class ResultsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val layout: LinearLayout = itemView as LinearLayout
    }
}
