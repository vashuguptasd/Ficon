package com.example.ficon.asking_coarse_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ficon.databinding.FragmentAskingOptionalBinding

class AskingOptionalFragment : Fragment() {
    private lateinit var binding : FragmentAskingOptionalBinding
    private val viewModel : SharedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAskingOptionalBinding.inflate(layoutInflater)
        val activity = requireNotNull(this.activity).application

        binding.apply {

            val recyclerView = selectYourYearRecyclerView

            val adapter = CoarseFragmentRecyclerViewAdapter( ClickListener {
                viewModel.updateOptional(it)
                Toast.makeText(activity, it,Toast.LENGTH_SHORT).show()

            })
            adapter.submitList(
                listOf(
                    CoarseDataClass("Maths", "null"),
                    CoarseDataClass("Bio", "null"),

                    )

            )
            recyclerView.adapter = adapter

            recyclerView.layoutManager = LinearLayoutManager(activity)
            lifecycleOwner = lifecycleOwner

        }

        return binding.root
    }

}