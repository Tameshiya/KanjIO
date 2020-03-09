package jp.rei.andou.kanjio.di.modules

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import jp.rei.andou.kanjio.data.KanjiDaoFactory
import jp.rei.andou.kanjio.data.KanjiDatabase
import javax.inject.Singleton


@Module
class DatabaseModule(context: Context) {

    private val database: KanjiDatabase

    init {
        database = Room.databaseBuilder(
            context,
            KanjiDatabase::class.java,
            KanjiDatabase.DATABASE_NAME
        )
            .createFromAsset(KanjiDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideDatabase(): KanjiDatabase {
        return database
    }

    @Provides
    @Singleton
    fun provideDaoFactory(database: KanjiDatabase): KanjiDaoFactory {
        return KanjiDaoFactory(database)
    }
}