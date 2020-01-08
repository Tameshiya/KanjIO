package jp.rei.andou.kanjio.data.dao

import androidx.room.Dao
import androidx.room.Query

import jp.rei.andou.kanjio.data.entities.Kanji

@Dao
interface KicKanjiDao : KanjiDao {

    @Query(
        """
            SELECT k.* FROM kanji as k
            JOIN kanji_sequence ks ON k.code = ks.code AND ks.kic_level = :level
            ORDER BY kic_sequence
        """
    )
    override suspend fun getKanjiListByLevel(level: Int): List<Kanji>


    @Query("""
        SELECT MAX(kic_level) FROM kanji_sequence
    """)
    override suspend fun getKanjiGroupGreatestLevel() : Int
}