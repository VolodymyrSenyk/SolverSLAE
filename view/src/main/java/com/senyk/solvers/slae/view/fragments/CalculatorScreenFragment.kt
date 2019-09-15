package com.senyk.solvers.slae.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.senyk.solvers.slae.R
import com.senyk.solvers.slae.view.adapters.MatrixAdapter
import com.senyk.solvers.slae.view.helpers.SpinnerSelectListener
import com.senyk.solvers.slae.view_model.CalculatorViewModel
import com.senyk.solvers.slae.view_model.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_calculator_screen.*

class CalculatorScreenFragment : Fragment(){
    private lateinit var model: CalculatorViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_calculator_screen, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model = ViewModelProviders.of(this, ViewModelFactory()).get(CalculatorViewModel::class.java)

        val dataList = matrix
        dataList.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(requireActivity().applicationContext)
        dataList.layoutManager = layoutManager

        variables_count.onItemSelectedListener = object : SpinnerSelectListener() {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                model.matrixSizeChanged(variables_count.selectedItem.toString().toInt())
            }
        }

        calculate_button.setOnClickListener {
            model.solve(method_spinner.selectedItemPosition)
        }

        model.matrix.observe(this, Observer<Array<DoubleArray>> {
            dataList.adapter = MatrixAdapter(it, model)
        })

        model.results.observe(this, Observer<DoubleArray> {
            val solverData = Bundle()
            solverData.putString(ResultsFragment.METHOD, method_spinner.selectedItem.toString())
            solverData.putSerializable(ResultsFragment.INPUT_DATA, model.getInputDataMatrix())
            solverData.putDoubleArray(ResultsFragment.RESULT_DATA, it)
            findNavController().navigate(R.id.resultsFragment, solverData)
        })
    }
}
