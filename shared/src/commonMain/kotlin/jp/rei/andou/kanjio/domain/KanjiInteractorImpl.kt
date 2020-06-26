package jp.rei.andou.kanjio.domain

import jp.rei.andou.kanjio.data.Kanji
import jp.rei.andou.kanjio.data.KanjiGroup
import jp.rei.andou.kanjio.data.KanjiRepository

class KanjiInteractorImpl(
    private var kanjiRepository: KanjiRepository
) : KanjiInteractor {

    override fun getKanjiListByLevel(level: Int): List<Kanji> {
        return kanjiRepository.getKanjiByLevel(level)
    }

    override fun getCurrentKanjiGroup(): KanjiGroup = kanjiRepository.kanjiGroup

    override fun getCurrentKanjiGroupLevel(): Int {
        return kanjiRepository.getKanjiGroupLevel()
    }

}