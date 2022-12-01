package com.example.ficon.pdfFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ficon.R
import com.example.ficon.databinding.FragmentHolderBinding
import com.google.android.material.tabs.TabLayoutMediator

class HolderFragment : Fragment() {

    private lateinit var binding : FragmentHolderBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHolderBinding.inflate(layoutInflater)


        binding.apply {

            val viewPager = viewPager
            val tabLayout = tabLayout
            val adapter = MyViewPagerAdapter(this@HolderFragment)

            viewPager.adapter = adapter


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