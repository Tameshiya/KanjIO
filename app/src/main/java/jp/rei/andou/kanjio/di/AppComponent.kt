package jp.rei.andou.kanjio.di

import dagger.Component
import jp.rei.andou.kanjio.KanjiListActivity
import jp.rei.andou.kanjio.di.modules.DatabaseModule
import jp.rei.andou.kanjio.di.modules.KanjiModule
import jp.rei.andou.kanjio.di.modules.PreferencesModule
import javax.inject.Singleton

@Singleton
@Component(modules = [PreferencesModule::class, DatabaseModule::class, KanjiModule::class])
interface AppComponent {

    fun inject(kanjiListActivity: KanjiListActivity)
}