package jp.rei.andou.kanjio.data

data class Kanji(
    val code: Long,
    val kunYomi: String?,
    val level: Int?,
    val meaning: String,
    val onYomi: String
)
