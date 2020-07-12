package jp.rei.andou.kanjio.data

import platform.Foundation.NSCoder
import platform.Foundation.NSUserDefaults

private const val KANJI_GROUP_LEVEL_KEY: String = "KanjiGroupLevelKey"
actual class KanjiPreferences {

    private val nsUserDefaults: NSUserDefaults = NSUserDefaults.standardUserDefaults()
    private val test: NSCoder = NSCoder()

    actual fun writeKanjiGroupLevel(kanjiGroupLevel: KanjiGroupLevel) {
        //nsUserDefaults.setObject(kanjiGroupLevel, KANJI_GROUP_LEVEL_KEY)
    }

    actual fun readKanjiGroupLevel(): KanjiGroupLevel? {
        //todo nsUserDefaults.getObject
        return null
    }

}