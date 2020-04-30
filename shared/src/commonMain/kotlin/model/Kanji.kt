package model

data class Kanji(
    val code: Int? = 0,
    val kunReading: String?,
    val level: Int? = 0,
    val meaning: String? = null,
    val onReading: String? = null
)
