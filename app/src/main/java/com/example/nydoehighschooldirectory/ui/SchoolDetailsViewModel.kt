package com.example.nydoehighschooldirectory.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nydoehighschooldirectory.model.SchoolDetails
import com.example.nydoehighschooldirectory.repository.SchoolRepository
import com.example.nydoehighschooldirectory.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SchoolDetailsViewModel @Inject constructor(
    private val schoolRepository: SchoolRepository
) : ViewModel() {

    private val _schoolDetailsStateFlow = MutableStateFlow<DataState<List<SchoolDetails>>>(DataState.Empty)
    val schoolDetailsStateFlow: StateFlow<DataState<List<SchoolDetails>>> = _schoolDetailsStateFlow

    fun getSchoolDetails(dbn: String) {
        viewModelScope.launch {
            try {
                // Emit loading state while fetching data
                _schoolDetailsStateFlow.value = DataState.Loading

                // Fetch school details from the repository using the provided dbn
                val schoolDetails = schoolRepository.getSchoolDetails(dbn)

                if (schoolDetails.isNotEmpty()) {
                    // If the details are successfully fetched, emit success state
                    _schoolDetailsStateFlow.value = DataState.Success(schoolDetails)
                } else {
                    // Handle the case where no data is found for the given dbn
                    _schoolDetailsStateFlow.value = DataState.Empty
                }
            } catch (e: Exception) {
                // Handle errors and emit error state if there's an exception
                _schoolDetailsStateFlow.value = DataState.Error(e)
            }
        }
    }

}