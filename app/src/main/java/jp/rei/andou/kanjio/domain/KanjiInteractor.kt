package jp.rei.andou.kanjio.domain

import jp.rei.andou.kanjio.data.KanjiDaoFactory
import jp.rei.andou.kanjio.data.repositories.RoomKanjiRepository
import model.Kanji
import model.KanjiGroup
import repositories.KanjiRepository

class KanjiInteractor(
    private var kanjiRepository: KanjiRepository,
    private val kanjiDaoFactory: KanjiDaoFactory
) {

    fun getKanjiListByLevel(level: Int): List<Kanji> {
        return kanjiRepository.getKanjiByLevel(level)
    }

    fun getCurrentKanjiGroup(): KanjiGroup = kanjiRepository.kanjiGroup

    fun changeKanjiGroup(kanjiGroup: KanjiGroup) {
        kanjiRepository = RoomKanjiRepository(kanjiGroup, kanjiDaoFactory.createKanjiDaoFor(kanjiGroup)) //todo move to different layer
    }

    fun getCurrentKanjiGroupLevel(): Int {
        return kanjiRepository.getKanjiGroupLevel()
    }

}