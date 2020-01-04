package jp.rei.andou.kanjio.di.modules

import dagger.Module
import dagger.Provides
import jp.rei.andou.kanjio.data.dao.KanjiDao
import jp.rei.andou.kanjio.data.model.KanjiGroup
import jp.rei.andou.kanjio.data.model.toRepository
import jp.rei.andou.kanjio.data.repositories.kanji.KanjiRepository
import jp.rei.andou.kanjio.domain.KanjiInteractor

@Module
class KanjiModule {

    @Provides
    fun provideKanjiRepository(kanjiGroup: KanjiGroup, kanjiDao: KanjiDao): KanjiRepository {
        return kanjiGroup.toRepository(kanjiDao)
    }

    @Provides
    fun provideKanjiInteractor(kanjiGroup: KanjiGroup, kanjiRepository: KanjiRepository, kanjiDao: KanjiDao/*todo incapsulate*/): KanjiInteractor {
        return KanjiInteractor(kanjiRepository, kanjiDao)
    }

}