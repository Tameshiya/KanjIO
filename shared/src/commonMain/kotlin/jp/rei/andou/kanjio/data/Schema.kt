package jp.rei.andou.kanjio.data

import com.squareup.sqldelight.db.SqlDriver
import jp.rei.andou.kanjio.KanjiDb

fun createQueryWrapper(driver: SqlDriver): KanjiDb {
    //todo Adapters
    return KanjiDb(
        driver = driver
    )
}