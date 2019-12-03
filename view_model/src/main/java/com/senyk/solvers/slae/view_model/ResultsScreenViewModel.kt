package com.senyk.solvers.slae.view_model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.senyk.solvers.slae.view_model.helper.SingleEventLiveData
import io.github.lucasfsc.html2pdf.Html2Pdf
import java.io.File
import java.util.*

class ResultsScreenViewModel : ViewModel() {
    private var _message = SingleEventLiveData<String>()
    val message: LiveData<String>
        get() = _message

    fun saveReport(context: Context, report: String) {
        val fileName = generateReportFileNameByDate(context)
        val dir = File(REPORTS_DIRECTORY_PATH)
        dir.mkdirs()
        val myFile = File(dir, fileName)
        myFile.createNewFile()
        val converter = Html2Pdf.Companion.Builder()
            .context(context)
            .html(report)
            .file(myFile)
            .build()
        converter.convertToPdf()
        _message.setValue(context.getString(R.string.report_saved, myFile.absolutePath))
    }

    private fun generateReportFileNameByDate(context: Context): String {
        val date = Calendar.getInstance()
        return context.getString(
            R.string.report_filename,
            date.get(Calendar.DAY_OF_MONTH),
            (date.get(Calendar.MONTH) + 1),
            date.get(Calendar.YEAR),
            date.get(Calendar.HOUR_OF_DAY),
            date.get(Calendar.MINUTE)
        )
    }

    companion object {
        const val REPORTS_DIRECTORY_PATH = "storage/emulated/0/SolverSLAE"
    }

}
