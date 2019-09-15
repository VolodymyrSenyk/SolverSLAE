package com.senyk.solvers.slae.view.fragments

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.senyk.solvers.slae.R
import com.senyk.solvers.slae.view.adapters.ResultsMatrixAdapter
import kotlinx.android.synthetic.main.fragment_results.*
import kotlinx.android.synthetic.main.result.view.*

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

        val answers = solverData.getDoubleArray(RESULT_DATA)
        for (i in answers!!.indices) {
            val variable = LayoutInflater.from(requireActivity().applicationContext).inflate(R.layout.result, null)
            variable.result.text = Html.fromHtml(requireActivity().applicationContext.getString(
                R.string.var_result,i + 1, answers[i]))
            results.addView(variable)
        }
    }

    companion object {
        const val METHOD = "selected method"
        const val INPUT_DATA = "input data matrix"
        const val RESULT_DATA = "results"
    }
}
