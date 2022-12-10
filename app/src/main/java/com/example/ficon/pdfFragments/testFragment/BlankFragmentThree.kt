package com.example.ficon.pdfFragments.testFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ficon.databinding.FragmentBlankThreeBinding

class BlankFragmentThree : Fragment() {
    private lateinit var binding : FragmentBlankThreeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentBlankThreeBinding.inflate(layoutInflater)






        return binding.root
    }

}