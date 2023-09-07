package com.example.nydoehighschooldirectory

import com.example.nydoehighschooldirectory.model.School
import com.example.nydoehighschooldirectory.repository.SchoolRepository
import com.example.nydoehighschooldirectory.ui.SchoolViewModel
import com.example.nydoehighschooldirectory.util.DataState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.time.ExperimentalTime



@ExperimentalTime
@ExperimentalCoroutinesApi
class SchoolViewModelUnitTest {
    private val repository = mockk<SchoolRepository>()
    // Test dispatcher for coroutines
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    private fun createViewModel(): SchoolViewModel {
        return SchoolViewModel(repository)
    }

    private fun createSchool(): School {
        return School(
            dbn = "01ABC",
            schoolName = "Android School",
            city = "MANHATTAN",
            zip = "10003"
        )
    }

    @Test
    fun `setSelectedDbn should update selectedDbn`() {
        val viewModel = createViewModel()
        val dbn = "01ABC"
        viewModel.setSelectedDbn(dbn)
        assert(viewModel.selectedDbn.value == dbn)
    }

    @Test
    fun `fetchSchoolList success`() {
        val viewModel = createViewModel()
        val schoolList = listOf(createSchool())
        coEvery { repository.getSchoolList() } returns schoolList
        viewModel.fetchSchoolList()
        assert(viewModel.schoolListState.value is DataState.Success)
        assert(viewModel.schoolListState.value is DataState.Success)
        val successState = viewModel.schoolListState.value as DataState.Success
        assert(successState.data == schoolList)

    }

    @Test
    fun `fetchSchoolList error`() {
        val viewModel = createViewModel()
        val errorMessage = "Error fetching schools"
        coEvery { repository.getSchoolList() } throws Exception(errorMessage)
        viewModel.fetchSchoolList()
        assert(viewModel.schoolListState.value is DataState.Error)
    }
}