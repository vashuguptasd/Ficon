package com.example.ficon.asking_coarse_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ficon.R
import com.example.ficon.databinding.FragmentAskingCoarseBinding
import com.example.ficon.databinding.FragmentAskingYearBinding
import kotlinx.coroutines.NonDisposableHandle.parent

class AskingYearFragment : Fragment() {
    private lateinit var binding : FragmentAskingYearBinding
    private val viewModel : SharedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAskingYearBinding.inflate(layoutInflater)
        val activity = requireNotNull(this.activity).application

        binding.apply{

            val recyclerView = selectYourYearRecyclerView
            val adapter = CoarseFragmentRecyclerViewAdapter(ClickListener {
                viewModel.updateYear(it)
                Toast.makeText(activity,it,Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_askingYearFragment_to_askingOptionalFragment)
            })

            val list = listOf<CoarseDataClass>()
            adapter.submitList(
                listOf(
                    CoarseDataClass("1st Year", "null"),
                    CoarseDataClass("2nd Year", "null"),
                    CoarseDataClass("3rd Year", "null"),

                    )
            )


            recyclerView.adapter = adapter
            recyclerView.layoutManager = GridLayoutManager(activity,1)
            lifecycleOwner = lifecycleOwner

        }










        return binding.root
    }

}