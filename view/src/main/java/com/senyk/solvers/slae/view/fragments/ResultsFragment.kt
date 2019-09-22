package com.senyk.solvers.slae.view.fragments

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.senyk.solvers.slae.R
import com.senyk.solvers.slae.view.adapters.ResultsAdapter
import com.senyk.solvers.slae.view.adapters.ResultsCheckAdapter
import com.senyk.solvers.slae.view.adapters.ResultsMatrixAdapter
import kotlinx.android.synthetic.main.fragment_results.*
import kotlinx.android.synthetic.main.result.view.*

class ResultsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
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
        matrix.adapter =
            ResultsMatrixAdapter(solverData.getSerializable(INPUT_DATA) as Array<DoubleArray>)

        val answersList = results
        answersList.setHasFixedSize(true)
        val layoutManagerForResults = LinearLayoutManager(requireActivity().applicationContext)
        answersList.layoutManager = layoutManagerForResults
        answersList.adapter = ResultsAdapter(solverData.getDoubleArray(RESULT_DATA) as DoubleArray)

        val checkMatrix = checking
        checkMatrix.setHasFixedSize(true)
        val layoutManagerForChecking = LinearLayoutManager(requireActivity().applicationContext)
        checkMatrix.layoutManager = layoutManagerForChecking
        checkMatrix.adapter = ResultsCheckAdapter(
            solverData.getSerializable(INPUT_DATA) as Array<DoubleArray>,
            solverData.getDoubleArray(RESULT_DATA) as DoubleArray
        )
    }

    companion object {
        const val METHOD = "selected method"
        const val INPUT_DATA = "input data matrix"
        const val RESULT_DATA = "results"
    }
}
