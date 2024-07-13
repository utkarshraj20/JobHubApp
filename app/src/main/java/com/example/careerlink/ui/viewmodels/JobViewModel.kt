package com.example.careerlink.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.careerlink.data.models.JobData
import com.example.careerlink.data.models.UiState
import com.example.careerlink.data.repository.JobRepository
import com.example.careerlink.data.remote.RetrofitClient
import kotlinx.coroutines.launch

class JobViewModel : ViewModel() {
    private val _jobList = MutableLiveData<UiState>(UiState.Loading)
    val jobList: LiveData<UiState> get() = _jobList

    private val jobRepository = JobRepository(RetrofitClient.instance)

    fun getJobList( isBookmark: Boolean , list: MutableList<JobData> ){
        _jobList.value = UiState.Loading
        if (isBookmark){
            if( list.isEmpty() ){
                _jobList.value = UiState.Error("No Saved Job")
            }
            else{
                _jobList.value = UiState.Success(list)
            }
        }
        else{
            viewModelScope.launch {
                try {
                    val result = jobRepository.getJobs().result
                    _jobList.value = UiState.Success(result)
                    if (result.isEmpty()) {
                        _jobList.value = UiState.Error("No Job Found")
                    } else {
                        _jobList.value = UiState.Success(result)
                    }
                } catch (e: Exception){
                    _jobList.value = UiState.Error(e.message.toString())
                }
            }
        }
    }

}