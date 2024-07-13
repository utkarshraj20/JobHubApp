package com.example.careerlink.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.careerlink.R
import com.example.careerlink.databinding.JobDetailsFragmentBinding
import com.example.careerlink.utils.JobDetailsDataSingleton

class FragmentJobDetails : BaseFragment<JobDetailsFragmentBinding>() {

    private val jobDetailData = JobDetailsDataSingleton.getJobDetailsData()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): JobDetailsFragmentBinding? {
        return JobDetailsFragmentBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvCompanyName.text = jobDetailData.companyName
        binding.tvRole.text = getString(R.string.role, jobDetailData.jobRole)
        binding.tvRoleCategory.text = getString(R.string.job_category, jobDetailData.jobCategory)
        binding.tvPlace.text = getString(R.string.place, jobDetailData.primaryDetails?.place)
        binding.tvSalary.text = getString(R.string.salary, jobDetailData.primaryDetails?.salary)
        binding.tvExperience.text = getString(R.string.experience, jobDetailData.primaryDetails?.experience)
        binding.tvQualication.text = getString(R.string.qualification, jobDetailData.primaryDetails?.qualification)
        binding.tvOpenings.text = getString(R.string.openings, jobDetailData.openingCount)
        binding.tvContactTime.text = getString(R.string.contact_time, jobDetailData.contactPreference?.preferredStartTime, jobDetailData.contactPreference?.preferredEndTime)
        binding.tvContact.text = getString(R.string.contact, jobDetailData.contact)
    }

    companion object{
        fun newInstance() : FragmentJobDetails {
            return FragmentJobDetails();
        }
    }
}