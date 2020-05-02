package jp.rei.andou.kanjio.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlin.collections.List
import kotlin.collections.filterNotNull
import kotlin.collections.map
import jp.rei.andou.kanjio.entities.Kanji as KanjiData

class JLPTKanjiRepository(val kanjiGroup: KanjiGroup) {

    fun getKanjiByLevel(level: Long): Flow<List<Kanji>> = /*Db.kanjiQuery {
        getKanjiListByLevel(level)
            .asFlow()
            .flowOn(Dispatchers.Default)
            .mapToList()
            .map { it.toUiKanji() }
    } ?: */emptyFlow()

    fun getKanjiGroupLevel(): Flow<Int> = /*Db.kanjiQuery {
        return@kanjiQuery getKanjiGroupGreatestLevel()
            .asFlow()
            .flowOn(Dispatchers.Default)
            .mapToOne()
            .map { it.toInt() }
    } ?:*/ emptyFlow()

    private fun List<KanjiData>.toUiKanji(): List<Kanji> {
        return map {
            Kanji(
                code = it.code ?: return@map null,
                onYomi = it.on_reading ?: return@map null,
                level = it.level?.toInt() ?: return@map null,
                meaning = it.meaning ?: return@map null,
                kunYomi = it.kun_reading
            )
        }.filterNotNull()
    }
}