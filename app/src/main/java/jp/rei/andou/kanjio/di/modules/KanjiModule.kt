package jp.rei.andou.kanjio.di.modules

import dagger.Module
import dagger.Provides
import jp.rei.andou.kanjio.data.*
import jp.rei.andou.kanjio.domain.KanjiInteractor
import jp.rei.andou.kanjio.domain.KanjiInteractorImpl
import jp.rei.andou.kanjio.domain.UserInteractor
import jp.rei.andou.kanjio.entities.GroupsLevelsQueries
import jp.rei.andou.kanjio.entities.KanjiGroupsQueries
import jp.rei.andou.kanjio.entities.KanjiQueries
import jp.rei.andou.kanjio.presentation.KanjiGroupsPresenter
import jp.rei.andou.kanjio.presentation.KanjiPresenter

@Module
class KanjiModule {

    @Provides
    fun provideKanjiRepository(kanjiQueries: KanjiQueries): KanjiRepository {
        return KanjiRepositoryImpl(kanjiQueries)
    }

    @Provides
    fun provideKanjiGroupRepository(
        kanjiGroupsQueries: KanjiGroupsQueries,
        groupsLevelsQueries: GroupsLevelsQueries
    ): KanjiGroupRepository {
        return KanjiGroupRepositoryImpl(
            kanjiGroupsQueries, groupsLevelsQueries
        )
    }

    @Provides
    fun provideKanjiInteractor(
        kanjiRepository: KanjiRepository,
        groupRepository: KanjiGroupRepository
    ): KanjiInteractor {
        return KanjiInteractorImpl(kanjiRepository, groupRepository)
    }

    @Provides
    fun provideKanjiPresenter(
        kanjiInteractor: KanjiInteractor,
        userInteractor: UserInteractor
    ): KanjiPresenter {
        return KanjiPresenter(kanjiInteractor, userInteractor)
    }

    @Provides
    fun provideKanjiGroupsPresenter(
        userInteractor: UserInteractor,
        kanjiInteractor: KanjiInteractor
    ): KanjiGroupsPresenter {
        return KanjiGroupsPresenter(userInteractor, kanjiInteractor)
    }

}