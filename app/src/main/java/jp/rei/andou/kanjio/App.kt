package jp.rei.andou.kanjio

import android.app.Application
import jp.rei.andou.kanjio.di.AppComponent
import jp.rei.andou.kanjio.di.DaggerAppComponent
import jp.rei.andou.kanjio.di.modules.DatabaseModule
import jp.rei.andou.kanjio.di.modules.KanjiModule
import jp.rei.andou.kanjio.di.modules.PreferencesModule


class App : Application() {

    lateinit var applicationComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        applicationComponent = buildComponent()
    }

    private fun buildComponent(): AppComponent {
        //todo move to koin
        return DaggerAppComponent.builder()
            .preferencesModule(PreferencesModule(this))
            .kanjiModule(KanjiModule())
            .databaseModule(DatabaseModule(this))
            .build()
    }
}