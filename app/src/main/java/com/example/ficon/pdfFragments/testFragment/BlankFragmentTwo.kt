package com.example.ficon.pdfFragments.testFragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.ficon.asking_coarse_fragments.viewmodel.LOG
import com.example.ficon.asking_coarse_fragments.viewmodel.SharedViewModel
import com.example.ficon.databinding.FragmentBlankTwoBinding
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle
import java.io.File

// TODO: Rename parameter arguments, choose names that match

class BlankFragmentTwo : Fragment() {
    private lateinit var binding : FragmentBlankTwoBinding
    private val viewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentBlankTwoBinding.inflate(layoutInflater)

        binding.buttonTwo.setOnClickListener {
            showPdfFromFile(File("/storage/emulated/0/Android/data/com.example.ficon/files/myFile.pdf"))
        }

        return binding.root
    }
    private fun showPdfFromFile(file: File) {

        binding.pdfView.fromFile(file)
                .scrollHandle(DefaultScrollHandle(context))
                .password(null)
                .spacing(5)
                .enableAntialiasing(true)
                .defaultPage(0)
                .enableSwipe(true)
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .onPageError { _, _ ->
                    Log.e("testApp","error happen")
                }
                .load()
        Log.e(LOG,"value is ${file.toString()} on 2")
    }

}