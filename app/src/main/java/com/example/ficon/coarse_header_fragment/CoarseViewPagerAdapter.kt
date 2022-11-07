package com.example.ficon.coarse_header_fragment

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class CoarseViewPagerAdapter(coarseHeaderFragment: CoarseHeaderFragment) : FragmentStateAdapter(coarseHeaderFragment) {

    private val fragmentList = listOf(CoarseSubFragmentMaths(),CoarseSubFragmentBio(),CoarseSubFragmentCom(),CoarseSubFragmentArts())

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }

}
