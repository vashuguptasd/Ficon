package com.sgg.ficon.pdfFragments.firestoreDataClass

import com.sgg.ficon.asking_coarse_fragments.adapter_and_dataClass.CoarseDataClass

data class FireStoreUnitsDataClass (

    val unit : String? = null,
    val unitNameEng : String? = null,
    val unitNameHin : String? = null,
    val solved : String? = null,
    val unSolved : String? = null,
    val syllabus : String? = null,
    val books : String? = null,
    val notes : String? = null,
    val copyright : String? = null

)

fun List<FireStoreUnitsDataClass>.asCoarseModel(): List<CoarseDataClass> {
    return map {
        CoarseDataClass(
            coarse = it.unitNameEng.toString(),
            fullCoarseName = it.unitNameHin.toString()
        )
    }
}