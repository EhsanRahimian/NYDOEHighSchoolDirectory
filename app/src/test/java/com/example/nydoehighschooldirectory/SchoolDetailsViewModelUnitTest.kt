package com.example.nydoehighschooldirectory

import com.example.nydoehighschooldirectory.model.SchoolDetails
import com.example.nydoehighschooldirectory.repository.SchoolRepository
import com.example.nydoehighschooldirectory.ui.SchoolDetailsViewModel
import com.example.nydoehighschooldirectory.util.DataState
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
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
class SchoolDetailsViewModelUnitTest {
    private val repository = mockk<SchoolRepository>()
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

    private fun createViewModel(): SchoolDetailsViewModel {
        return SchoolDetailsViewModel(repository)
    }

    private fun createSchoolDetails(): SchoolDetails {

        return SchoolDetails(
                 dbn= "08X408",
                schoolName = "Android School",
                numOfSatTestTakers = "100",
                satCriticalReadingAvgScore = "150",
                satMathAvgScore = "150",
                satWritingAvgScore = "150"
        )
    }
    private val  schoolDetailsList = listOf(createSchoolDetails())

    @Test
    fun `getSchoolDetails success`() {
        val viewModel = createViewModel()
        val dbn = "01ABC"
        val schoolDetails = createSchoolDetails()

        // Mock the repository to return the schoolDetails
        coEvery { repository.getSchoolDetails(dbn) } returns listOf(schoolDetails)
        viewModel.getSchoolDetails(dbn)
        // Assert the emitted states
        val state = viewModel.schoolDetailsStateFlow.value
        assert(state is DataState.Success)
        assert((state as DataState.Success).data == listOf(schoolDetails))

        // Verify interactions with the repository
        coVerify(exactly = 1) { repository.getSchoolDetails(dbn) }
        confirmVerified(repository)
    }

    @Test
    fun `getSchoolDetails empty`() {
        val viewModel = createViewModel()
        val dbn = "01ABC"

        // Mock the repository to return an empty list
        coEvery { repository.getSchoolDetails(dbn) } returns emptyList()

        viewModel.getSchoolDetails(dbn)

        // Assert the emitted states
        val state = viewModel.schoolDetailsStateFlow.value
        assert(state is DataState.Empty)

        // Verify interactions with the repository
        coVerify(exactly = 1) { repository.getSchoolDetails(dbn) }
        confirmVerified(repository)
    }

    @Test
    fun `getSchoolDetails error`() {
        val viewModel = createViewModel()
        val dbn = "01ABC"
        val errorMessage = "Error fetching school details"

        // Mock the repository to throw an exception
        coEvery { repository.getSchoolDetails(dbn) } throws Exception(errorMessage)

        viewModel.getSchoolDetails(dbn)

        // Assert the emitted states
        val state = viewModel.schoolDetailsStateFlow.value
        assert(state is DataState.Error)
        assert((state as DataState.Error).exception.message == errorMessage)

        // Verify interactions with the repository
        coVerify(exactly = 1) { repository.getSchoolDetails(dbn) }
        confirmVerified(repository)
    }

}