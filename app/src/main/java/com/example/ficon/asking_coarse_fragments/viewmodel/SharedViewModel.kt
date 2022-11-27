package com.example.ficon.asking_coarse_fragments.viewmodel

import androidx.lifecycle.ViewModel
import com.example.ficon.asking_coarse_fragments.adapter_and_dataClass.SubjectsDataClass

class SharedViewModel : ViewModel() {

    private var mCoarse = "unknown"
    private var mYear = "unknown"
    private var mParts : SubjectsDataClass? = null

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
        return mParts
    }
    fun updateSubjectOptions(subjectList : SubjectsDataClass) {
        mParts = subjectList
    }
    fun getPathString(): String {
        val pathString = mCoarse + mYear
        val regex = Regex("[^A-Za-z0-9\t]")
        return regex.replace(pathString, "")
    }


}