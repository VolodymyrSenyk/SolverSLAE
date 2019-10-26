package com.senyk.solvers.slae.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.senyk.solvers.slae.R
import com.senyk.solvers.slae.view.adapters.resultsscreen.ResultsAdapter
import com.senyk.solvers.slae.view.adapters.resultsscreen.ResultsCheckAdapter
import com.senyk.solvers.slae.view.adapters.resultsscreen.ResultsInputDataMatrixAdapter
import kotlinx.android.synthetic.main.fragment_results_screen.*

class ResultsScreenFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_results_screen, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
            adapter = ResultsAdapter(solverData.getDoubleArray(RESULT_DATA) as DoubleArray)
        }

        checking.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = ResultsCheckAdapter(
                solverData.getSerializable(INPUT_DATA) as Array<DoubleArray>,
                solverData.getDoubleArray(RESULT_DATA) as DoubleArray
            )
        }
    }

    companion object {
        const val METHOD = "selected method"
        const val INPUT_DATA = "input data matrix"
        const val RESULT_DATA = "results"
        const val REPORT_DATA = "report"
    }
}
