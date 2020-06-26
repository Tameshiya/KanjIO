package jp.rei.andou.kanjio.domain

import jp.rei.andou.kanjio.data.Kanji
import jp.rei.andou.kanjio.data.KanjiGroup

interface KanjiInteractor {
    
    fun getKanjiListByLevel(level: Int): List<Kanji>

    fun getCurrentKanjiGroup(): KanjiGroup

    fun getCurrentKanjiGroupLevel(): Int
}