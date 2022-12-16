package com.example.ficon.pdfFragments.testFragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.ficon.asking_coarse_fragments.viewmodel.SharedViewModel
import com.example.ficon.databinding.FragmentBlankOneBinding
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle
import java.io.File

class BlankFragmentOne : Fragment() {
    private lateinit var binding : FragmentBlankOneBinding
    private val viewModel: SharedViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        binding = FragmentBlankOneBinding.inflate(layoutInflater)
        val application = requireNotNull(this.activity).application

        //observing and showing pdf
        viewModel.downloadedFilePathSyllabus.observe(viewLifecycleOwner, Observer {
            showPdfFromFile(it)
        })

        // observing and set up progress bar visibility
        viewModel.progressBarVisibility.observe(viewLifecycleOwner, Observer {
            if (it){
                binding.progressBar.visibility = View.VISIBLE

            }
            else{
                binding.progressBar.visibility = View.GONE

            }
        })

        //Setting up progressbar visibility
        viewModel.progressBarVisibility.value = true


        if (viewModel.syllabusDownloaded.value == true){
            viewModel.downloadedFilePathSyllabus.value?.let { showPdfFromFile(it) }
            binding.progressBar.visibility = View.GONE
        }else{
            viewModel.downloadPdfFromInternet(
                application,
                "syllabus",
                viewModel.getRootDirPath(application),
                "myFile.pdf"
            )
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

    }


}