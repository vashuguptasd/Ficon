package com.example.ficon.asking_coarse_fragments.viewmodel

import android.app.Application
import android.content.Context
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.downloader.Error
import com.downloader.OnDownloadListener
import com.downloader.PRDownloader
import com.example.ficon.asking_coarse_fragments.adapter_and_dataClass.SubjectsDataClass
import com.example.ficon.pdfFragments.firestoreDataClass.FireStoreUnitsDataClass
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import java.io.File
const val LOG = "testApp"
class SharedViewModel : ViewModel() {
    //Initialising Server
    private var database = FirebaseDatabase.getInstance().reference
    private var fireStore = FirebaseFirestore.getInstance()

    // for calling server specific branch
    var mCoarseSelected = ""
    var mYearSelected = ""
    var mSubjectSelected = ""
    var mPartSelected = ""

    private var listRealtimeDatabase: SubjectsDataClass? = null
    private var _listFromServer = MutableLiveData<List<SubjectsDataClass>>()
    var listFromServer: LiveData<List<SubjectsDataClass>> = _listFromServer

    // get fireStore data string
    private fun getFireStorePathString(): String {
        val pathString = mCoarseSelected + mYearSelected + mSubjectSelected + mPartSelected
        val regex = Regex("[^A-Za-z0-9\t]")
        return regex.replace(pathString, "")

    }


    fun updateCoarse(coarse: String) {
        mCoarseSelected = coarse
    }

    fun updateYear(year: String) {
        mYearSelected = year
    }

    fun getSubjectsOptions(): SubjectsDataClass? {
        return listRealtimeDatabase
    }

    fun updateSubjectOptions(subjectList: SubjectsDataClass) {
        listRealtimeDatabase = subjectList
    }

    // get path for subjects on realtime database
    private fun getRealtimeDatabasePathString(): String {
        val pathString = mCoarseSelected + mYearSelected
        val regex = Regex("[^A-Za-z0-9\t]")
        return regex.replace(pathString, "")
    }

    // get subjects and name of subjects
    fun getListFromRealTimeDatabase(
        activity: Application?
    ) {
        val dbRef = database.child("subjects").child(getRealtimeDatabasePathString())

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

                    _listFromServer.value = subList
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(activity, "Error Loading Subject", Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun getSubjectFromList(
        name: String,
        subjectList: MutableList<SubjectsDataClass>
    ): SubjectsDataClass? {
        return subjectList.find { it.name == name }
    }

    //to know which unit is clicked in Chapter fragment
    var chapterClickedOn : String = ""

    //for storing fireStore data
    private val fireStoreData = MutableLiveData<List<FireStoreUnitsDataClass>>()
    val mFireStoreData : LiveData<List<FireStoreUnitsDataClass>> = fireStoreData

    fun callFireStore() {

        fireStore.collection(getFireStorePathString()).get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val myObject = result.toObjects(FireStoreUnitsDataClass::class.java)
                    fireStoreData.value = myObject

                }
            }
            .addOnFailureListener { exception ->
                Log.w("testApp", "Error getting documents.", exception)
            }
    }

    // to get unit data only from list
    private fun getPreferredUnit( list : List<FireStoreUnitsDataClass>): FireStoreUnitsDataClass {
        val regex = Regex("[A-Za-z\n ]")
        val unitNo = regex.replace(chapterClickedOn, "")
        return list[unitNo.toInt()-1]
    }

    // show progressBar on loading data
    var progressBarVisibility = MutableLiveData<Boolean>()

    //pdf downloaded path
    var downloadedFilePathSyllabus = MutableLiveData<File>()

    //pdf downloaded path
    var downloadedFilePathSolved = MutableLiveData<File>()

    //pdf downloaded path
    var downloadedFilePathUnSolved = MutableLiveData<File>()

    //pdf downloaded path
    var downloadedFilePathNotes = MutableLiveData<File>()

    //pdf downloaded path
    var downloadedFilePathBooks = MutableLiveData<File>()

    // logic to download pdf only once
    var syllabusDownloaded = MutableLiveData<Boolean>()

    // logic to download pdf only once
    var solvedDownloaded = MutableLiveData<Boolean>()

    // logic to download pdf only once
    var unSolvedDownloaded = MutableLiveData<Boolean>()

    // logic to download pdf only once
    var notesDownloaded = MutableLiveData<Boolean>()

    // logic to download pdf only once
    var booksDownloaded = MutableLiveData<Boolean>()


    init {
        progressBarVisibility.value = true
        syllabusDownloaded.value = false
        solvedDownloaded.value = false
        unSolvedDownloaded.value = false
        notesDownloaded.value = false
        booksDownloaded.value = false
    }

    // download file with PRDownloader
    fun downloadPdfFromInternet(context : Context, category: String, dirPath: String, fileName: String) {

        PRDownloader.download(
            getPdfUrl(category),
            dirPath,
            fileName
        ).build()
            .start(object : OnDownloadListener {
                override fun onDownloadComplete() {
                    Toast.makeText(context, "downloadComplete", Toast.LENGTH_LONG)
                        .show()
                    val downloadedFile = File(dirPath, fileName)

                    when(category){
                        "syllabus" -> {downloadedFilePathSyllabus.value = downloadedFile
                            syllabusDownloaded.value = true}

                        "solved" -> {downloadedFilePathSolved.value = downloadedFile
                            solvedDownloaded.value = true}

                        "unSolved" -> {downloadedFilePathUnSolved.value = downloadedFile
                            unSolvedDownloaded.value = true}

                        "notes" -> {downloadedFilePathNotes.value = downloadedFile
                            notesDownloaded.value = true}

                        "book" -> {downloadedFilePathBooks.value = downloadedFile
                            booksDownloaded.value = true}

                    }

                }

                override fun onError(error: Error?) {
                    Toast.makeText(
                        context,
                        "Error in downloading file : $error",
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
            })
    }

    fun getRootDirPath(context: Context): String {
        return if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
            val file: File = ContextCompat.getExternalFilesDirs(
                context.applicationContext,
                null
            )[0]
            file.absolutePath
        } else {
            context.applicationContext.filesDir.absolutePath
        }
    }

    private fun getPdfUrl(category: String): String? {
        val syllabus = fireStoreData.value?.let { getPreferredUnit(it) } to FireStoreUnitsDataClass().syllabus
        val solved = fireStoreData.value?.let { getPreferredUnit(it) } to FireStoreUnitsDataClass().solved
        val unSolved = fireStoreData.value?.let { getPreferredUnit(it) } to FireStoreUnitsDataClass().unSolved
        val notes = fireStoreData.value?.let { getPreferredUnit(it) } to FireStoreUnitsDataClass().notes
        val book = fireStoreData.value?.let { getPreferredUnit(it) } to FireStoreUnitsDataClass().book



        return when (category){
            "syllabus" -> syllabus.first?.syllabus
            "solved" -> solved.first?.solved
            "unSolved" -> unSolved.first?.unSolved
            "notes" -> notes.first?.notes
            "book" -> book.first?.book

            else -> {"https://www.orimi.com/pdf-test.pdf"}
        }
    }




}