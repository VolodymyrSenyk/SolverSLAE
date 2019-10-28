package com.senyk.solvers.slae.view.screens.results.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.senyk.solvers.slae.R
import com.senyk.solvers.slae.view.screens.results.adapters.ResultsInputDataMatrixAdapter
import com.senyk.solvers.slae.view.screens.results.adapters.RootsCheckAdapter
import com.senyk.solvers.slae.view.screens.results.adapters.RootsOutputAdapter
import com.senyk.solvers.slae.view_model.ResultsScreenViewModel
import com.senyk.solvers.slae.view_model.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_results_screen.*

class ResultsScreenFragment : Fragment() {
    private lateinit var viewModel: ResultsScreenViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_results_screen, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel =
            ViewModelProviders.of(this, ViewModelFactory()).get(ResultsScreenViewModel::class.java)
        viewModel.message.observe(this, Observer<String> { message ->
            if (!message.isNullOrEmpty()) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
            }
        })

        setHasOptionsMenu(true)
        setResults(arguments!!)
    }

    @Suppress("UNCHECKED_CAST")
    private fun setResults(solverData: Bundle) {
        method.text = context?.getString(R.string.method, solverData.getString(METHOD))

        matrix.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireActivity())
            adapter =
                ResultsInputDataMatrixAdapter(
                    solverData.getSerializable(INPUT_DATA) as Array<DoubleArray>
                )
        }

        results.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = RootsOutputAdapter(
                solverData.getDoubleArray(RESULT_DATA) as DoubleArray
            )
        }

        checking.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = RootsCheckAdapter(
                solverData.getSerializable(INPUT_DATA) as Array<DoubleArray>,
                solverData.getDoubleArray(RESULT_DATA) as DoubleArray
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.results_screen_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_save_report) {
            viewModel.saveReport(requireContext(), arguments?.getString(REPORT_DATA)!!)
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val METHOD = "selected method"
        const val INPUT_DATA = "input data matrix"
        const val RESULT_DATA = "results"
        const val REPORT_DATA = "report"
    }
}
