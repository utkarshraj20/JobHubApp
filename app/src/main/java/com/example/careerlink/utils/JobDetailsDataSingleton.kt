package com.example.careerlink.utils

import com.example.careerlink.data.models.JobData

object JobDetailsDataSingleton {
    private lateinit var jobDetailsData: JobData
    fun setJobDetailsData(jobDetailsData: JobData){
        JobDetailsDataSingleton.jobDetailsData = jobDetailsData
    }
    fun getJobDetailsData(): JobData {
        return jobDetailsData
    }
}