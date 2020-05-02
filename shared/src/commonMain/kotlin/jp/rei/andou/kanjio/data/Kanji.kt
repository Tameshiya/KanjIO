package jp.rei.andou.kanjio.data

data class Kanji(
    val code: Long,
    val kunYomi: String?,
    val level: Int = 0,
    val meaning: String,
    val onYomi: String
)
