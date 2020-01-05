package jp.rei.andou.kanjio.data.dao

import androidx.room.Dao
import androidx.room.Query
import io.reactivex.Maybe
import io.reactivex.Single
import jp.rei.andou.kanjio.data.entities.Kanji

@Dao
interface KankenKanjiDao : KanjiDao {

    @Query(
        """
            SELECT k.* FROM kanji as k
            JOIN kanji_sequence ks ON k.code = ks.code AND ks.kanken_level = :level
            ORDER BY kanken_sequence
        """
    )
    override fun getKanjiListByLevel(level: Int): Maybe<List<Kanji>>

    @Query(
        """
            SELECT MAX(kanken_level) FROM kanji_sequence
        """
    )
    override fun getKanjiGroupGreatestLevel() : Single<Int>
}