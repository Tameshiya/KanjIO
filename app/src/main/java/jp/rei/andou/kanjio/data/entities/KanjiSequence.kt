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
        Index(name="jlpt_revised_level_idx", unique=false, value=["jlpt_revised_level"]),
        Index(name="kanji_sequence_kic_sequence_idx", unique=false, value=["kic_sequence"]),
        Index(name="kanji_sequence_heisig_revised_sequence_idx", unique=true, value=["heisig_revised_sequence"]),
        Index(name="jlpt_level_idx", unique=false, value=["jlpt_level"]),
        Index(name="kklc_sequence_idx", unique=true, value=["kklc_sequence"]),
        Index(name="heisig_sequence_idx", unique=true, value=["heisig_sequence"]),
        Index(name="freq_level_idx", unique=false, value=["freq_level"]),
        Index(name="heisig_revised_sequence_idx", unique=true, value=["heisig_revised_sequence"]),
        Index(name="kanji_sequence_heisig_sequence_idx", unique=true, value=["heisig_sequence"]),
        Index(name="jouyou_revised_sequence_idx", unique=true, value=["jouyou_revised_sequence"]),
        Index(name="kanken_level_idx", unique=false, value=["kanken_level"]),
        Index(name="kanji_sequence_jouyou_sequence_idx", unique=true, value=["jouyou_sequence"]),
        Index(name="kanji_sequence_kklc_level_idx", unique=false, value=["kklc_level"]),
        Index(name="kanji_sequence_freq_sequence_idx", unique=true, value=["freq_sequence"]),
        Index(name="kanji_sequence_heisig_level_idx", unique=false, value=["heisig_level"]),
        Index(name="freq_sequence_idx", unique=true, value=["freq_sequence"]),
        Index(name="kic_sequence_idx", unique=true, value=["kic_sequence"]),
        Index(name="kanji_sequence_jlpt_revised_level_idx", unique=false, value=["jlpt_revised_level"]),
        Index(name="kanji_sequence_jouyou_revised_sequence_idx", unique=true, value=["jouyou_revised_sequence"]),
        Index(name="jlpt_sequence_idx", unique=true, value=["jlpt_sequence"]),
        Index(name="jlpt_revised_sequence_idx", unique=true, value=["jlpt_revised_sequence"]),
        Index(name="kanken_sequence_idx", unique=true, value=["kanken_sequence"]),
        Index(name="kanji_sequence_kic_level_idx", unique=false, value=["kic_level"]),
        Index(name="kanji_sequence_jouyou_revised_level_idx", unique=false, value=["jouyou_revised_level"]),
        Index(name="heisig_level_idx", unique=false, value=["heisig_level"]),
        Index(name="kklc_level_idx", unique=false, value=["kklc_level"]),
        Index(name="kanji_sequence_kanken_level_idx", unique=false, value=["kanken_level"]),
        Index(name="jouyou_level_idx", unique=false, value=["jouyou_level"]),
        Index(name="jouyou_revised_level_idx", unique=false, value=["jouyou_revised_level"]),
        Index(name="kanji_sequence_jlpt_revised_sequence_idx", unique=true, value=["jlpt_revised_sequence"]),
        Index(name="kanji_sequence_jlpt_level_idx", unique=false, value=["jlpt_level"]),
        Index(name="kanji_sequence_jlpt_sequence_idx", unique=true, value=["jlpt_sequence"]),
        Index(name="kanji_sequence_kanken_sequence_idx", unique=true, value=["kanken_sequence"]),
        Index(name="kanji_sequence_kklc_sequence_idx", unique=true, value=["kklc_sequence"]),
        Index(name="kanji_sequence_freq_level_idx", unique=false, value=["freq_level"]),
        Index(name="kanji_sequence_jouyou_level_idx", unique=false, value=["jouyou_level"]),
        Index(name="heisig_revised_level_idx", unique=false, value=["heisig_revised_level"]),
        Index(name="kanji_sequence_heisig_revised_level_idx", unique=false, value=["heisig_revised_level"]),
        Index(name="kic_level_idx", unique=false, value=["kic_level"]),
        Index(name="jouyou_sequence_idx", unique=true, value=["jouyou_sequence"])
    ]
)
@Parcelize
data class KanjiSequence(
    @PrimaryKey
    @ColumnInfo(name = "code")
    val code: Int? = 0,

    @ColumnInfo(name = "freq_level")
    val freqLevel: Int? = 0,

    @ColumnInfo(name = "freq_sequence")
    val freqSequence: Int? = 0,

    @ColumnInfo(name = "heisig_level")
    val heisigLevel: Int? = 0,

    @ColumnInfo(name = "heisig_revised_level")
    val heisigRevisedLevel: Int? = 0,

    @ColumnInfo(name = "heisig_revised_sequence")
    val heisigRevisedSequence: Int? = 0,

    @ColumnInfo(name = "heisig_sequence")
    val heisigSequence: Int? = 0,

    @ColumnInfo(name = "jlpt_level")
    val jlptLevel: Int? = 0,

    @ColumnInfo(name = "jlpt_revised_level")
    val jlptRevisedLevel: Int? = 0,

    @ColumnInfo(name = "jlpt_revised_sequence")
    val jlptRevisedSequence: Int? = 0,

    @ColumnInfo(name = "jlpt_sequence")
    val jlptSequence: Int? = 0,

    @ColumnInfo(name = "jouyou_level")
    val jouyouLevel: Int? = 0,

    @ColumnInfo(name = "jouyou_revised_level")
    val jouyouRevisedLevel: Int? = 0,

    @ColumnInfo(name = "jouyou_revised_sequence")
    val jouyouRevisedSequence: Int? = 0,

    @ColumnInfo(name = "jouyou_sequence")
    val jouyouSequence: Int? = 0,

    @ColumnInfo(name = "kanken_level")
    val kankenLevel: Int? = 0,

    @ColumnInfo(name = "kanken_sequence")
    val kankenSequence: Int? = 0,

    @ColumnInfo(name = "kic_level")
    val kicLevel: Int? = 0,

    @ColumnInfo(name = "kic_sequence")
    val kicSequence: Int? = 0,

    @ColumnInfo(name = "kklc_level")
    val kklcLevel: Int? = 0,

    @ColumnInfo(name = "kklc_sequence")
    val kklcSequence: Int? = 0
) : Parcelable