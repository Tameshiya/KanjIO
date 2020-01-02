package jp.rei.andou.kanjio.di

import dagger.Module
import dagger.Provides
import jp.rei.andou.kanjio.data.dao.KanjiDao
import jp.rei.andou.kanjio.data.repositories.KanjiRepository

@Module
class KanjiModule {

    @Provides
    fun provideKanjiRepository(kanjiDao: KanjiDao): KanjiRepository = KanjiRepository(kanjiDao)

}