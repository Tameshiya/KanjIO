package jp.rei.andou.kanjio.data

interface KanjiRepository {

    fun getKanjiByLevel(level: Int): List<Kanji>

}
