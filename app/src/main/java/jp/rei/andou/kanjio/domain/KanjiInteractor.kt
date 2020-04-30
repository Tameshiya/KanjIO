package jp.rei.andou.kanjio.domain

import jp.rei.andou.kanjio.data.KanjiDaoFactory
import jp.rei.andou.kanjio.data.repositories.kanji.KanjiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import model.Kanji
import model.KanjiGroup

class KanjiInteractor(
    private var kanjiRepository: KanjiRepository,
    private val kanjiDaoFactory: KanjiDaoFactory
) {

    fun getKanjiListByLevel(level: Int): Flow<List<Kanji>>{
        return kanjiRepository.getKanjiByLevel(level)
            .flowOn(Dispatchers.IO)
    }

    fun getCurrentKanjiGroup(): KanjiGroup = kanjiRepository.kanjiGroup

    fun changeKanjiGroup(kanjiGroup: KanjiGroup) {
        //todo move to different layer
        kanjiRepository = KanjiRepository(kanjiGroup, kanjiDaoFactory.createKanjiDaoFor(kanjiGroup))
    }

    fun getCurrentKanjiGroupLevel(): Flow<Int> {
        return kanjiRepository.getKanjiGroupLevel()
            .flowOn(Dispatchers.IO)
    }

}