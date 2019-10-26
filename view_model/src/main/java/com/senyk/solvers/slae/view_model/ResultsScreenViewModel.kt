package com.senyk.solvers.slae.view_model

import android.content.Context
import android.os.Environment
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
        val fileName = generateReportFileNameByDate()
        val myFile = File(Environment.getExternalStorageDirectory().toString() + "/SolverSLAE/Reports/" + fileName)
        myFile.createNewFile()
        val converter = Html2Pdf.Companion.Builder()
            .context(context)
            .html(report)
            .file(myFile)
            .build()
        converter.convertToPdf()
        _message.setValue("Отчёт сохранён в ${myFile.absolutePath}")
    }

    private fun generateReportFileNameByDate(): String {
        val date = Calendar.getInstance()
        return "Report for " +
                date.get(Calendar.DAY_OF_MONTH) + "." +
                (date.get(Calendar.MONTH) + 1) + "." +
                date.get(Calendar.YEAR) + " " +
                date.get(Calendar.HOUR_OF_DAY) + ":" +
                date.get(Calendar.MINUTE) +
                ".pdf"
    }

}
