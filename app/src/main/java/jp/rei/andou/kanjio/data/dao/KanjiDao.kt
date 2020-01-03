package jp.rei.andou.kanjio.data.dao

import androidx.room.Dao
import androidx.room.Query
import io.reactivex.Maybe
import jp.rei.andou.kanjio.data.entities.Kanji

@Dao
interface KanjiDao {

    @Query("""
        SELECT k.* FROM kanji as k 
        JOIN kanji_sequence ks ON ks.jlpt_level = :jlptLevel 
        limit 100
        """)
    fun getKanjiListByJLPTLevel(jlptLevel: Int): Maybe<List<Kanji>>

}
