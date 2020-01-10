package jp.rei.andou.kanjio.di.modules

import dagger.Module
import dagger.Provides
import domain.KanjiInteractor
import jp.rei.andou.kanjio.data.KanjiDaoFactory
import jp.rei.andou.kanjio.data.repositories.RoomKanjiRepository
import jp.rei.andou.kanjio.domain.KanjiInteractorImpl
import model.KanjiGroup
import repositories.KanjiRepository

@Module
class KanjiModule {

    @Provides
    fun provideKanjiRepository(kanjiGroup: KanjiGroup, kanjiDaoFactory: KanjiDaoFactory): KanjiRepository {
        return RoomKanjiRepository(kanjiGroup, kanjiDaoFactory.createKanjiDaoFor(kanjiGroup))
    }

    @Provides
    fun provideKanjiInteractor(
        kanjiRepository: KanjiRepository,
        kanjiDaoFactory: KanjiDaoFactory
    ): KanjiInteractor {
        return KanjiInteractorImpl(kanjiRepository, kanjiDaoFactory)
    }

}