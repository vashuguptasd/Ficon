package com.example.ficon.asking_coarse_fragments.askingChapterFragments

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ficon.R
import com.example.ficon.asking_coarse_fragments.adapter_and_dataClass.ClickListener
import com.example.ficon.asking_coarse_fragments.adapter_and_dataClass.CoarseFragmentRecyclerViewAdapter
import com.example.ficon.asking_coarse_fragments.viewmodel.LOG
import com.example.ficon.asking_coarse_fragments.viewmodel.SharedViewModel
import com.example.ficon.databinding.FragmentAskingChapterBinding
import com.example.ficon.pdfFragments.firestoreDataClass.asCoarseModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class AskingChapterFragment : Fragment() {
    private val viewModel : SharedViewModel by activityViewModels()
    private lateinit var binding : FragmentAskingChapterBinding
    // ads holder
    private var mInterstitialAd: InterstitialAd? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAskingChapterBinding.inflate(layoutInflater)
        val application = requireNotNull(this.activity).application
        binding.apply {

            // showing progressbar at start
            progressBar3.visibility = View.VISIBLE

            recyclerView.layoutManager = GridLayoutManager(application,1)

            // initializing ads
            val adRequest = AdRequest.Builder().build()
            InterstitialAd.load(application,"ca-app-pub-2008088454941941/6299545425", adRequest, object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.e(LOG,"error loading ad ${adError.toString()}")
                    mInterstitialAd = null
                }
                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    Log.d(LOG, "Ad loaded")
                    mInterstitialAd = interstitialAd
                }
            })

            val adapter =  CoarseFragmentRecyclerViewAdapter(33,22, ClickListener {

                loadAds()

                findNavController().navigate(R.id.action_askingChapterFragment_to_holderFragment)
                viewModel.progressBarVisibility.value = true
                viewModel.errorDownloadingText.value = false
                viewModel.chapterClickedOn = it
            })

            recyclerView.adapter = adapter
            viewModel.mFireStoreData.observe(viewLifecycleOwner, Observer {
                       adapter.submitList(it.asCoarseModel())

                // removing progressBar on list loaded
                progressBar3.visibility = View.GONE
            })
        }
        return binding.root
    }

    private fun loadAds() {
        if (mInterstitialAd != null) {
            mInterstitialAd?.show(Activity().parent)
        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.")
        }
    }
}

