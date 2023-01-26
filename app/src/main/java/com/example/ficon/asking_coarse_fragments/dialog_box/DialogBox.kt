package com.example.ficon.asking_coarse_fragments.dialog_box

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.ficon.R
import com.example.ficon.asking_coarse_fragments.viewmodel.SharedViewModel
import com.example.ficon.databinding.DialogBoxLayoutBinding

class DialogBox : DialogFragment() {

    private lateinit var binding : DialogBoxLayoutBinding
    private val viewModel: SharedViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let { fragmentActivity ->
            val builder = AlertDialog.Builder(fragmentActivity)

            binding = DialogBoxLayoutBinding.inflate(layoutInflater)
            var option1Visibility = false
            var option2Visibility = false
            var option3Visibility = false
            var option4Visibility = false

            viewModel.getSubjectsOptions().let { it ->
                it?.let {
                    binding.apply {
                        it.part1Hindi?.let {
                            subjectOption1Hindi.text = it
                        }
                        it.part2Hindi?.let {
                            subjectOption2Hindi.text = it

                        }
                        it.part3Hindi?.let {
                            subjectOption3Hindi.text = it

                        }
                        it.part4Hindi?.let {
                            subjectOption4Hindi.text = it

                        }
                        it.part1English?.let {
                            subjectOption1English.text = it
                            option1Visibility = true

                        }
                        it.part2English?.let {
                            subjectOption2English.text = it
                            option2Visibility = true

                        }
                        it.part3English?.let {
                            subjectOption3English.text = it
                            option3Visibility = true

                        }
                        it.part4English?.let {
                            subjectOption4English.text = it
                            option4Visibility = true

                        }
                    }
                }
            }

            if (!option1Visibility){
                binding.cardView10.visibility = View.GONE
            }
            if (!option2Visibility){
                binding.cardView11.visibility = View.GONE
            }
            if (!option3Visibility){
                binding.cardView12.visibility = View.GONE
            }
            if (!option4Visibility){
                binding.cardView13.visibility = View.GONE
            }

            binding.cardView10.setOnClickListener {
                viewModel.mPartSelected = "part1"
                findNavController().navigate(R.id.action_subjectFragment_to_askingChapterFragment)
                viewModel.callFireStore()

            }
            binding.cardView11.setOnClickListener {
                viewModel.mPartSelected = "part2"
                findNavController().navigate(R.id.action_subjectFragment_to_askingChapterFragment)
                viewModel.callFireStore()

            }
            binding.cardView12.setOnClickListener {
                viewModel.mPartSelected = "part3"
                findNavController().navigate(R.id.action_subjectFragment_to_askingChapterFragment)
                viewModel.callFireStore()

            }
            binding.cardView13.setOnClickListener {
                viewModel.mPartSelected = "part4"
                findNavController().navigate(R.id.action_subjectFragment_to_askingChapterFragment)
                viewModel.callFireStore()

            }

            builder.setView(binding.root)
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}