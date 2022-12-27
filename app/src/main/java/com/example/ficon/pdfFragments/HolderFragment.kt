package com.example.ficon.pdfFragments

import android.app.Activity
import android.app.Application
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.ficon.R
import com.example.ficon.asking_coarse_fragments.viewmodel.LOG
import com.example.ficon.asking_coarse_fragments.viewmodel.SharedViewModel
import com.example.ficon.databinding.FragmentHolderBinding
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle
import java.io.File
import kotlin.properties.Delegates

class HolderFragment : Fragment() {

    private lateinit var binding: FragmentHolderBinding
    private val viewModel: SharedViewModel by activityViewModels()
    // Logic to enter Full screen mode
    private var fullScreenClicked by Delegates.notNull<Boolean>()
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

        // enter full screen mode
        fullScreenClicked = true
        binding.fullScreenButton.setOnClickListener {
            if (fullScreenClicked){
                enterFullScreen()
            }else{
                exitFullScreen()
            }
        }

        return binding.root
    }

    private fun exitFullScreen() {
        binding.fullScreenButton.setImageResource(R.drawable.enter_full_screen)
        binding.bottomNavigation.visibility = View.VISIBLE
        fullScreenClicked = true
        (activity as AppCompatActivity?)?.supportActionBar?.show()

    }

    private fun enterFullScreen() {
        binding.fullScreenButton.setImageResource(R.drawable.exit_full_screen)
        binding.bottomNavigation.visibility = View.GONE
        fullScreenClicked = false
        (activity as AppCompatActivity?)?.supportActionBar?.hide()

    }

    private fun downloadSyllabus(application: Application) {
        viewModel.errorDownloadingText.value = false
        viewModel.progressBarVisibility.value = true

        if (viewModel.syllabusDownloaded.value == true) {
            viewModel.progressBarVisibility.value = false
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
        viewModel.errorDownloadingText.value = false
        viewModel.progressBarVisibility.value = true

        if (viewModel.solvedDownloaded.value == true) {
            viewModel.progressBarVisibility.value = false
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
        viewModel.errorDownloadingText.value = false
        viewModel.progressBarVisibility.value = true

        if (viewModel.unSolvedDownloaded.value == true) {
            viewModel.progressBarVisibility.value = false
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
        viewModel.errorDownloadingText.value = false
        viewModel.progressBarVisibility.value = true

        if (viewModel.notesDownloaded.value == true) {
            viewModel.progressBarVisibility.value = false
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
        viewModel.errorDownloadingText.value = false
        viewModel.progressBarVisibility.value = true

        if (viewModel.booksDownloaded.value == true) {
            viewModel.progressBarVisibility.value = false
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