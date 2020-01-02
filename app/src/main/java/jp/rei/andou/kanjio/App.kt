package jp.rei.andou.kanjio

import android.app.Application
import jp.rei.andou.kanjio.di.AppComponent
import jp.rei.andou.kanjio.di.DaggerAppComponent
import jp.rei.andou.kanjio.di.DatabaseModule
import jp.rei.andou.kanjio.di.KanjiModule


class App : Application() {

    lateinit var applicationComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        applicationComponent = buildComponent()
    }

    private fun buildComponent(): AppComponent {
        return DaggerAppComponent.builder()
            .kanjiModule(KanjiModule())
            .databaseModule(DatabaseModule(this))
            .build()
    }
}