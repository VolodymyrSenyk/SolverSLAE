package com.senyk.solvers.slae.view.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.senyk.solvers.slae.R

class MatrixAdapter : RecyclerView.Adapter<MatrixAdapter.EquationViewHolder>() {
    private var items: Array<DoubleArray> = Array(0) {DoubleArray(0)}

    fun setData(newItems: Array<DoubleArray>) {
        this.items = newItems
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EquationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.equation, parent, false)
        return EquationViewHolder(view)
    }

    override fun onBindViewHolder(holder: EquationViewHolder, position: Int) {
        val item = this.items[position]
    }

    override fun getItemCount(): Int {
        return this.items.size
    }

    class EquationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
        }
    }
}
