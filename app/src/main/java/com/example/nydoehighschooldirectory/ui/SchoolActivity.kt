package com.example.nydoehighschooldirectory.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.nydoehighschooldirectory.R
import com.example.nydoehighschooldirectory.databinding.ActivitySchoolBinding
import com.example.nydoehighschooldirectory.model.School
import com.example.nydoehighschooldirectory.util.DataState
import com.example.nydoehighschooldirectory.util.ErrorMessageManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SchoolActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySchoolBinding
    private val viewModel: SchoolViewModel by viewModels()
    private val schoolAdapter = SchoolAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySchoolBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setSupportActionBar(binding.toolbar)

        binding.contentLayout.recyclerView.apply {
            visibility = View.VISIBLE
            adapter = schoolAdapter

            schoolAdapter.setOnItemClickListener(object : SchoolAdapter.OnItemClickListener {
                override fun onItemClick(school: School) {
                    val selectedDbn = school.dbn

                    viewModel.setSelectedDbn(school.dbn)

                    val intent = Intent(this@SchoolActivity, SchoolDetailsActivity::class.java)
                    intent.putExtra("dbn", selectedDbn) // Pass the selected dbn
                    startActivity(intent)
                }
            })
        }
        viewModel.fetchSchoolList()

        lifecycleScope.launch {
            viewModel.schoolListState.collect { state ->
                when (state) {
                    is DataState.Success -> {
                        binding.contentLayout.recyclerView.visibility = View.VISIBLE
                        schoolAdapter.submitList(state.data)
                        updateUIState(
                            isLoading = false,
                            isError = false,
                            isEmpty = false,
                        )
                    }

                    is DataState.Error -> {
                        updateUIState(isLoading = false, isError = true, isEmpty = false)
                        val errorMessage = ErrorMessageManager.getErrorMessage(state.exception)
                        binding.errorLayout.errorTextView.text = errorMessage
                        binding.contentLayout.recyclerView.visibility = View.GONE
                    }

                    DataState.Empty -> {
                        updateUIState(isLoading = false, isError = false, isEmpty = true)
                        binding.emptyLayout.emptyTextView.text = getString(R.string.no_data_is_available)
                    }

                    DataState.Loading -> {
                        updateUIState(isLoading = true, isError = false, isEmpty = false)
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchSchoolList()
    }

    private fun updateUIState(isLoading: Boolean, isError: Boolean, isEmpty: Boolean) {
        binding.loadingLayout.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.errorLayout.errorTextView.visibility = if (isError) View.VISIBLE else View.GONE
        binding.errorLayout.circleIv.visibility = if (isError) View.VISIBLE else View.GONE
        binding.emptyLayout.emptyTextView.visibility = if (isEmpty) View.VISIBLE else View.GONE

    }
}