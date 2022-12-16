package com.example.ficon.pdfFragments

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

        binding.apply {


            bottomNavigation.setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.blankFragmentOne -> downloadSyllabus(application)
                    R.id.blankFragmentTwo -> downloadSolved(application)
                    R.id.blankFragmentThree -> downloadUnSolved(application)
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
                Log.e(LOG, "downloaded file is $it")

            }
            //observing and showing pdf
            viewModel.downloadedFilePathNotes.observe(viewLifecycleOwner) {
                showPdfFromFile(it)
                Log.e(LOG, "downloaded file is $it")

            }
            //observing and showing pdf
            viewModel.downloadedFilePathBooks.observe(viewLifecycleOwner) {
                showPdfFromFile(it)
                Log.e(LOG, "downloaded file is $it")

            }

//          downloading syllabus for initialising
            downloadUnSolved(application)
//          downloadSyllabus(application)
//          downloadSolved(application)
        }

        return binding.root
    }

    private fun downloadSyllabus(application: Application) {
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
        viewModel.downloadedFilePathSolved.value?.let { showPdfFromFile(it) }
    }

    private fun downloadUnSolved(application: Application) {
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
        viewModel.downloadedFilePathUnSolved.value?.let { showPdfFromFile(it) }
    }

    private fun downloadNotes(application: Application) {
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
        viewModel.downloadedFilePathNotes.value?.let { showPdfFromFile(it) }
    }

    private fun downloadBooks(application: Application) {
        if (viewModel.booksDownloaded.value == true) {
            viewModel.downloadedFilePathBooks.value?.let { showPdfFromFile(it) }
        } else {
            viewModel.downloadPdfFromInternet(
                    application,
                    "books",
                    viewModel.getRootDirPath(application),
                    "books.pdf"
            )
            viewModel.booksDownloaded.value = true
        }
        viewModel.downloadedFilePathBooks.value?.let { showPdfFromFile(it) }
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
    }

}