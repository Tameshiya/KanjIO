package jp.rei.andou.kanjio.data

import com.squareup.sqldelight.db.SqlDriver
import jp.rei.andou.kanjio.KanjiDb
import jp.rei.andou.kanjio.entities.KanjiQueries

actual object Db {
  private var driverRef: SqlDriver? = null
  private var dbRef: KanjiDb? = null

  actual val ready: Boolean
    get() = driverRef != null

  actual val instance: KanjiDb?
    get() = dbRef

  actual fun dbSetup(driver: SqlDriver) {
    val db = createQueryWrapper(driver)
    driverRef = driver
    dbRef = db
  }

  actual fun <T> kanjiQuery(queries: KanjiQueries.() -> T): T? {
    return instance?.kanjiQueries?.let(queries)
  }

  internal fun dbClear() {
    driverRef!!.close()
    dbRef = null
    driverRef = null
  }
}