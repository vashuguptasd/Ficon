package com.example.ficon.pdfFragments

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
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

    // logic for pdf not changing on changing unit on back click
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // setting all pdf as not downloaded
                setUpAppPdfDownloadedFalse()
                isEnabled = false
                activity?.onBackPressed()
            }

            private fun setUpAppPdfDownloadedFalse() {
                Log.e(LOG,"Back Button Pressed")
                viewModel.solvedDownloaded.value = false
                viewModel.unSolvedDownloaded.value = false
                viewModel.syllabusDownloaded.value = false
                viewModel.booksDownloaded.value = false
                viewModel.notesDownloaded.value = false
            }
        })
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHolderBinding.inflate(layoutInflater)
        val application = requireNotNull(this.activity).application

        // setting up progressbar
        viewModel.progressBarVisibility.value = true
        viewModel.errorDownloadingText.value = false

        // showing error downloading file on download fail
        viewModel.errorDownloadingText.observe(viewLifecycleOwner){
            Log.e(LOG,"error downloading text value is ${it.toString()}")
            if(it){
                binding.loadingTextView.visibility = View.VISIBLE
                binding.loadingTextView.text = getString(R.string.download_fail)
            }
            else{
                binding.loadingTextView.visibility = View.VISIBLE
                binding.loadingTextView.text = getString(R.string.loading)
            }
        }
        //show progressbar
        //show loading text view
        viewModel.progressBarVisibility.observe(viewLifecycleOwner){
            Log.e(LOG,"progressBarVisibility value is ${it.toString()}")
            if (it){
                binding.loadingTextView.visibility = View.VISIBLE
                binding.progressBar4.visibility = View.VISIBLE
            }else{
                binding.loadingTextView.visibility = View.GONE
                binding.progressBar4.visibility = View.GONE
            }
        }

        binding.apply {

            // setting up bottom navigation bar
            bottomNavigation.setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.askingCoarseFragment -> {
                        // resetting downloading text view
                        viewModel.errorDownloadingText.value = false
                        downloadSyllabus(application)
                    }
                    R.id.askingYearFragment -> {
                        // resetting downloading text view
                        viewModel.errorDownloadingText.value = false
                        downloadSolved(application)
                    }
                    R.id.subjectFragment -> {
                        // resetting downloading text view
                        viewModel.errorDownloadingText.value = false
                        downloadUnSolved(application)
                    }
                    R.id.askingChapterFragment -> {
                        // resetting downloading text view
                        viewModel.errorDownloadingText.value = false
                        downloadNotes(application)
                    }
                    R.id.holderFragment -> {
                        // resetting downloading text view
                        viewModel.errorDownloadingText.value = false
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

            // initialising With solved
            downloadSolved(application)
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

    }

    private fun downloadBooks(application: Application) {
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
    }

}