package jp.rei.andou.kanjio.data.repositories.kanji

import jp.rei.andou.kanjio.data.dao.KanjiDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import model.Kanji
import model.KanjiGroup
import jp.rei.andou.kanjio.data.entities.Kanji as KanjiData

class KanjiRepository(val kanjiGroup: KanjiGroup, private val kanjiDao: KanjiDao) {

    fun getKanjiByLevel(level: Int): Flow<List<Kanji>> {
        return kanjiDao.getKanjiListByLevel(level).map { it.toUiKanji() } .flowOn(Dispatchers.IO)
    }

    fun getKanjiGroupLevel(): Flow<Int> {
        return kanjiDao.getKanjiGroupGreatestLevel().flowOn(Dispatchers.IO)
    }

}

private fun List<KanjiData>.toUiKanji(): List<Kanji> {
    return map { Kanji(it.code, it.kunReading, it.level, it.meaning, it.onReading) }
}
