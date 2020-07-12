package jp.rei.andou.kanjio.domain

import jp.rei.andou.kanjio.data.KanjiGroupLevel

interface UserInteractor {

    fun getCurrentKanjiGroupLevel(): KanjiGroupLevel

    fun selectKanjiGroupLevel(kanjiGroupLevel: KanjiGroupLevel)

}