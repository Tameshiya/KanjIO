package jp.rei.andou.kanjio.data.dao

import androidx.room.Dao
import androidx.room.Query
import jp.rei.andou.kanjio.data.entities.Kanji
import kotlinx.coroutines.flow.Flow

@Dao
interface RevisedJouyouKanjiDao : KanjiDao {

    @Query(
        """
            SELECT MAX(jouyou_revised_level) FROM kanji_sequence
        """
    )
    override fun getKanjiGroupGreatestLevel() : Flow<Int>

    @Query(
        """
            SELECT k.* FROM kanji as k
            JOIN kanji_sequence ks ON k.code = ks.code AND ks.jouyou_revised_level = :level
            ORDER BY jouyou_revised_sequence
        """
    )
    override fun getKanjiListByLevel(level: Int): Flow<List<Kanji>>
}