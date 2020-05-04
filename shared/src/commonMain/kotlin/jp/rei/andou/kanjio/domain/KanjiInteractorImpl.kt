package jp.rei.andou.kanjio.domain

import jp.rei.andou.kanjio.data.Kanji
import jp.rei.andou.kanjio.data.KanjiGroup
import jp.rei.andou.kanjio.data.KanjiRepository
import kotlinx.coroutines.flow.Flow

class KanjiInteractorImpl(
    private var kanjiRepository: KanjiRepository
) : KanjiInteractor {

    override fun getKanjiListByLevel(level: Int): Flow<List<Kanji>> {
        return kanjiRepository.getKanjiByLevel(level)
    }

    override fun getCurrentKanjiGroup(): KanjiGroup = kanjiRepository.kanjiGroup

    override fun getCurrentKanjiGroupLevel(): Flow<Int> {
        return kanjiRepository.getKanjiGroupLevel()
    }

}