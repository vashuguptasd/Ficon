package com.sgg.ficon.asking_coarse_fragments.adapter_and_dataClass

data class SubjectsDataClass(
    val name: String? = null,
    val nameHindi: String? = null,
    val part1English: String? = null,
    val part1Hindi: String? = null,
    val part2English: String? = null,
    val part2Hindi: String? = null,
    val part3English: String? = null,
    val part3Hindi: String? = null,
    val part4English: String? = null,
    val part4Hindi: String? = null

)

fun List<SubjectsDataClass>.asCoarseModel(): List<CoarseDataClass> {
    return map {
        CoarseDataClass(
            coarse = it.name.toString(),
            fullCoarseName = it.nameHindi.toString()
        )
    }
}