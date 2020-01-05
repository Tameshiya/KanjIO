package jp.rei.andou.kanjio.data

import androidx.room.Database
import androidx.room.RoomDatabase
import jp.rei.andou.kanjio.data.dao.*
import jp.rei.andou.kanjio.data.entities.Kanji
import jp.rei.andou.kanjio.data.entities.KanjiSequence

@Database(version = 1, entities = [Kanji::class, KanjiSequence::class])
abstract class KanjiDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_NAME = "kanji_db"
    }

    abstract fun getFreqKanjiDao(): FreqKanjiDao

    abstract fun getHeisigKanjiDao(): HeisigKanjiDao

    abstract fun getRevisedHeisigDao(): RevisedHeisigKanjiDao

    abstract fun getJlptKanjiDao(): JLPTKanjiDao

    abstract fun getRevisedJlptKanjiDao(): RevisedJLPTKanjiDao

    abstract fun getJouyouKanjiDao(): JouyouKanjiDao

    abstract fun getRevisedJouyouKanjiDao(): RevisedJouyouKanjiDao

    abstract fun getKicKanjiDao(): KicKanjiDao

    abstract fun getKklcKanjiDao(): KklcKanjiDao

    abstract fun getKankenKanjiDao(): KankenKanjiDao


}