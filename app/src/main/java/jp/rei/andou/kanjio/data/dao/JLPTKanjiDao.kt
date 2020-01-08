package jp.rei.andou.kanjio.data.dao

import androidx.room.Dao
import androidx.room.Query

import jp.rei.andou.kanjio.data.entities.Kanji

@Dao
interface JLPTKanjiDao : KanjiDao {

    @Query(
        """
        SELECT k.* FROM kanji as k
        JOIN kanji_sequence ks ON k.code = ks.code AND ks.jlpt_level = :level
        ORDER BY jlpt_revised_sequence
        """
    )
    override suspend fun getKanjiListByLevel(level: Int): List<Kanji>

    @Query(
        """
            SELECT MAX(jlpt_level) FROM kanji_sequence
        """
    )
    override suspend fun getKanjiGroupGreatestLevel() : Int
}