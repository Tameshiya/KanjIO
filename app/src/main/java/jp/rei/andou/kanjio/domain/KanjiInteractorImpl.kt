package jp.rei.andou.kanjio.domain

import domain.KanjiInteractor
import jp.rei.andou.kanjio.data.KanjiDaoFactory
import jp.rei.andou.kanjio.data.repositories.RoomKanjiRepository
import model.Kanji
import model.KanjiGroup
import repositories.KanjiRepository

class KanjiInteractorImpl(
    private var kanjiRepository: KanjiRepository,
    private val kanjiDaoFactory: KanjiDaoFactory
) : KanjiInteractor {

    override fun getKanjiListByLevel(level: Int): List<Kanji> {
        return kanjiRepository.getKanjiByLevel(level)
    }

    override fun getCurrentKanjiGroup(): KanjiGroup = kanjiRepository.kanjiGroup

    override fun changeKanjiGroup(kanjiGroup: KanjiGroup) {
        kanjiRepository = RoomKanjiRepository(kanjiGroup, kanjiDaoFactory.createKanjiDaoFor(kanjiGroup)) //todo move to different layer
    }

    override fun getCurrentKanjiGroupLevel(): Int {
        return kanjiRepository.getKanjiGroupLevel()
    }

}


