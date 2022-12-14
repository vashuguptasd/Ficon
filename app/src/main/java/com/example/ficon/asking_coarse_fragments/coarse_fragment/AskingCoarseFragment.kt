package com.example.ficon.asking_coarse_fragments.coarse_fragment

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ficon.R
import com.example.ficon.asking_coarse_fragments.adapter_and_dataClass.ClickListener
import com.example.ficon.asking_coarse_fragments.adapter_and_dataClass.CoarseDataClass
import com.example.ficon.asking_coarse_fragments.adapter_and_dataClass.CoarseFragmentRecyclerViewAdapter
import com.example.ficon.asking_coarse_fragments.asking_year_dialog.AskingYearDialog
import com.example.ficon.asking_coarse_fragments.viewmodel.LOG
import com.example.ficon.asking_coarse_fragments.viewmodel.SharedViewModel
import com.example.ficon.databinding.FragmentAskingCoarseBinding
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import kotlinx.coroutines.NonDisposableHandle.parent

class AskingCoarseFragment : Fragment() {

    private val viewModel: SharedViewModel by activityViewModels()
    private lateinit var binding: FragmentAskingCoarseBinding
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAskingCoarseBinding.inflate(layoutInflater)
        val application = requireNotNull(this.activity).application

        binding.apply {
            return setUpRecyclerView(application)
        }
    }

    private fun FragmentAskingCoarseBinding.setUpRecyclerView(application: Application?): View {

        val recyclerView = chooseCoarseRecyclerView
        val adapter = CoarseFragmentRecyclerViewAdapter(34, 16, ClickListener {
            viewModel.updateCoarse(it)
            val dialogBox = AskingYearDialog()
            dialogBox.show(childFragmentManager,"dialog")

        })

        recyclerView.adapter = adapter
        adapter.submitList(
                listOf(
                        CoarseDataClass("B.Sc", "Batchelor of Science"),
                        CoarseDataClass("M.Sc", "Master of Science"),
                        CoarseDataClass("B.A", "Batchelor of Arts"),
                        CoarseDataClass("M.A", "Master of Arts"),
                        CoarseDataClass("B.Com", "Batchelor of Commerce"),
                        CoarseDataClass("M.Com", "Master of Commerce"),
                        CoarseDataClass("B.C.A", "Batchelor of Computer App.."),
                        CoarseDataClass("M.C.A", "Master of Computer App..")

                )
        )
        recyclerView.layoutManager = GridLayoutManager(application, 2)
        lifecycleOwner = lifecycleOwner


        // setting up floating action button
        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_askingCoarseFragment_to_copyRightFragment)
        }

        return binding.root
    }
}