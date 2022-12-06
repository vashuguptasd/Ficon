package com.example.ficon.asking_coarse_fragments.year_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.ficon.R
import com.example.ficon.asking_coarse_fragments.viewmodel.SharedViewModel
import com.example.ficon.databinding.FragmentAskingYearBinding

class AskingYearFragment : Fragment() {
    private lateinit var binding: FragmentAskingYearBinding
    private val viewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAskingYearBinding.inflate(layoutInflater)
        val activity = requireNotNull(this.activity).application


        val views = listOf(
            binding.partOneTextView,
            binding.partTwoTextView,
            binding.partThreeTextView,
            binding.sem1TextView,
            binding.sem2TextView,
            binding.sem3TextView,
            binding.sem4TextView,
            binding.sem5TextView,
            binding.sem6TextView
        )

        views.forEach {
            val text = it.text
            it.setOnClickListener {
                viewModel.updateYear(text.toString())
                findNavController().navigate(R.id.action_askingYearFragment_to_subjectFragment)
                Toast.makeText(activity,text.toString(),Toast.LENGTH_SHORT).show()


            }
        }

        return binding.root
    }
}

