package com.example.ficon.pdfFragments

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.ficon.asking_coarse_fragments.coarse_fragment.AskingCoarseFragment
import com.example.ficon.asking_coarse_fragments.subjects_fragment.AskingOptionalFragment
import com.example.ficon.asking_coarse_fragments.year_fragment.AskingYearFragment
import com.example.ficon.pdfFragments.testFragment.BlankFragmentOne
import com.example.ficon.pdfFragments.testFragment.BlankFragmentThree
import com.example.ficon.pdfFragments.testFragment.BlankFragmentTwo

class MyViewPagerAdapter (holderFragment: HolderFragment) : FragmentStateAdapter(holderFragment) {

    private val list = listOf(BlankFragmentOne(),BlankFragmentTwo(),BlankFragmentThree())

    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {
        return list[position]
    }

}