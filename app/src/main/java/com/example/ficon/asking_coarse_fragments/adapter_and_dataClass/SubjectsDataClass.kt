package com.example.ficon.asking_coarse_fragments.adapter_and_dataClass

data class SubjectsDataClass(
    val name: String? = null,
    val nameHindi: String? = null,
    val part1english: String? = null,
    val part1hindi: String? = null,
    val part2english: String? = null,
    val part2hindi: String? = null,
    val part3english: String? = null,
    val part3hindi: String? = null
)

fun List<SubjectsDataClass>.asCoarseModel(): List<CoarseDataClass> {
    return map {
        CoarseDataClass(
            coarse = it.name.toString(),
            fullCoarseName = it.nameHindi.toString()
        )
    }
}