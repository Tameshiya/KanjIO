package jp.rei.andou.kanjio.di.modules

import android.content.Context
import android.util.Log
import androidx.room.Room
import dagger.Module
import dagger.Provides
import jp.rei.andou.kanjio.data.KanjiDatabase
import jp.rei.andou.kanjio.data.dao.KanjiDao
import java.io.DataInputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Singleton


@Module
class DatabaseModule(context: Context) {

    private val database: KanjiDatabase

    init {
        fetchAssetDatabase(context)
        database = Room.databaseBuilder(
            context,
            KanjiDatabase::class.java,
            KanjiDatabase.DATABASE_NAME
        ).build() //todo .openHelperFactory(new AssetSQLiteOpenHelperFactory())
    }

    /**
     * Fetch a default database from asset folder
     * if database file is already exists, so database is already fetched
     * @param applicationContext context of application
     */
    private fun fetchAssetDatabase(applicationContext: Context) {
        val databasePath: File = applicationContext.getDatabasePath(KanjiDatabase.DATABASE_NAME)
        if (databasePath.exists()) {
            return
        }
        try {
            DataInputStream(
                applicationContext.resources
                    .assets
                    .open(KanjiDatabase.DATABASE_NAME)
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
        val directoryCreated: Boolean = dbFile.parentFile?.mkdirs() ?: throw IOException("cannot get parent file to create directory")
        return if (directoryCreated || dbFile.createNewFile()) {
            dbFile
        } else {
            throw IOException("Не удалось создать файл базы данных")
        }
    }

    @Provides
    @Singleton
    fun provideDatabase(): KanjiDatabase {
        return database
    }

    @Provides
    @Singleton
    fun provideKanjiDao(): KanjiDao {
        return database.getKanjiDao()
    }
}