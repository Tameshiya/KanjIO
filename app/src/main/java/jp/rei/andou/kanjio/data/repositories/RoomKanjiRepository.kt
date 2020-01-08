package jp.rei.andou.kanjio.data.repositories

import jp.rei.andou.kanjio.data.dao.KanjiDao
import jp.rei.andou.kanjio.data.mappers.toKanjiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import model.Kanji
import model.KanjiGroup
import repositories.KanjiRepository

class RoomKanjiRepository(
    override val kanjiGroup: KanjiGroup,
    private val kanjiDao: KanjiDao
) : KanjiRepository {

    override fun getKanjiByLevel(level: Int): List<Kanji> = runBlocking {
        withContext(Dispatchers.IO) {
            kanjiDao.getKanjiListByLevel(level).map { it.toKanjiModel() }
        }
    }


    override fun getKanjiGroupLevel(): Int = runBlocking {
        withContext(Dispatchers.IO) {
            kanjiDao.getKanjiGroupGreatestLevel()
        }
    }

}
