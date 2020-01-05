package jp.rei.andou.kanjio.data.dao

import androidx.room.Dao
import androidx.room.Query
import io.reactivex.Maybe
import io.reactivex.Single
import jp.rei.andou.kanjio.data.entities.Kanji

@Dao
interface RevisedHeisigKanjiDao : KanjiDao {

    @Query(
        """
            SELECT k.* FROM kanji as k
            JOIN kanji_sequence ks ON k.code = ks.code AND ks.heisig_revised_level = :level
            ORDER BY heisig_revised_sequence
        """
    )
    override fun getKanjiListByLevel(level: Int): Maybe<List<Kanji>>

    @Query(
        """
            SELECT MAX(heisig_revised_level) FROM kanji_sequence
        """
    )
    override fun getKanjiGroupGreatestLevel() : Single<Int>
}