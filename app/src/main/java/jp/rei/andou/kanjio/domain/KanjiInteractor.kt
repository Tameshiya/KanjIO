package jp.rei.andou.kanjio.domain

import io.reactivex.Maybe
import jp.rei.andou.kanjio.data.dao.KanjiDao
import jp.rei.andou.kanjio.data.entities.Kanji
import jp.rei.andou.kanjio.data.model.KanjiGroup
import jp.rei.andou.kanjio.data.model.toRepository
import jp.rei.andou.kanjio.data.model.whatGroup
import jp.rei.andou.kanjio.data.repositories.kanji.KanjiRepository

class KanjiInteractor(
    private var kanjiRepository: KanjiRepository,
    private val kanjiDao: KanjiDao //todo incapsulate
) {

    fun getKanjiListByLevel(level: Int): Maybe<List<Kanji>> {
        return kanjiRepository.getKanjiByLevel(level)
    }

    fun getCurrentKanjiGroup(): KanjiGroup = kanjiRepository.whatGroup()

    fun changeKanjiGroup(kanjiGroup: KanjiGroup) {
        kanjiRepository = kanjiGroup.toRepository(kanjiDao)
    }

}