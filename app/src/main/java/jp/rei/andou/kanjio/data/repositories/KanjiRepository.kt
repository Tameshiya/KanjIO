package jp.rei.andou.kanjio.data.repositories

import io.reactivex.Maybe
import jp.rei.andou.kanjio.data.dao.KanjiDao
import jp.rei.andou.kanjio.data.entities.Kanji

class KanjiRepository(private val kanjiDao: KanjiDao) {

    fun getKanjiListByJLPTLevel(level: Int): Maybe<List<Kanji>> {
        return kanjiDao.getKanjiListByJLPTLevel(level)
    }
}