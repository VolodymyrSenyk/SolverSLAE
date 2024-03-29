package com.senyk.solvers.slae.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import com.senyk.solvers.slae.R

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = findNavController(this, R.id.host_fragment)
    }

    override fun onBackPressed() {
        when (navController.currentDestination?.id) {
            R.id.resultsScreenFragment -> navController.navigate(R.id.inputScreenFragment)
            R.id.inputScreenFragment -> finish()
            else -> super.onBackPressed()
        }
    }
}
