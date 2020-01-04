package jp.rei.andou.kanjio.data.repositories.kanji

import io.reactivex.Maybe
import jp.rei.andou.kanjio.data.dao.KanjiDao
import jp.rei.andou.kanjio.data.entities.Kanji

class FreqKanjiRepository(private val kanjiDao: KanjiDao) : KanjiRepository {

    override fun getKanjiByLevel(level: Int): Maybe<List<Kanji>> {
        return kanjiDao.getFreqKanjiByLevel(level)
    }

}
