package jp.rei.andou.kanjio.data

import com.squareup.sqldelight.db.SqlDriver
import jp.rei.andou.kanjio.KanjiDb

actual class KanjiDatabase {

  private lateinit var driverRef: SqlDriver
  private lateinit var dbRef: KanjiDb

  actual val ready: Boolean
    get() = ::driverRef.isInitialized

  actual val instance: KanjiDb
    get() = dbRef

  actual fun createDatabase(driver: SqlDriver) {
    val db = createQueryWrapper(driver)
    driverRef = driver
    dbRef = db
  }

  internal fun dbClear() {
    driverRef.close()
  }
}