package com.example.ficon.asking_coarse_fragments

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ficon.R
import com.example.ficon.databinding.FragmentAskingYearBinding

class AskingYearFragment : Fragment() {
    private lateinit var binding: FragmentAskingYearBinding
    private val viewModel: SharedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAskingYearBinding.inflate(layoutInflater)
        val activity = requireNotNull(this.activity).application















        return binding.root
    }
}

