package jp.rei.andou.kanjio.data

import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.squareup.sqldelight.runtime.coroutines.mapToOne
import jp.rei.andou.kanjio.entities.KanjiQueries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlin.collections.map
import jp.rei.andou.kanjio.entities.Kanji as KanjiData

class JLPTKanjiRepository(
/*todo*/override val kanjiGroup: KanjiGroup,
        private val kanjiQueries: KanjiQueries
) : KanjiRepository {

    @ExperimentalCoroutinesApi
    override fun getKanjiByLevel(level: Int): Flow<List<Kanji>> {
        return kanjiQueries.getKanjiListByLevel(level.toLong())
            .asFlow()
            .flowOn(Dispatchers.Default)
            .mapToList()
            .map { it.toUiKanji() }
    }

    @ExperimentalCoroutinesApi
    override fun getKanjiGroupLevel(): Flow<Int> {
        return kanjiQueries.getKanjiGroupGreatestLevel()
            .asFlow()
            .flowOn(Dispatchers.Default)
            .mapToOne()
            .map { it.MAX?.toInt() ?: 0 }
    }

    private fun List<KanjiData>.toUiKanji(): List<Kanji> {
        return map {
            Kanji(
                code = it.code ?: return@map null,
                onYomi = it.on_reading ?: return@map null,
                meaning = it.meaning ?: return@map null,
                level = it.level?.toInt(),
                kunYomi = it.kun_reading
            )
        }.filterNotNull()
    }
}