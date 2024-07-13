package com.example.careerlink.data.repository

import com.example.careerlink.data.models.JobListData
import com.example.careerlink.data.remote.ApiService

class JobRepository( private val apiService: ApiService) {
    suspend fun getJobs(): JobListData {
        return apiService.getJobs()
    }
}