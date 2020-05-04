package jp.rei.andou.kanjio.domain

import jp.rei.andou.kanjio.data.Kanji
import jp.rei.andou.kanjio.data.KanjiGroup
import kotlinx.coroutines.flow.Flow

interface KanjiInteractor {
    
    fun getKanjiListByLevel(level: Int): Flow<List<Kanji>>

    fun getCurrentKanjiGroup(): KanjiGroup

    fun getCurrentKanjiGroupLevel(): Flow<Int>
}