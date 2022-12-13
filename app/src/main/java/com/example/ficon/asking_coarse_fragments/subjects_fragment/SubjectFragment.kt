package com.example.ficon.asking_coarse_fragments.subjects_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ficon.asking_coarse_fragments.adapter_and_dataClass.ClickListener
import com.example.ficon.asking_coarse_fragments.adapter_and_dataClass.CoarseFragmentRecyclerViewAdapter
import com.example.ficon.asking_coarse_fragments.adapter_and_dataClass.SubjectsDataClass
import com.example.ficon.asking_coarse_fragments.adapter_and_dataClass.asCoarseModel
import com.example.ficon.asking_coarse_fragments.dialog_box.DialogBox
import com.example.ficon.asking_coarse_fragments.viewmodel.SharedViewModel
import com.example.ficon.databinding.FragmentAskingOptionalBinding
import com.google.firebase.database.*

class SubjectFragment : Fragment() {
    private lateinit var binding: FragmentAskingOptionalBinding
    private val viewModel: SharedViewModel by activityViewModels()
    private lateinit var database: DatabaseReference
    private lateinit var adapter: CoarseFragmentRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentAskingOptionalBinding.inflate(layoutInflater)
        val activity = requireNotNull(this.activity).application

        database = FirebaseDatabase.getInstance().reference
        database.keepSynced(true)
        viewModel.getListFromRealTimeDatabase(activity)

        binding.progressBar2.visibility = View.VISIBLE


        viewModel.listFromServer.observe(viewLifecycleOwner) {
            setUpRecyclerView(it as MutableList<SubjectsDataClass>?)
            binding.progressBar2.visibility = View.GONE
        }

        return binding.root
    }


    private fun setUpRecyclerView(subList: MutableList<SubjectsDataClass>?) {
        binding.apply {

            val recyclerView = selectYourYearRecyclerView
            adapter = CoarseFragmentRecyclerViewAdapter(37, 17, ClickListener {
                viewModel.mSubjectSelected = it
                if (subList != null) {
                    viewModel.getSubjectFromList(it,subList)?.let { it1 -> viewModel.updateSubjectOptions(it1) }
                }
                val dialogBox = DialogBox()
                dialogBox.show(childFragmentManager,"dialog")

            })

            recyclerView.adapter = adapter
            adapter.submitList(subList?.asCoarseModel())

            recyclerView.layoutManager = GridLayoutManager(activity, 1)
            lifecycleOwner = lifecycleOwner

        }
    }
}