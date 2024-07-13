package com.example.careerlink.data.remote

import com.example.careerlink.data.models.JobListData
import retrofit2.http.GET

interface ApiService {
    @GET("/common/jobs?pages=1")
    suspend fun getJobs(): JobListData
}