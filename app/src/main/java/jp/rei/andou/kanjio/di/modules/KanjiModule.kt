package jp.rei.andou.kanjio.di.modules

import dagger.Module
import dagger.Provides
import jp.rei.andou.kanjio.data.KanjiDaoFactory
import jp.rei.andou.kanjio.data.KanjiGroup
import jp.rei.andou.kanjio.data.repositories.kanji.KanjiRepository
import jp.rei.andou.kanjio.domain.KanjiInteractor

@Module
class KanjiModule {

    @Provides
    fun provideKanjiRepository(kanjiGroup: KanjiGroup, kanjiDaoFactory: KanjiDaoFactory): KanjiRepository {
        return KanjiRepository(kanjiGroup, kanjiDaoFactory.createKanjiDaoFor(kanjiGroup))
    }

    @Provides
    fun provideKanjiInteractor(
        kanjiRepository: KanjiRepository,
        kanjiDaoFactory: KanjiDaoFactory
    ): KanjiInteractor {
        return KanjiInteractor(kanjiRepository, kanjiDaoFactory)
    }

}