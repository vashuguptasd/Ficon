package com.example.ficon.asking_coarse_fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ficon.R
import com.example.ficon.databinding.FragmentAskingCoarseBinding
import kotlinx.coroutines.NonDisposableHandle.parent

class AskingCoarseFragment : Fragment() {

    private lateinit var binding : FragmentAskingCoarseBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAskingCoarseBinding.inflate(layoutInflater)
        val application = requireNotNull(this.activity).application

        binding.apply {
            val recyclerView = chooseCoarseRecyclerView
            val adapter = CoarseFragmentRecyclerViewAdapter()

            recyclerView.adapter = adapter
            adapter.submitList(listOf(
                CoarseDataClass("B.Sc","Batchelor of Science"),
                CoarseDataClass("M.Sc","Master of Science"),
                CoarseDataClass("B.A","Batchelor of Arts"),
                CoarseDataClass("M.A","Master of Arts"),
                CoarseDataClass("B.Com","Batchelor of Commerce"),
                CoarseDataClass("M.Com","Master of Commerce"),
                CoarseDataClass("B.C.A","Batchelor of Computer App.."),
                CoarseDataClass("B.C.A","Master of Computer App..")

            ))
            recyclerView.layoutManager = GridLayoutManager(application,1)
            lifecycleOwner = lifecycleOwner

        }


        return binding.root
    }

}