package com.example.ficon.asking_coarse_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ficon.databinding.FragmentAskingOptionalBinding
import com.google.firebase.database.*

class AskingOptionalFragment : Fragment() {
    private lateinit var binding: FragmentAskingOptionalBinding
    private val viewModel: SharedViewModel by viewModels()

    private lateinit var database: DatabaseReference
    private lateinit var adapter: CoarseFragmentRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        database = FirebaseDatabase.getInstance().reference

        binding = FragmentAskingOptionalBinding.inflate(layoutInflater)
        val activity = requireNotNull(this.activity).application

        val dbRef = database.child("subjects").child("bsc1part1")

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
                    setUpRecyclerView(subList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(activity, "Error Loading Subject", Toast.LENGTH_SHORT).show()
            }

            private fun setUpRecyclerView(subList: MutableList<SubjectsDataClass>?) {
                binding.apply {

                    val recyclerView = selectYourYearRecyclerView

                    adapter = CoarseFragmentRecyclerViewAdapter(true, 37, 17, ClickListener {
                        viewModel.updateOptional(it)
                        Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()

                    })

                    recyclerView.adapter = adapter
                    adapter.submitList(subList?.asCoarseModel())

                    recyclerView.layoutManager = GridLayoutManager(activity, 1)
                    lifecycleOwner = lifecycleOwner

                }
            }

        })

        return binding.root
    }


}