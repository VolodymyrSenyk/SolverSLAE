package com.senyk.solvers.slae.view.screens.input.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.senyk.solvers.slae.R
import com.senyk.solvers.slae.view.helpers.SpinnerSelectListener
import com.senyk.solvers.slae.view.screens.input.adapters.InputDataMatrixAdapter
import com.senyk.solvers.slae.view.screens.results.fragments.ResultsScreenFragment
import com.senyk.solvers.slae.view_model.InputScreenViewModel
import com.senyk.solvers.slae.view_model.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_input_screen.*

class InputScreenFragment : Fragment() {
    private lateinit var viewModel: InputScreenViewModel
    private lateinit var inputDataMatrixAdapter: InputDataMatrixAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_input_screen, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel =
            ViewModelProviders.of(this, ViewModelFactory()).get(InputScreenViewModel::class.java)
        setAdapters(); setViews(); addObservers()
    }

    private fun setAdapters() {
        val coefficientChanged: (Int, Int, Double) -> Unit =
            { position, variableIndex, variableValue ->
                viewModel.coefficientChanged(position, variableIndex, variableValue)
            }
        inputDataMatrixAdapter = InputDataMatrixAdapter(coefficientChanged)
    }

    private fun setViews() {
        matrix.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = inputDataMatrixAdapter
        }

        variables_count_spinner.onItemSelectedListener = object : SpinnerSelectListener() {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) = viewModel.matrixSizeChanged(variables_count_spinner.selectedItem.toString().toInt())
        }

        calculate_button.setOnClickListener { viewModel.solve(method_spinner.selectedItemPosition) }
    }

    private fun addObservers() {
        viewModel.matrix.observe(this, Observer<Array<DoubleArray>> { inputDataMatrix ->
            inputDataMatrixAdapter.setNewData(inputDataMatrix)
        })

        viewModel.isSolved.observe(this, Observer<Boolean> { isSolved ->
            if (isSolved) {
                findNavController().navigate(
                    R.id.resultsScreenFragment,
                    bundleOf(
                        ResultsScreenFragment.METHOD to method_spinner.selectedItem.toString(),
                        ResultsScreenFragment.INPUT_DATA to viewModel.matrix.value,
                        ResultsScreenFragment.RESULT_DATA to viewModel.results,
                        ResultsScreenFragment.REPORT_DATA to viewModel.algorithmReport
                    )
                )
            }
        })

        viewModel.message.observe(this, Observer<String> { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
        })

    }
}
