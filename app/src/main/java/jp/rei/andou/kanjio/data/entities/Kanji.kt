package jp.rei.andou.kanjio.data.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.sql.Types.VARCHAR

@Parcelize
@Entity(
    tableName = "kanji",
    indices = [
        Index(name = "level_idx", value = ["level"]),
        Index(name = "sequence_idx", value = ["sequence"]),
        Index(name = "stroke_count_idx", value = ["stroke_count"])
    ]
)
data class Kanji(

    @PrimaryKey
    @ColumnInfo(name = "code")
    val code: Int? = 0,

    @ColumnInfo(name = "custom_kun_reading", typeAffinity = VARCHAR)
    val customKunReading: String? = null,

    @ColumnInfo(name = "custom_meaning", typeAffinity = VARCHAR)
    val customMeaning: String? = null,

    @ColumnInfo(name = "custom_on_reading", typeAffinity = VARCHAR)
    val customOnReading: String?,

    @ColumnInfo(name = "kun_reading", typeAffinity = VARCHAR)
    val kunReading: String?,

    @ColumnInfo(name = "level")
    val level: Int? = 0,

    @ColumnInfo(name = "meaning", typeAffinity = VARCHAR)
    val meaning: String? = null,

    @ColumnInfo(name = "on_reading", typeAffinity = VARCHAR)
    val onReading: String? = null,

    @ColumnInfo(name = "radicals", typeAffinity = VARCHAR)
    val radicals: String? = null,

    @ColumnInfo(name = "reading", typeAffinity = VARCHAR)
    val reading: String? = null,

    @ColumnInfo(name = "sequence")
    val sequence: Int? = 0,

    @ColumnInfo(name = "stroke_count")
    val strokeCount: Int? = 0,

    @ColumnInfo(name = "stroke_paths", typeAffinity = VARCHAR)
    val strokePaths: String? = null,

    @ColumnInfo(name = "decomposition", typeAffinity = VARCHAR)
    val decomposition: String? = null,

    @ColumnInfo(name = "translation", typeAffinity = VARCHAR)
    val translation: String? = null,

    @ColumnInfo(name = "stroke_highlights", typeAffinity = VARCHAR)
    val strokeHighlights: String? = null
) : Parcelable
