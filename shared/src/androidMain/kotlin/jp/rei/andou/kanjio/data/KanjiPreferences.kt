package jp.rei.andou.kanjio.data

import android.content.SharedPreferences

actual class KanjiPreferences(private val sharedPreferences: SharedPreferences) {

    actual fun writeKanjiGroupLevel(kanjiGroupLevel: KanjiGroupLevel) {
        //todo
    }

    actual fun readKanjiGroupLevel(): KanjiGroupLevel? {
        //todo
        return null
    }

}