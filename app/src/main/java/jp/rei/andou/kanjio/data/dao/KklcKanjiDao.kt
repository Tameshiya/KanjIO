package jp.rei.andou.kanjio.data.dao

import androidx.room.Dao
import androidx.room.Query
import jp.rei.andou.kanjio.data.entities.Kanji

@Dao
interface KklcKanjiDao : KanjiDao{

    @Query(
        """
            SELECT k.* FROM kanji as k
            JOIN kanji_sequence ks ON k.code = ks.code AND ks.kklc_level = :level
            ORDER BY kklc_sequence
        """
    )
    override suspend fun getKanjiListByLevel(level: Int): List<Kanji>

    @Query(
        """
            SELECT MAX(jouyou_revised_level) FROM kanji_sequence
        """
    )
    override suspend fun getKanjiGroupGreatestLevel() : Int

}