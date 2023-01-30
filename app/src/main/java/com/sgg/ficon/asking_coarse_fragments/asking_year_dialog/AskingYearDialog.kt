package com.sgg.ficon.asking_coarse_fragments.asking_year_dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.sgg.ficon.R
import com.sgg.ficon.asking_coarse_fragments.viewmodel.SharedViewModel
import com.sgg.ficon.databinding.FragmentAskingYearBinding

class AskingYearDialog : DialogFragment() {
    private lateinit var binding : FragmentAskingYearBinding
    private val viewModel: SharedViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let { fragmentActivity ->
            val builder = AlertDialog.Builder(fragmentActivity)

            binding = FragmentAskingYearBinding.inflate(layoutInflater)

            val views = listOf(

                    binding.partOneTextView,
                    binding.partTwoTextView,
                    binding.partThreeTextView,
                    binding.sem1TextView,
                    binding.sem2TextView,
                    binding.sem3TextView,
                    binding.sem4TextView,
                    binding.sem5TextView,
                    binding.sem6TextView
            )

            views.forEach {
                val text = it.text
                it.setOnClickListener {
                    viewModel.updateYear(text.toString())
                    findNavController().navigate(R.id.action_askingCoarseFragment_to_subjectFragment)
                    Toast.makeText(activity,text.toString(), Toast.LENGTH_SHORT).show()

                }
            }


            // hiding sem in Batchelor and Part in Masters subject
            val coarse = viewModel.mCoarseSelected
            if (coarse=="B.Sc"||coarse=="B.A"||coarse=="B.Com"||coarse=="B.C.A"){
                hideSemester()
            }else{
                hideParts()
            }


            builder.setView(binding.root)
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }


    private fun hideParts() {
        binding.cardView.visibility = View.GONE
        binding.cardView2.visibility = View.GONE
        binding.cardView3.visibility = View.GONE
        binding.textView.visibility = View.GONE
    }

    private fun hideSemester() {
        binding.textView3.visibility = View.GONE
        binding.cardView4.visibility = View.GONE
        binding.cardView5.visibility = View.GONE
        binding.cardView6.visibility = View.GONE
        binding.cardView7.visibility = View.GONE
        binding.cardView8.visibility = View.GONE
        binding.cardView9.visibility = View.GONE
    }
}