package com.senyk.solvers.slae.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.senyk.solvers.slae.R
import com.senyk.solvers.slae.view.adapters.ResultsAnswersAdapter
import com.senyk.solvers.slae.view.adapters.ResultsMatrixAdapter
import kotlinx.android.synthetic.main.fragment_results.*

class ResultsFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_results, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setResults(arguments!!)
    }

    @Suppress("UNCHECKED_CAST")
    private fun setResults(solverData: Bundle) {
        val method = method
        method.text = context?.getString(R.string.method, solverData.getString(METHOD))

        val dataList = matrix
        dataList.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(requireActivity().applicationContext)
        dataList.layoutManager = layoutManager
        matrix.adapter = ResultsMatrixAdapter(solverData.getSerializable(INPUT_DATA) as Array<DoubleArray>)

        val answersList = results
        answersList.setHasFixedSize(true)
        val layoutManagerForAnswersList = LinearLayoutManager(requireActivity().applicationContext)
        answersList.layoutManager = layoutManagerForAnswersList
        val results = solverData.getDoubleArray(RESULT_DATA)
        var countOfLines = results!!.size / NUM_OF_RESULTS_IN_STRING
        if (results.size % NUM_OF_RESULTS_IN_STRING > 0) {
            countOfLines++
        }
        val resultsForList = Array(countOfLines) { DoubleArray(NUM_OF_RESULTS_IN_STRING) }
        for (i in results.indices) {
            resultsForList[i / NUM_OF_RESULTS_IN_STRING][i % NUM_OF_RESULTS_IN_STRING] = results[i]
        }
        answersList.adapter = ResultsAnswersAdapter(resultsForList)
    }

    companion object {
        const val METHOD = "selected method"
        const val INPUT_DATA = "input data matrix"
        const val RESULT_DATA = "results"

        const val NUM_OF_RESULTS_IN_STRING = 3
    }
}
