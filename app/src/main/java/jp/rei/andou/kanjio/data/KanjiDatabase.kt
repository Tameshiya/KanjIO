package jp.rei.andou.kanjio.data

import androidx.room.Database
import androidx.room.RoomDatabase
import jp.rei.andou.kanjio.data.dao.KanjiDao
import jp.rei.andou.kanjio.data.entities.Kanji
import jp.rei.andou.kanjio.data.entities.KanjiSequence

@Database(version = 1, entities = [Kanji::class, KanjiSequence::class])
abstract class KanjiDatabase : RoomDatabase() {

    abstract fun getKanjiDao(): KanjiDao
}