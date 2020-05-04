package jp.rei.andou.kanjio.data

import com.squareup.sqldelight.db.SqlDriver
import jp.rei.andou.kanjio.KanjiDb

expect class KanjiDatabase() {
    val ready: Boolean
    val instance: KanjiDb
    fun createDatabase(driver: SqlDriver)
}