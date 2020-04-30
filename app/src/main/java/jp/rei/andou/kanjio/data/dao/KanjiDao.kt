package jp.rei.andou.kanjio.data.dao

import jp.rei.andou.kanjio.data.entities.Kanji
import kotlinx.coroutines.flow.Flow

interface KanjiDao {

    fun getKanjiListByLevel(level: Int) : Flow<List<Kanji>>

    fun getKanjiGroupGreatestLevel() : Flow<Int>
}
