package com.example.ficon.asking_coarse_fragments

import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {

    private var mCoarse = "unknown"
    private var mYear = "unknown"
    private var mOptional = "unknown"

    fun updateCoarse(coarse : String){
        mCoarse = coarse
    }
    fun updateYear(year : String){
        mYear = year
    }
    fun updateOptional(optional : String){
        mOptional = optional
    }
}