package com.senyk.solvers.slae.view.screens.results.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.senyk.solvers.slae.R
import kotlinx.android.synthetic.main.root_output_field.view.*

class RootsOutputAdapter(private val results: DoubleArray) :
    RecyclerView.Adapter<RootsOutputAdapter.ResultsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultsViewHolder =
        ResultsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.equation_line,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ResultsViewHolder, position: Int) {
        val root =
            LayoutInflater.from(holder.itemView.context).inflate(R.layout.root_output_field, null)
        root.variable_label.text =
            HtmlCompat.fromHtml(
                holder.layout.context.getString(
                    R.string.variable,
                    position + 1
                ), HtmlCompat.FROM_HTML_MODE_LEGACY
            )
        root.variable_coef.text = this.results[position].toString()
        holder.layout.addView(root)
    }

    override fun getItemCount(): Int = this.results.size

    class ResultsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val layout: LinearLayout = itemView as LinearLayout
    }
}
