package jp.rei.andou.kanjio.data.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "kanji", primaryKeys = ["code"])
class Kanji(

    @PrimaryKey
    @ColumnInfo(name = "code", index = true)
    val code: Int,

    @ColumnInfo(name = "custom_kun_reading")
    val customKunReading: String,

    @ColumnInfo(name = "kun_reading")
    val kunReading: String,

    @ColumnInfo(name = "level")
    val level: Int,

    @ColumnInfo(name = "meaning")
    val meaning: String,

    @ColumnInfo(name = "on_reading")
    val onReading: String,

    @ColumnInfo(name = "radicals")
    val radicals: String,

    @ColumnInfo(name = "reading")
    val reading: String,

    @ColumnInfo(name = "sequence")
    val sequence: Int,

    @ColumnInfo(name = "stroke_count")
    val strokeCount: Int,

    @ColumnInfo(name = "stroke_paths")
    val strokePaths: String,

    @ColumnInfo(name = "translation")
    val translation: String
) : Parcelable
