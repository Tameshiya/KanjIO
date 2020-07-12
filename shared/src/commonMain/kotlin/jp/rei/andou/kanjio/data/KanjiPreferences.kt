package jp.rei.andou.kanjio.data

expect class KanjiPreferences {

    fun writeKanjiGroupLevel(kanjiGroupLevel: KanjiGroupLevel)

    fun readKanjiGroupLevel(): KanjiGroupLevel?

}