package jp.rei.andou.kanjio.data.dao

import jp.rei.andou.kanjio.data.entities.Kanji

interface KanjiDao {

    suspend fun getKanjiListByLevel(level: Int) : List<Kanji>

    suspend fun getKanjiGroupGreatestLevel() : Int
}
