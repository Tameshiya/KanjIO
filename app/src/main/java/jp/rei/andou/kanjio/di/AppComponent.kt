package jp.rei.andou.kanjio.di

import dagger.Component
import jp.rei.andou.kanjio.KanjiListActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [DatabaseModule::class, KanjiModule::class])
interface AppComponent {

    fun inject(kanjiListActivity: KanjiListActivity)
}