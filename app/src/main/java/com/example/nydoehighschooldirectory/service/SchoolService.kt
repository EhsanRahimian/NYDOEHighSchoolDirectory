package com.example.nydoehighschooldirectory.service

import com.example.nydoehighschooldirectory.model.School
import com.example.nydoehighschooldirectory.model.SchoolDetails
import retrofit2.http.GET
import retrofit2.http.Query

interface SchoolService {
    @GET("resource/s3k6-pzi2.json")
    suspend fun getSchoolList(): List<School>

    @GET("resource/f9bf-2cp4.json")
    suspend fun getSchoolDetails(@Query("dbn") dbn: String): List<SchoolDetails>

}