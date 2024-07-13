package com.example.careerlink.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.careerlink.R

abstract class BaseFragment<VB: ViewBinding>: Fragment() {
    private var _binding: VB? = null
    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return getViewBinding( inflater , container ).apply { _binding = this }?.root
    }

    abstract fun getViewBinding( inflater: LayoutInflater , container: ViewGroup? ) : VB?

    fun replaceFragment( fragment: Fragment ){
        childFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, fragment,"")
            .addToBackStack(fragment::class.simpleName)
            .commit()
    }

}