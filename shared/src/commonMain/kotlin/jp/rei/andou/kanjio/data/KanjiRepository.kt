package jp.rei.andou.kanjio.data

interface KanjiRepository {

    val kanjiGroup: KanjiGroup

    fun getKanjiByLevel(level: Int): List<Kanji>

    fun getKanjiGroupLevel() : Int

}
