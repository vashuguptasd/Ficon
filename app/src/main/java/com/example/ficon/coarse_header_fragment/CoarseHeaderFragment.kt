package com.example.ficon.coarse_header_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.ficon.R
import com.example.ficon.databinding.FragmentCoarseHeaderBinding
import com.google.android.material.tabs.TabLayoutMediator

class CoarseHeaderFragment : Fragment() {
    private lateinit var binding : FragmentCoarseHeaderBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCoarseHeaderBinding.inflate(layoutInflater)

        binding.apply {

            val viewPager = viewPagerCoarseFragment
            val adapter = CoarseViewPagerAdapter(this@CoarseHeaderFragment)

            viewPager.adapter = adapter
            viewPager.orientation = ViewPager2.ORIENTATION_VERTICAL



        }















        return binding.root
    }
}