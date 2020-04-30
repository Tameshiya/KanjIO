package jp.rei.andou.kanjio.data.dao

import androidx.room.Dao
import androidx.room.Query
import jp.rei.andou.kanjio.data.entities.Kanji
import kotlinx.coroutines.flow.Flow

@Dao
interface FreqKanjiDao : KanjiDao {

    @Query(
        """
            SELECT k.* FROM kanji as k
            JOIN kanji_sequence ks ON k.code = ks.code AND ks.freq_level = :level
            ORDER BY freq_sequence
        """
    )
    override fun getKanjiListByLevel(level: Int): Flow<List<Kanji>>

    @Query(
        """
            SELECT MAX(freq_level) FROM kanji_sequence
        """
    )
    override fun getKanjiGroupGreatestLevel() : Flow<Int>

}
