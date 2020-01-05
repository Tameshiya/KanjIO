package jp.rei.andou.kanjio.data.dao

import io.reactivex.Maybe
import io.reactivex.Single
import jp.rei.andou.kanjio.data.entities.Kanji

interface KanjiDao {

    fun getKanjiListByLevel(level: Int) : Maybe<List<Kanji>>

    fun getKanjiGroupGreatestLevel() : Single<Int>
}
