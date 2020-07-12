package jp.rei.andou.kanjio.di

import dagger.Component
import jp.rei.andou.kanjio.di.modules.DatabaseModule
import jp.rei.andou.kanjio.di.modules.KanjiModule
import jp.rei.andou.kanjio.di.modules.PreferencesModule
import jp.rei.andou.kanjio.di.modules.UserModule
import jp.rei.andou.kanjio.presentation.KanjiListActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [PreferencesModule::class, DatabaseModule::class, KanjiModule::class, UserModule::class])
interface AppComponent {

    fun inject(kanjiListActivity: KanjiListActivity)
}