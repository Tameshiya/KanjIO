package jp.rei.andou.kanjio.di.modules

import dagger.Module
import dagger.Provides
import jp.rei.andou.kanjio.data.JLPTKanjiRepository
import jp.rei.andou.kanjio.data.KanjiGroup
import jp.rei.andou.kanjio.data.KanjiRepository
import jp.rei.andou.kanjio.domain.KanjiInteractor
import jp.rei.andou.kanjio.domain.KanjiInteractorImpl
import jp.rei.andou.kanjio.entities.KanjiQueries
import jp.rei.andou.kanjio.presentation.KanjiPresenter

@Module
class KanjiModule {

    @Provides
    fun provideKanjiRepository(kanjiGroup: KanjiGroup, kanjiQueries: KanjiQueries): KanjiRepository {
        //todo implement factory for repositories or strategies for each KanjiGroup
        return JLPTKanjiRepository(kanjiGroup, kanjiQueries)
    }

    @Provides
    fun provideKanjiInteractor(kanjiRepository: KanjiRepository): KanjiInteractor {
        return KanjiInteractorImpl(kanjiRepository)
    }

    @Provides
    fun provideKanjiPresenter(kanjiInteractor: KanjiInteractor): KanjiPresenter {
        return KanjiPresenter(kanjiInteractor)
    }

}