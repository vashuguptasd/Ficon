package com.example.ficon.asking_coarse_fragments.dialog_box

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
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
            // Get the layout inflater
            val inflater = requireActivity().layoutInflater;
            binding = DialogBoxLayoutBinding.inflate(layoutInflater)
            var option1Visibility = false
            var option2Visibility = false
            var option3Visibility = false

            viewModel.getSubjectsOptions().let { it ->
                it?.let {
                    binding.apply {

                        it.part1hindi?.let {
                            subjectOption1Hindi.text = it
                        }
                        it.part2hindi?.let {
                            subjectOption2Hindi.text = it

                        }
                        it.part3hindi?.let {
                            subjectOption3Hindi.text = it

                        }
                        it.part1english?.let {
                            subjectOption1English.text = it
                            option1Visibility = true

                        }
                        it.part2english?.let {
                            subjectOption2English.text = it
                            option2Visibility = true

                        }
                        it.part3english?.let {
                            subjectOption3English.text = it
                            option3Visibility = true

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

            binding.cardView10.setOnClickListener {
                Log.e("testApp","I am clicked")
                findNavController().navigate(R.id.action_askingOptionalFragment_to_holderFragment2)

            }
            binding.cardView11.setOnClickListener {
                Log.e("testApp","I am clicked")

            }
            binding.cardView12.setOnClickListener {
                Log.e("testApp","I am clicked")

            }


            builder.setView(binding.root)
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}