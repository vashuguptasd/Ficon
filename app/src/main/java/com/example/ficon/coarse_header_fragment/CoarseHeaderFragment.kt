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
//            val tabLayout = tabLayoutCoarseFragment
            val adapter = CoarseViewPagerAdapter(this@CoarseHeaderFragment)

            viewPager.adapter = adapter
//            viewPager.setPageTransformer(ZoomOutPageTransformer())
            viewPager.orientation = ViewPager2.ORIENTATION_VERTICAL

//            TabLayoutMediator(tabLayout,viewPager){tab,position ->
//                when(position){
//                    0 -> tab.text = "Maths"
//                    1 -> tab.text = "Bio"
//                    2 ->tab.text = "Com.."
//                    3 ->tab.text = "Arts"
//
//                }
//            }.attach()

        }















        return binding.root
    }
}