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

    private var mCoarse = "unknown"
    private var mYear = "unknown"
    private var listRealtimeDatabase : SubjectsDataClass? = null
    private var database: DatabaseReference = FirebaseDatabase.getInstance().reference
    private var _listFromServer = MutableLiveData<List<SubjectsDataClass>>()
    var listFromServer : LiveData<List<SubjectsDataClass>> = _listFromServer


    fun updateCoarse(coarse : String){
        mCoarse = coarse
    }
    fun updateYear(year : String){
        mYear = year
    }

    fun getCoarse(): String {
        return mCoarse
    }

    fun getYear() : String{
        return mYear
    }

    fun getSubjectsOptions() : SubjectsDataClass? {
        return listRealtimeDatabase
    }

    fun updateSubjectOptions(subjectList : SubjectsDataClass) {
        listRealtimeDatabase = subjectList
    }

    fun getPathString(): String {
        val pathString = mCoarse + mYear
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
                    Log.e("testApp", subList.toString())

                    //                    setUpRecyclerView(subList)
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

}