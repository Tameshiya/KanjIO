package jp.rei.andou.kanjio.data.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(
    tableName = "kanji_sequence",
    indices = [
        Index(
            value = [
                "freq_sequence",
                "heisig_revised_sequence",
                "heisig_sequence",
                "jlpt_revised_sequence",
                "jlpt_sequence",
                "jouyou_revised_sequence",
                "jouyou_sequence",
                "kanken_sequence",
                "kic_sequence",
                "kklc_sequence"
            ],
            unique = true
        )
    ]
)
@Parcelize
data class KanjiSequence(
    @PrimaryKey
    @ColumnInfo(name = "code")
    val code: Int,

    @ColumnInfo(name = "freq_level", index = true)
    val freqLevel: Int,

    @ColumnInfo(name = "freq_sequence")
    val freqSequence: Int,

    @ColumnInfo(name = "heisig_level", index = true)
    val heisigLevel: Int,

    @ColumnInfo(name = "heisig_revised_level", index = true)
    val heisigRevisedLevel: Int,

    @ColumnInfo(name = "heisig_revised_sequence")
    val heisigRevisedSequence: Int,

    @ColumnInfo(name = "heisig_sequence")
    val heisigSequence: Int,

    @ColumnInfo(name = "jlpt_level", index = true)
    val jlptLevel: Int,

    @ColumnInfo(name = "jlpt_revised_level", index = true)
    val jlptRevisedLevel: Int,

    @ColumnInfo(name = "jlpt_revised_sequence")
    val jlptRevisedSequence: Int,

    @ColumnInfo(name = "jlpt_sequence")
    val jlptSequence: Int,

    @ColumnInfo(name = "jouyou_level", index = true)
    val jouyouLevel: Int,

    @ColumnInfo(name = "jouyou_revised_level", index = true)
    val jouyouRevisedLevel: Int,

    @ColumnInfo(name = "jouyou_revised_sequence")
    val jouyouRevisedSequence: Int,

    @ColumnInfo(name = "jouyou_sequence")
    val jouyouSequence: Int,

    @ColumnInfo(name = "kanken_level", index = true)
    val kankenLevel: Int,

    @ColumnInfo(name = "kanken_sequence")
    val kankenSequence: Int,

    @ColumnInfo(name = "kic_level", index = true)
    val kicLevel: Int,

    @ColumnInfo(name = "kic_sequence")
    val kicSequence: Int,

    @ColumnInfo(name = "kklc_level", index = true)
    val kklcLevel: Int,

    @ColumnInfo(name = "kklc_sequence")
    val kklcSequence: Int
) : Parcelable