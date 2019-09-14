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

class ResultsAdapter(private val items: DoubleArray) :
    RecyclerView.Adapter<ResultsAdapter.ResultViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.equation, parent, false)
        return ResultViewHolder(view)
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        val item = this.items[position]
        val variable = LayoutInflater.from(holder.itemView.context)
            .inflate(R.layout.result, null)
        variable.result.text = Html.fromHtml(
            holder.layout.context.getString(
                R.string.var_result,
                position + 1,
                item
            )
        )
        holder.layout.addView(variable)
    }

    override fun getItemCount(): Int {
        return this.items.size
    }

    class ResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val layout: LinearLayout = itemView as LinearLayout
    }
}
