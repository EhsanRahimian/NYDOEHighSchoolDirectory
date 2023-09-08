package com.example.nydoehighschooldirectory.ui

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.nydoehighschooldirectory.R
import com.example.nydoehighschooldirectory.databinding.ActivitySchoolDetailsBinding
import com.example.nydoehighschooldirectory.util.DataState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SchoolDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySchoolDetailsBinding
    private val viewModel: SchoolDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySchoolDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.title = "SAT Scores"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val selectedDbn = intent.getStringExtra("dbn")

        // Fetch school details when the activity is created
        lifecycleScope.launch {
            selectedDbn?.let { dbn ->
                // Trigger fetching of school details with the selected dbn
                viewModel.getSchoolDetails(dbn)
            }
        }
        observeSchoolDetailsState()
    }

    override fun onResume() {
        super.onResume()
        observeSchoolDetailsState()
    }

    private fun observeSchoolDetailsState() {
        lifecycleScope.launch {
            viewModel.schoolDetailsStateFlow.collect { state ->
                when (state) {
                    is DataState.Success -> {
                        val schoolDetailsList = state.data

                        val schoolDetails = schoolDetailsList[0]
                        binding.schoolNameTextView.text = schoolDetails.schoolName
                        binding.numSatTestTakers.text = getString(
                            R.string.sat_test_taker_text,
                            schoolDetails.numOfSatTestTakers
                        )
                        binding.satReadingTextView.text = getString(
                            R.string.sat_reading_text,
                            schoolDetails.satCriticalReadingAvgScore
                        )
                        binding.satMathTextView.text =
                            getString(R.string.sat_math_text, schoolDetails.satMathAvgScore)
                        binding.satWritingTextView.text =
                            getString(R.string.sat_writing_text, schoolDetails.satWritingAvgScore)
                        binding.progressBar.visibility = View.GONE
                    }

                    is DataState.Error -> {
                        // Handle error state
                        val errorMessage = state.exception.message ?: "An error occurred."
                        showErrorDialog(errorMessage)
                        binding.progressBar.visibility = View.GONE
                    }

                    is DataState.Loading -> {
                        // Handle loading state, show progress indicator, etc.
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is DataState.Empty -> {
                        binding.progressBar.visibility = View.GONE
                        showNoDataFoundDialog()
                    }
                }
            }
        }
    }

    @Suppress("DEPRECATION")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showNoDataFoundDialog() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("No Data Found")
        alertDialogBuilder.setMessage("No data was found for the selected school.")
        alertDialogBuilder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
            finish() // Close the activity or take any other appropriate action
        }
        alertDialogBuilder.setCancelable(false)
        alertDialogBuilder.create().show()
    }

    private fun showErrorDialog(errorMessage: String) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Error")
        alertDialogBuilder.setMessage(errorMessage)
        alertDialogBuilder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
            finish()
        }
        alertDialogBuilder.setCancelable(false)
        alertDialogBuilder.create().show()
    }

}
