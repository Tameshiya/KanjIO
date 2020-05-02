package jp.rei.andou.kanjio.data

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import jp.rei.andou.kanjio.KanjiDb
import jp.rei.andou.kanjio.entities.KanjiQueries
import kotlin.native.concurrent.AtomicReference
import kotlin.native.concurrent.freeze

actual object Db {
  private val driverRef = AtomicReference<SqlDriver?>(null)
  private val dbRef = AtomicReference<KanjiDb?>(null)

  actual val ready: Boolean
    get() = driverRef.value != null
  
  actual val instance: KanjiDb?
    get() = dbRef.value

  actual fun dbSetup(driver: SqlDriver) {
    val db = createQueryWrapper(driver)
    driverRef.value = driver.freeze()
    dbRef.value = db.freeze()
  }

  actual fun <T> kanjiQuery(queries: KanjiQueries.() -> T): T? {
    return instance?.kanjiQueries?.let(queries)
  }

  //Called from Swift
  @Suppress("unused")
  fun defaultDriver() {
    dbSetup(NativeSqliteDriver(KanjiDb.Schema, "sampledb"))
  }

  internal fun dbClear() {
    driverRef.value!!.close()
    dbRef.value = null
    driverRef.value = null
  }
}