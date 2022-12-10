package com.example.ficon.pdfFragments.testFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ficon.databinding.FragmentBlankTwoBinding

// TODO: Rename parameter arguments, choose names that match

class BlankFragmentTwo : Fragment() {
    private lateinit var binding : FragmentBlankTwoBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentBlankTwoBinding.inflate(layoutInflater)







        return binding.root
    }

}