package jp.rei.andou.kanjio.di.modules

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import jp.rei.andou.kanjio.di.qualifiers.KanjiGroup

private const val KANJI_PREFS = "kanji_prefs"
private const val KANJI_SET_PREFS_KEY = "kanji_set_prefs_key"

@Module
class PreferencesModule(private val context: Context) {

    @Provides
    fun provideSharedPreferences(): SharedPreferences {
        return context.getSharedPreferences(KANJI_PREFS, MODE_PRIVATE)
    }

    @Provides
    @KanjiGroup
    fun provideKanjiGroupId(sharedPreferences: SharedPreferences): Int? {
        return sharedPreferences.getInt(KANJI_SET_PREFS_KEY, -1).takeIf { it != -1 }
    }

}
