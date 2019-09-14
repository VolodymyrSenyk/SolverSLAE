package com.senyk.solvers.slae.view.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.senyk.solvers.slae.R

class ResultsAdapter : RecyclerView.Adapter<ResultsAdapter.ResultViewHolder>() {
    private var items: DoubleArray = DoubleArray(0)

    fun setData(newItems: DoubleArray) {
        this.items = newItems
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.equation, parent, false)
        return ResultViewHolder(view)
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        val item = this.items[position]
    }

    override fun getItemCount(): Int {
        return this.items.size
    }

    class ResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
        }
    }
}
