package com.senyk.solvers.slae.view.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.senyk.solvers.slae.R
import com.senyk.solvers.slae.view.adapters.ResultsAdapter
import com.senyk.solvers.slae.view_model.SolverSLAEViewModel
import com.senyk.solvers.slae.view_model.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_results.*

class ResultsFragment : BaseFragment() {
    private lateinit var model: SolverSLAEViewModel

    override fun initViewModel() {
        model = ViewModelProviders.of(requireActivity(), ViewModelFactory()).get(SolverSLAEViewModel::class.java)
    }

    override fun getLayoutResource(): Int = R.layout.fragment_results

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        val dataList = results
        dataList.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(requireActivity().applicationContext)
        dataList.layoutManager = layoutManager

        model.results.observe(this, Observer<DoubleArray> {
            dataList.adapter = ResultsAdapter(it)
        })
    }
}
