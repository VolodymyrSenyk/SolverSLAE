package com.senyk.solvers.slae.view.screens.results.fragments

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
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
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(
                    arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    PERMISSION_REQUEST
                )
            } else {
                viewModel.saveReport(requireContext(), arguments?.getString(REPORT_DATA)!!)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_REQUEST) {
            permissionsAnalise(permissions, grantResults)
        }
    }

    private fun permissionsAnalise(permissions: Array<String>, grantResults: IntArray) {
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            viewModel.saveReport(requireContext(), arguments?.getString(REPORT_DATA)!!)
        } else if (!ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                permissions[0]
            )
        ) {
            makeSnackbar().show()
        } else {
            Toast.makeText(
                requireContext(),
                getString(R.string.permission_not_granted),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun makeSnackbar(): Snackbar {
        val toSettings = View.OnClickListener {
            val intent = Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts("package", requireActivity().packageName, null)
            )
            startActivity(intent)
        }
        val snackbar = Snackbar.make(
            checking,
            getString(R.string.permission_not_granted),
            SNACKBAR_DURATION
        ).setAction(R.string.to_settings, toSettings)
        val textView =
            snackbar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        textView.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.colorPrimaryDark
            )
        )
        return snackbar
    }

    companion object {
        const val METHOD = "selected method"
        const val INPUT_DATA = "input data matrix"
        const val RESULT_DATA = "results"
        const val REPORT_DATA = "report"
        const val PERMISSION_REQUEST = 1
        const val SNACKBAR_DURATION = 10000
    }
}
