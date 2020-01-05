package jp.rei.andou.kanjio.data.dao

import androidx.room.Dao
import androidx.room.Query
import io.reactivex.Maybe
import io.reactivex.Single
import jp.rei.andou.kanjio.data.entities.Kanji

@Dao
interface JouyouKanjiDao : KanjiDao {

    @Query(
        """
            SELECT k.* FROM kanji as k
            JOIN kanji_sequence ks ON k.code = ks.code AND ks.jouyou_level = :level
            ORDER BY jouyou_sequence
        """
    )
    override fun getKanjiListByLevel(level: Int): Maybe<List<Kanji>>

    @Query(
        """
            SELECT MAX(jouyou_level) FROM kanji_sequence
        """
    )
    override fun getKanjiGroupGreatestLevel() : Single<Int>
}