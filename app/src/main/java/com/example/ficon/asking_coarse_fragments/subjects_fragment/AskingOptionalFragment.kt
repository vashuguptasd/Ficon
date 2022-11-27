package com.example.ficon.asking_coarse_fragments.subjects_fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
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

class AskingOptionalFragment : Fragment() {
    private lateinit var binding: FragmentAskingOptionalBinding
    private val viewModel: SharedViewModel by activityViewModels()
    private lateinit var database: DatabaseReference
    private lateinit var adapter: CoarseFragmentRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        database = FirebaseDatabase.getInstance().reference
        database.keepSynced(true)

        binding = FragmentAskingOptionalBinding.inflate(layoutInflater)
        val activity = requireNotNull(this.activity).application

        val resultPathString = viewModel.getPathString()
        val dbRef = database.child("subjects").child(resultPathString)
        dbRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val subList = mutableListOf<SubjectsDataClass>()
                    for (subjectSnap in snapshot.children) {
                        val data = subjectSnap.getValue(SubjectsDataClass::class.java)
                        if (data != null) {
                            subList.add(data)
                        }
                    }
                    Log.e("testApp",subList.toString())
                    setUpRecyclerView(subList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(activity, "Error Loading Subject", Toast.LENGTH_SHORT).show()
            }

        })

        return binding.root
    }

    private fun setUpRecyclerView(subList: MutableList<SubjectsDataClass>?) {
        binding.apply {

            val recyclerView = selectYourYearRecyclerView

            adapter = CoarseFragmentRecyclerViewAdapter(37, 17, ClickListener {

                if (subList != null) {
                    getSubjectFromList(it,subList)?.let { it1 -> viewModel.updateSubjectOptions(it1) }
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

    private fun getSubjectFromList(
        name: String,
        subjectList: MutableList<SubjectsDataClass>
    ): SubjectsDataClass? {
        return subjectList.find { it.name == name }
    }




}