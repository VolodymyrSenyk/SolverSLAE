package com.senyk.solvers.slae.view.helpers

import android.widget.AdapterView

abstract class SpinnerSelectListener : AdapterView.OnItemSelectedListener {
    override fun onNothingSelected(parent: AdapterView<*>?) {
        return
    }
}
