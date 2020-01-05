package jp.rei.andou.kanjio.data.repositories.kanji

import io.reactivex.Maybe
import io.reactivex.Single
import jp.rei.andou.kanjio.data.dao.KanjiDao
import jp.rei.andou.kanjio.data.entities.Kanji
import jp.rei.andou.kanjio.data.model.KanjiGroup

class KanjiRepository(val kanjiGroup: KanjiGroup, private val kanjiDao: KanjiDao) {

    fun getKanjiByLevel(level: Int): Maybe<List<Kanji>> {
        return kanjiDao.getKanjiListByLevel(level)
    }

    fun getKanjiGroupLevel() : Single<Int> {
        return kanjiDao.getKanjiGroupGreatestLevel()
    }

}
