package com.example.careerlink.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.careerlink.R
import com.example.careerlink.utils.BookmarkManager
import com.example.careerlink.ui.adaptors.JobListAdaptor
import com.example.careerlink.ui.activity.MainActivity
import com.example.careerlink.databinding.JobsFragmentBinding
import com.example.careerlink.data.models.JobData
import com.example.careerlink.utils.JobDetailsDataSingleton
import com.example.careerlink.data.models.UiState
import com.example.careerlink.ui.viewmodels.JobViewModel

class FragmentJobs : BaseFragment<JobsFragmentBinding>() {
    private val jobViewModel = JobViewModel()
    private lateinit var bookmarkManager: BookmarkManager
    private var jobList = mutableListOf<JobData>()
    private var isBookmarkList: Boolean = false

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): JobsFragmentBinding? {
        bookmarkManager = BookmarkManager(requireContext())
        arguments?.let {
            isBookmarkList = it.getBoolean(ARG_IS_BOOKMARK_LIST)
        }
        jobList.clear()
        jobList.addAll(bookmarkManager.getBookmarks())
        jobViewModel.getJobList(isBookmarkList , jobList)
        (activity as MainActivity).showBottomNav()
        return JobsFragmentBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        if(isBookmarkList){
            binding.tvJobs.setText(R.string.your_bookmarks)
        }
        else{
            binding.tvJobs.setText(R.string.jobs)
        }

        jobViewModel.jobList.observe(viewLifecycleOwner){ state ->
            when(state){
                is UiState.Loading -> showLoading()
                is UiState.Success -> showData( (state.data) as MutableList<JobData> )
                is UiState.Error -> showError(state.message)
            }
        }
    }

    fun showLoading(){
        showShimmer()
    }

    private fun showShimmer() {
        binding.tvJobs.visibility = View.GONE
        binding.rvJobList.visibility = View.GONE
        binding.shimmerView.visibility = View.VISIBLE
    }

    private fun hideShimmer() {
        binding.shimmerView.visibility = View.GONE
        binding.tvJobs.visibility = View.VISIBLE
        binding.rvJobList.visibility = View.VISIBLE
    }

    fun showData( jobDataList: MutableList<JobData>){
        hideShimmer()
        binding.tvErrorCase.visibility = View.GONE
        binding.rvJobList.visibility = View.VISIBLE
        val jobList = (jobDataList.filter { it.title != null }.toMutableList())
        binding.rvJobList.layoutManager = LinearLayoutManager(activity)
        for( job in jobList ){
            job.isBookmarked = true
            if( !bookmarkManager.isBookmark(job) ){
                job.isBookmarked = false
            }
        }
        val adaptor = JobListAdaptor(
            jobList,
            onItemClick = { itemClicked ->
                onItemClick(itemClicked, jobList)
            },
            onBookmarkClick = { itemClicked ->
                onBookmarkClick(itemClicked, jobList)
            }
        )
        binding.rvJobList.adapter = adaptor
    }

    fun onItemClick(job: JobData, jobList: MutableList<JobData>){
        JobDetailsDataSingleton.setJobDetailsData(job)
        (activity as MainActivity).hideBottomNav()
        (activity as MainActivity).replaceFragment(FragmentJobDetails.newInstance())
    }

    fun onBookmarkClick(itemClicked: JobData, jobList: MutableList<JobData>){
        val index = jobList.indexOf(itemClicked)
        if (jobList[index].isBookmarked){
            bookmarkManager.removeBookmark(jobList[index])
            jobList[index].isBookmarked = false
            if( isBookmarkList ){
                jobList.clear()
                jobList.addAll(bookmarkManager.getBookmarks())
                jobViewModel.getJobList(isBookmarkList, jobList)
            }
        }
        else{
            jobList[index].isBookmarked = true
            bookmarkManager.saveBookmark(jobList[index])
        }
    }

    fun showError(message: String){
        hideShimmer()
        binding.apply {
            tvErrorCase.setText(message)
            tvErrorCase.visibility = View.VISIBLE
            rvJobList.visibility = View.GONE
        }
    }

    companion object{

        private const val ARG_IS_BOOKMARK_LIST = "is_bookmark_list"

        fun newInstance(isBookmarkList: Boolean) : FragmentJobs {
            return FragmentJobs().apply {
                arguments = Bundle().apply {
                    putBoolean(ARG_IS_BOOKMARK_LIST, isBookmarkList)
                }
            }
        }
    }
}