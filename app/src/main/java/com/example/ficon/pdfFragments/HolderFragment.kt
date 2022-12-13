package com.example.ficon.pdfFragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.ficon.R
import com.example.ficon.asking_coarse_fragments.viewmodel.SharedViewModel
import com.example.ficon.databinding.FragmentHolderBinding
import com.google.android.material.tabs.TabLayoutMediator

class HolderFragment : Fragment() {

    private lateinit var binding : FragmentHolderBinding
    private val viewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHolderBinding.inflate(layoutInflater)

        binding.apply {

            val viewPager = viewPager
            val tabLayout = tabLayout
            val adapter = MyViewPagerAdapter(this@HolderFragment)
            viewPager.adapter = adapter

            // disable viewpager scroll
            viewPager.isUserInputEnabled = false

            TabLayoutMediator(tabLayout,viewPager){tab,position ->
                when(position){
                    0 -> tab.text = "Syllabus"
                    1 -> tab.text = "UnSolved PYQ"
                    2 ->tab.text = "Solved PYQ"
                }
            }.attach()
        }

        return binding.root
    }

}