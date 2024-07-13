package com.example.careerlink.data.models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class JobListData(
    @SerializedName("results") val result: List<JobData>
)

@Serializable
data class JobData(
    @SerializedName("id") val id : String? = "Not Available",
    @SerializedName("title") val title: String? = "Not Available",
    @SerializedName("primary_details") val primaryDetails: PrimaryDetails?,
    @SerializedName("whatsapp_no") val contact: String? = "Not Available",
    @SerializedName("company_name") val companyName: String? = "Not Available",
    @SerializedName("contact_preference") val contactPreference: ContactPreference?,
    @SerializedName("openings_count") val openingCount: String? = "Not Available",
    @SerializedName("job_role") val jobRole: String?= "Not Available",
    @SerializedName("job_category") val jobCategory: String? = "Not Available",
    var isBookmarked: Boolean = false
)

@Serializable
data class PrimaryDetails(
    @SerializedName("Place") val place: String? = "Not Available",
    @SerializedName("Salary") val salary: String? = "Not Available",
    @SerializedName("Experience") val experience: String? = "Not Available",
    @SerializedName("Qualification") val qualification: String?= "Not Available"
)

@Serializable
data class ContactPreference(
    @SerializedName("preferred_call_start_time") val preferredStartTime: String? = "NA",
    @SerializedName("preferred_call_end_time") val preferredEndTime: String? = "NA"
)
