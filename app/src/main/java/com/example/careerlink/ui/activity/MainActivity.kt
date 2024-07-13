package com.example.careerlink.ui.activity

import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.careerlink.R
import com.example.careerlink.databinding.ActivityMainBinding
import com.example.careerlink.ui.fragments.FragmentJobs

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if( savedInstanceState == null ){
            setActiveTab(binding.btnJobApi)
            setInactiveTab(binding.btnBookMark)
            replaceFragment(FragmentJobs.newInstance(false))
        }
        binding.btnJobApi.setOnClickListener {
            setActiveTab(binding.btnJobApi)
            setInactiveTab(binding.btnBookMark)
            replaceFragment(FragmentJobs.newInstance(false))
        }
        binding.btnBookMark.setOnClickListener {
            setActiveTab(binding.btnBookMark)
            setInactiveTab(binding.btnJobApi)
            replaceFragment(FragmentJobs.newInstance(true))
        }
    }

    private fun setActiveTab(tab: TextView) {
        tab.setTextColor(ContextCompat.getColor(this, R.color.active_tab_color))
        tab.setTypeface(null, Typeface.BOLD)
    }

    private fun setInactiveTab(tab: TextView) {
        tab.setTextColor(ContextCompat.getColor(this, R.color.inactive_tab_color))
        tab.setTypeface(null, Typeface.NORMAL)
    }

    fun hideBottomNav(){
        binding.btnJobApi.visibility = View.GONE
        binding.btnBookMark.visibility = View.GONE
    }

    fun showBottomNav(){
        binding.btnJobApi.visibility = View.VISIBLE
        binding.btnBookMark.visibility = View.VISIBLE
    }

    fun replaceFragment( fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer , fragment)
            .apply {
                addToBackStack(fragment::class.simpleName)
            }
            .commit()
    }

}