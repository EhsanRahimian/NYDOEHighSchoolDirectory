package com.example.nydoehighschooldirectory.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nydoehighschooldirectory.model.School
import com.example.nydoehighschooldirectory.repository.SchoolRepository
import com.example.nydoehighschooldirectory.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SchoolViewModel @Inject constructor(
    private val schoolRepository: SchoolRepository
) : ViewModel() {

    private val _schoolListState = MutableStateFlow<DataState<List<School>>>(DataState.Empty)
    val schoolListState: StateFlow<DataState<List<School>>> = _schoolListState

    private val _selectedDbn = MutableStateFlow("")
    val selectedDbn: StateFlow<String> = _selectedDbn

    fun setSelectedDbn(dbn: String) {
        // Set the selected dbn here and trigger fetching of school details
        _selectedDbn.value = dbn
    }

    // Function to fetch school list
    fun fetchSchoolList() = viewModelScope.launch {
        _schoolListState.value = DataState.Loading
        try {
            val schoolList = schoolRepository.getSchoolList()
            _schoolListState.value = DataState.Success(schoolList)
        } catch (e: Exception) {
            _schoolListState.value = DataState.Error(e)
        }
    }
}