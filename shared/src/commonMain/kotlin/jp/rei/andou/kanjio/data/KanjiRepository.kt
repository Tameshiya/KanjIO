package jp.rei.andou.kanjio.data

import kotlinx.coroutines.flow.Flow

interface KanjiRepository {

    val kanjiGroup: KanjiGroup

    fun getKanjiByLevel(level: Int): Flow<List<Kanji>>

    fun getKanjiGroupLevel(): Flow<Int>

}
