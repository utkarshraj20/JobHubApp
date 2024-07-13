package com.example.careerlink.ui.adaptors

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.careerlink.R
import com.example.careerlink.databinding.JobCardBinding
import com.example.careerlink.data.models.JobData

class JobListAdaptor(private val jobList: MutableList<JobData>,
                     private val onItemClick: (JobData) -> Unit,
                     private val onBookmarkClick: (JobData) -> Unit ) : RecyclerView.Adapter<JobListAdaptor.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): ViewHolder {
        val binding = JobCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding) { position ->
            val job = jobList[position]
            onItemClick(job)
        }.apply {
            binding.btnBookMark.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val job = jobList[position]
                    onBookmarkClick(job)
                }
                notifyItemChanged(position)
            }
        }
    }

    override fun getItemCount(): Int = jobList.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(jobList[position])
    }


    class ViewHolder(
        private val binding: JobCardBinding,
        onItemClicked: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                onItemClicked(bindingAdapterPosition)
            }
        }

        fun bind( job: JobData){
            binding.tvTitle.text = binding.root.context.getString(R.string.role, job.title)
            binding.tvLocation.text = binding.root.context.getString(R.string.place, job.primaryDetails?.place)
            binding.tvSalary.text = binding.root.context.getString(R.string.salary, job.primaryDetails?.salary)
            binding.tvContact.text = binding.root.context.getString(R.string.contact, job.contact)
            itemView.findViewById<ImageView>(R.id.btnBookMark).setImageResource(
                if( job.isBookmarked ) R.mipmap.ic_bookmark_filled else R.mipmap.ic_bookmark_border
            )
            binding.executePendingBindings()
        }
    }
}