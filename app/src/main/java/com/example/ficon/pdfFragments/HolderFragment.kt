package com.example.ficon.pdfFragments

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.ficon.R
import com.example.ficon.asking_coarse_fragments.viewmodel.LOG
import com.example.ficon.asking_coarse_fragments.viewmodel.SharedViewModel
import com.example.ficon.databinding.FragmentHolderBinding
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle
import java.io.File

class HolderFragment : Fragment() {

    private lateinit var binding: FragmentHolderBinding
    private val viewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHolderBinding.inflate(layoutInflater)
        val application = requireNotNull(this.activity).application

//        // showing ads
//        Log.e(LOG,"activity is ${Activity().parent.toString()}")
//        viewModel.showAds(Activity().parent)

        //show progressbar
        viewModel.progressBarVisibility.observe(viewLifecycleOwner){
            if (it){
                binding.progressBar4.visibility = View.VISIBLE
            }else{
                binding.progressBar4.visibility = View.GONE
            }
        }

        binding.apply {

            // setting up bottom navigation bar
            bottomNavigation.setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.askingCoarseFragment -> {
                        downloadSyllabus(application)
                    }
                    R.id.askingYearFragment -> {
                        downloadSolved(application)
                    }
                    R.id.subjectFragment -> {
                        downloadUnSolved(application)
                    }
                    R.id.askingChapterFragment -> {
                        downloadNotes(application)
                    }
                    R.id.holderFragment -> {
                        downloadBooks(application)
                    }

                }
                true
            }


            //observing and showing pdf
            viewModel.downloadedFilePathSyllabus.observe(viewLifecycleOwner) {
                showPdfFromFile(it)
            }
            //observing and showing pdf
            viewModel.downloadedFilePathSolved.observe(viewLifecycleOwner) {
                showPdfFromFile(it)
            }
            //observing and showing pdf
            viewModel.downloadedFilePathUnSolved.observe(viewLifecycleOwner) {
                showPdfFromFile(it)

            }
            //observing and showing pdf
            viewModel.downloadedFilePathNotes.observe(viewLifecycleOwner) {
                showPdfFromFile(it)

            }
            //observing and showing pdf
            viewModel.downloadedFilePathBooks.observe(viewLifecycleOwner) {
                showPdfFromFile(it)

            }
            // initialising With syllabus
            downloadSyllabus(application)
        }

        return binding.root
    }

    private fun downloadSyllabus(application: Application) {
        viewModel.progressBarVisibility.value = true
        if (viewModel.syllabusDownloaded.value == true) {
            viewModel.downloadedFilePathSyllabus.value?.let {
                showPdfFromFile(it)
            }
        } else {
            viewModel.downloadPdfFromInternet(
                    application,
                    "syllabus",
                    viewModel.getRootDirPath(application),
                    "syllabus.pdf"
            )
            viewModel.syllabusDownloaded.value = true
        }
    }

    private fun downloadSolved(application: Application) {
        viewModel.progressBarVisibility.value = true
        if (viewModel.solvedDownloaded.value == true) {
            viewModel.downloadedFilePathSolved.value?.let { showPdfFromFile(it) }
        } else {
            viewModel.downloadPdfFromInternet(
                    application,
                    "solved",
                    viewModel.getRootDirPath(application),
                    "solved.pdf"
            )
            viewModel.solvedDownloaded.value = true
        }
    }

    private fun downloadUnSolved(application: Application) {
        viewModel.progressBarVisibility.value = true
        if (viewModel.unSolvedDownloaded.value == true) {
            viewModel.downloadedFilePathUnSolved.value?.let { showPdfFromFile(it) }
        } else {
            viewModel.downloadPdfFromInternet(
                    application,
                    "unSolved",
                    viewModel.getRootDirPath(application),
                    "unSolved.pdf"
            )
            viewModel.unSolvedDownloaded.value = true
        }
    }

    private fun downloadNotes(application: Application) {
        viewModel.progressBarVisibility.value = true
        if (viewModel.notesDownloaded.value == true) {
            viewModel.downloadedFilePathNotes.value?.let { showPdfFromFile(it) }
        } else {
            viewModel.downloadPdfFromInternet(
                    application,
                    "notes",
                    viewModel.getRootDirPath(application),
                    "notes.pdf"
            )
            viewModel.notesDownloaded.value = true
        }
    }

    private fun downloadBooks(application: Application) {
        viewModel.progressBarVisibility.value = true
        if (viewModel.booksDownloaded.value == true) {
            viewModel.downloadedFilePathBooks.value?.let { showPdfFromFile(it) }
        } else {
            viewModel.downloadPdfFromInternet(
                    application,
                    "book",
                    viewModel.getRootDirPath(application),
                    "book.pdf"
            )
            viewModel.booksDownloaded.value = true
        }
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
                .onPageError { page, error ->
                    Log.e("testApp", "error loading page on${page.toString()} and error ${error.toString()} ")
                }
                .load()
        viewModel.progressBarVisibility.value = false
    }


}