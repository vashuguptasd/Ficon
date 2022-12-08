package com.example.ficon.asking_coarse_fragments.askingChapterFragments

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
import com.example.ficon.asking_coarse_fragments.adapter_and_dataClass.CoarseDataClass
import com.example.ficon.asking_coarse_fragments.adapter_and_dataClass.CoarseFragmentRecyclerViewAdapter
import com.example.ficon.asking_coarse_fragments.viewmodel.SharedViewModel
import com.example.ficon.databinding.FragmentAskingChapterBinding
import com.example.ficon.pdfFragments.firestoreDataClass.asCoarseModel

class AskingChapterFragment : Fragment() {
    private val viewModel : SharedViewModel by activityViewModels()
    private lateinit var binding : FragmentAskingChapterBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAskingChapterBinding.inflate(layoutInflater)
        val application = requireNotNull(this.activity).application
        binding.apply {

            recyclerView.layoutManager = GridLayoutManager(application,1)

            val adapter =  CoarseFragmentRecyclerViewAdapter(33,22, ClickListener {
                Toast.makeText(application,it.toString(),Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_askingChapterFragment_to_holderFragment)
            })

            recyclerView.adapter = adapter
            viewModel._fireStoreData.observe(viewLifecycleOwner, Observer {
                       adapter.submitList(it.asCoarseModel())
            })
        }
        return binding.root
    }
}