package com.senyk.solvers.slae.view.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.senyk.solvers.slae.R
import com.senyk.solvers.slae.view_model.SolverSLAEViewModel
import com.senyk.solvers.slae.view_model.ViewModelFactory

class CalculatorScreenFragment : BaseFragment() {
    private lateinit var model: SolverSLAEViewModel

    override fun initViewModel() {
        model = ViewModelProviders.of(this, ViewModelFactory()).get(SolverSLAEViewModel::class.java)
    }

    override fun getLayoutResource(): Int = R.layout.fragment_calculator_screen

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
    }
}
