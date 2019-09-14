package com.senyk.solvers.slae.view.fragments

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.senyk.solvers.slae.R
import com.senyk.solvers.slae.view.adapters.MatrixAdapter
import com.senyk.solvers.slae.view_model.SolverSLAEViewModel
import com.senyk.solvers.slae.view_model.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_calculator_screen.*

class CalculatorScreenFragment : BaseFragment() {
    private lateinit var model: SolverSLAEViewModel

    override fun initViewModel() {
        model = ViewModelProviders.of(requireActivity(), ViewModelFactory())
            .get(SolverSLAEViewModel::class.java)
    }

    override fun getLayoutResource(): Int = R.layout.fragment_calculator_screen

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        val dataList = matrix
        dataList.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(requireActivity().applicationContext)
        dataList.layoutManager = layoutManager

        model.matrixSize.observe(this, Observer<Pair<Int, Int>> {
            dataList.adapter = MatrixAdapter(it.first, it.second + 1, model)
        })

        equations_count.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                return
            }

            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                counterSpinnerClicked()
            }
        }

        variables_count.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                return
            }

            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                counterSpinnerClicked()
            }
        }

        calculate_button.setOnClickListener {
            findNavController().navigate(R.id.resultsFragment)
            model.solve(method_spinner.selectedItemPosition)
        }
    }

    private fun counterSpinnerClicked() {
        model.matrixSizeChanged(
            equations_count.selectedItem.toString().toInt(),
            variables_count.selectedItem.toString().toInt()
        )
    }
}
