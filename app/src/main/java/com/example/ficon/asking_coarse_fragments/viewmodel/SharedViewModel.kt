package com.example.ficon.asking_coarse_fragments.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ficon.asking_coarse_fragments.adapter_and_dataClass.SubjectsDataClass
import com.google.firebase.database.*

class SharedViewModel : ViewModel() {
    private var database: DatabaseReference = FirebaseDatabase.getInstance().reference

    private var mCoarseSelected = "unknown"
    private var mYearSelected = "unknown"
    var mSubjectSelected ="unknown"
    var mPartSelected = "unknown"
    private var listRealtimeDatabase : SubjectsDataClass? = null
    private var _listFromServer = MutableLiveData<List<SubjectsDataClass>>()
    var listFromServer : LiveData<List<SubjectsDataClass>> = _listFromServer

    fun getFireStorePathString(): String {
        val pathString =  mCoarseSelected + mYearSelected + mSubjectSelected + mPartSelected
        val regex = Regex("[^A-Za-z0-9\t]")
        return regex.replace(pathString, "")
    }

    fun updateCoarse(coarse : String){
        mCoarseSelected = coarse
    }

    fun updateYear(year : String){
        mYearSelected = year
    }

    fun getSubjectsOptions() : SubjectsDataClass? {
        return listRealtimeDatabase
    }

    fun updateSubjectOptions(subjectList : SubjectsDataClass) {
        listRealtimeDatabase = subjectList
    }

    fun getPathString(): String {
        val pathString = mCoarseSelected + mYearSelected
        val regex = Regex("[^A-Za-z0-9\t]")
        return regex.replace(pathString, "")
    }

    fun getListFromRealTimeDatabase(
        activity: Application?
    ) {
        val dbRef = database.child("subjects").child(getPathString())

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

                    //                    setUpRecyclerView(subList)
                    _listFromServer.value = subList
                    Log.e("testApp","list fetched is $subList")
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

}