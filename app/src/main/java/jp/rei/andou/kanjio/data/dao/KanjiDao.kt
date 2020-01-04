package jp.rei.andou.kanjio.data.dao

import androidx.room.Dao
import androidx.room.Query
import io.reactivex.Maybe
import jp.rei.andou.kanjio.data.entities.Kanji

@Dao
interface KanjiDao {

    @Query(
        """
            SELECT k.* FROM kanji as k
            JOIN kanji_sequence ks ON k.code = ks.code AND ks.jouyou_level = :level
            ORDER BY jouyou_sequence
        """
    )
    fun getJouyouKanjiByLevel(level: Int): Maybe<List<Kanji>>

    @Query(
        """
            SELECT k.* FROM kanji as k
            JOIN kanji_sequence ks ON k.code = ks.code AND ks.jouyou_revised_level = :level
            ORDER BY jouyou_revised_sequence
        """
    )
    fun getRevisedJouyouKanjiByLevel(level: Int): Maybe<List<Kanji>>

    @Query(
        """
        SELECT k.* FROM kanji as k
        JOIN kanji_sequence ks ON k.code = ks.code AND ks.jlpt_revised_level = :jlptLevel
        ORDER BY jlpt_revised_sequence
        """
    )
    fun getKanjiListByRevisedJLPTLevel(jlptLevel: Int): Maybe<List<Kanji>>

    @Query(
        """
        SELECT k.* FROM kanji as k
        JOIN kanji_sequence ks ON k.code = ks.code AND ks.jlpt_level = :jlptLevel
        ORDER BY jlpt_revised_sequence
        """
    )
    fun getKanjiListByJLPTLevel(jlptLevel: Int): Maybe<List<Kanji>>

    @Query(
        """
            SELECT k.* FROM kanji as k
            JOIN kanji_sequence ks ON k.code = ks.code AND ks.kklc_level = :level
            ORDER BY kklc_sequence
        """
    )
    fun getKklcKanjiByLevel(level: Int): Maybe<List<Kanji>>

    @Query(
        """
            SELECT k.* FROM kanji as k
            JOIN kanji_sequence ks ON k.code = ks.code AND ks.kic_level = :level
            ORDER BY kic_sequence
        """
    )
    fun getKicKanjiByLevel(level: Int): Maybe<List<Kanji>>

    @Query(
        """
            SELECT k.* FROM kanji as k
            JOIN kanji_sequence ks ON k.code = ks.code AND ks.freq_level = :level
            ORDER BY freq_sequence
        """
    )
    fun getFreqKanjiByLevel(level: Int): Maybe<List<Kanji>>

    @Query(
        """
            SELECT k.* FROM kanji as k
            JOIN kanji_sequence ks ON k.code = ks.code AND ks.heisig_level = :level
            ORDER BY heisig_sequence
        """
    )
    fun getHeisigKanjiByLevel(level: Int): Maybe<List<Kanji>>

    @Query(
        """
            SELECT k.* FROM kanji as k
            JOIN kanji_sequence ks ON k.code = ks.code AND ks.heisig_revised_level = :level
            ORDER BY heisig_revised_sequence
        """
    )
    fun getRevisedHeisigKanjiByLevel(level: Int): Maybe<List<Kanji>>

    @Query(
        """
            SELECT k.* FROM kanji as k
            JOIN kanji_sequence ks ON k.code = ks.code AND ks.kanken_level = :level
            ORDER BY kanken_sequence
        """
    )
    fun getKankenKanjiByLevel(level: Int): Maybe<List<Kanji>>

}
