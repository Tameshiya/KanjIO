package jp.rei.andou.kanjio.di.modules

import android.content.Context
import android.util.Log
import com.squareup.sqldelight.android.AndroidSqliteDriver
import dagger.Module
import dagger.Provides
import jp.rei.andou.kanjio.KanjiDb
import jp.rei.andou.kanjio.data.KanjiDatabase
import jp.rei.andou.kanjio.entities.GroupsLevelsQueries
import jp.rei.andou.kanjio.entities.KanjiGroupsQueries
import jp.rei.andou.kanjio.entities.KanjiQueries
import java.io.DataInputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Singleton


@Module
class DatabaseModule(context: Context) {

    private val kanjiDatabase = KanjiDatabase()

    init {
        if (!kanjiDatabase.ready) {
            prefetchDbFromAssets(context) //todo move as singleton
            kanjiDatabase.createDatabase(AndroidSqliteDriver(KanjiDb.Schema, name = "KanjiDb.db", context = context))
        }
    }

    @Provides
    @Singleton
    fun provideKanjiDao(): KanjiQueries {
        return kanjiDatabase.instance.kanjiQueries
    }

    @Provides
    @Singleton
    fun provideKanjiGroupsDao(): KanjiGroupsQueries {
        return kanjiDatabase.instance.kanjiGroupsQueries
    }

    @Provides
    @Singleton
    fun provideKanjiLevelsDao(): GroupsLevelsQueries {
        return kanjiDatabase.instance.groupsLevelsQueries
    }

    /**
     * Fetch a default database from asset folder
     * if database file is already exists, so database is already fetched
     * @param applicationContext context of application
     */
    private fun prefetchDbFromAssets(applicationContext: Context) {
        val databaseFileName = "${KanjiDb::class.simpleName}.db"
        val databasePath: File = applicationContext.getDatabasePath(databaseFileName)
        if (databasePath.exists()) {
            return
        }
        try {
            DataInputStream(
                applicationContext.resources.assets.open(databaseFileName)
            ).use { defaultDatabase ->
                FileOutputStream(createDatabaseFile(databasePath)).use { databaseStream ->
                    val byteCount: Int = defaultDatabase.available()
                    if (byteCount > 0) {
                        val bytes = ByteArray(byteCount)
                        defaultDatabase.readFully(bytes)
                        databaseStream.write(bytes)
                    }
                }
            }
        } catch (e: IOException) {
            Log.e("Database fetching", "FAILED WITH: " + e.message)
        }
    }

    @Throws(IOException::class)
    private fun createDatabaseFile(dbFile: File): File {
        val directoryCreated: Boolean = dbFile.parentFile?.mkdirs()
            ?: throw IOException("cannot get parent file to create directory")
        return if (directoryCreated || dbFile.createNewFile()) {
            dbFile
        } else {
            throw IOException("Не удалось создать файл базы данных")
        }
    }
}