package jp.rei.andou.kanjio.domain

import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import jp.rei.andou.kanjio.data.KanjiDaoFactory
import jp.rei.andou.kanjio.data.entities.Kanji
import jp.rei.andou.kanjio.data.model.KanjiGroup
import jp.rei.andou.kanjio.data.repositories.kanji.KanjiRepository

class KanjiInteractor(
    private var kanjiRepository: KanjiRepository,
    private val kanjiDaoFactory: KanjiDaoFactory
) {

    fun getKanjiListByLevel(level: Int): Maybe<List<Kanji>> {
        return kanjiRepository.getKanjiByLevel(level)
    }

    fun getCurrentKanjiGroup(): KanjiGroup = kanjiRepository.kanjiGroup

    fun changeKanjiGroup(kanjiGroup: KanjiGroup) {
        kanjiRepository = KanjiRepository(kanjiGroup, kanjiDaoFactory.createKanjiDaoFor(kanjiGroup)) //todo move to different layer
    }

    fun getCurrentKanjiGroupLevel(): Single<Int> {
        return kanjiRepository.getKanjiGroupLevel()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}