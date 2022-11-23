package com.example.ficon.asking_coarse_fragments

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.ficon.R
import com.example.ficon.databinding.FragmentAskingCoarseBinding

class AskingCoarseFragment : Fragment() {

    private val viewModel : SharedViewModel by activityViewModels()

    private lateinit var binding : FragmentAskingCoarseBinding
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
        val adapter = CoarseFragmentRecyclerViewAdapter(false,33,13,ClickListener {
            viewModel.updateCoarse(it)
            Toast.makeText(activity,it,Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_askingCoarseFragment_to_askingYearFragment)
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
                CoarseDataClass("M.C.A", "Master of Computer App.."),
                CoarseDataClass("B.Tech", "Bachelor of Technology"),
                CoarseDataClass("M.Tech", "Master of Technology"),
                CoarseDataClass("BBA", "Bachelors of Business Admin.."),
                CoarseDataClass("MBA", "Master of Business Admin.."),
                CoarseDataClass("LLB", "Bachelor of Laws"),
                CoarseDataClass("LLM", "Master of Laws"),
                CoarseDataClass("B.Pharma", "Bachelor of Pharmacy"),
                CoarseDataClass("M.Pharma", "Master of Pharmacy"),
                CoarseDataClass("D.Pharma", "Diploma in Pharmacy"),
                CoarseDataClass("B.ED", "Bachelor in Education"),
                CoarseDataClass("BE", "Bachelor of Engineering"),
                CoarseDataClass("ME", "Master of Engineering"),
                CoarseDataClass("BSW ", "Bachelor of Social Work."),
                CoarseDataClass("MSW", "Master of Social Work "),
                CoarseDataClass("BPE", "Bachelor in Physical Edu.."),
                CoarseDataClass("MPE", "Master in Physical Edu.."),
                CoarseDataClass("BA-LLB", "Bachelor of Legislative Law"),
                CoarseDataClass("BHSC", "Bachelor of Health Science."),
                CoarseDataClass("BPED", "Master of Physical Education"),
                CoarseDataClass("BVOC", "Bachelor of Vocation"),
                CoarseDataClass("DIPLOMA", "Development and Improvement  "),
                CoarseDataClass("MED", " Masters in Education"),
                CoarseDataClass("MPED", "Master of Physical Education"),
                CoarseDataClass("MPHIL", "Master of Philosophy"),
                CoarseDataClass("SHASTRI", "Shastri"),
                CoarseDataClass("ACHARYA", "Aacharya"),

            )
        )
        recyclerView.layoutManager = GridLayoutManager(application,2)
        lifecycleOwner = lifecycleOwner


        return binding.root
    }















//    private fun sendEmailIntent() {
//        val emailIntent = Intent(
//            Intent.ACTION_SENDTO, Uri.fromParts(
//                "mailto", "ficonsupport@gmail.com", null
//            )
//        )
//        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "add my course ")
//        emailIntent.putExtra(Intent.EXTRA_TEXT, "add my coarse")
//        startActivity(Intent.createChooser(emailIntent, "Send email..."))
//    }

}