package domain

import model.Kanji
import model.KanjiGroup

interface KanjiInteractor {
    
    fun getKanjiListByLevel(level: Int): List<Kanji>

    fun getCurrentKanjiGroup(): KanjiGroup

    fun changeKanjiGroup(kanjiGroup: KanjiGroup)

    fun getCurrentKanjiGroupLevel(): Int
}