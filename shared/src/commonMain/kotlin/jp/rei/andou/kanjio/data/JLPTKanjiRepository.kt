package jp.rei.andou.kanjio.data

import jp.rei.andou.kanjio.entities.KanjiQueries
import kotlin.collections.map
import jp.rei.andou.kanjio.entities.Kanji as KanjiData

class JLPTKanjiRepository(
/*todo*/override val kanjiGroup: KanjiGroup,
        private val kanjiQueries: KanjiQueries
) : KanjiRepository {

    override fun getKanjiByLevel(level: Int): List<Kanji> {
        return kanjiQueries.getKanjiListByLevel(level.toLong()).executeAsList().toUiKanji()
    }

    override fun getKanjiGroupLevel(): Int {
        return kanjiQueries.getKanjiGroupGreatestLevel().executeAsOne().MAX?.toInt() ?: 0
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