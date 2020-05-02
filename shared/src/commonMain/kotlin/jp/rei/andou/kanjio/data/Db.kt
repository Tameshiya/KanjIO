package jp.rei.andou.kanjio.data

import com.squareup.sqldelight.db.SqlDriver
import jp.rei.andou.kanjio.KanjiDb
import jp.rei.andou.kanjio.entities.KanjiQueries

expect object Db {

    val ready: Boolean
    val instance: KanjiDb?
    fun dbSetup(driver: SqlDriver)
    fun <T> kanjiQuery(queries: KanjiQueries.() -> T): T?
} 