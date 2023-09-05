package com.example.nydoehighschooldirectory.repository

import com.example.nydoehighschooldirectory.model.School
import com.example.nydoehighschooldirectory.model.SchoolDetails
import com.example.nydoehighschooldirectory.service.SchoolService
import javax.inject.Inject

class SchoolRepository @Inject constructor(private val schoolService: SchoolService) {

    suspend fun getSchoolList(): List<School> {
        return schoolService.getSchoolList()
    }

    suspend fun getSchoolDetails(dbn: String): List<SchoolDetails> {
        return schoolService.getSchoolDetails(dbn)
    }
}