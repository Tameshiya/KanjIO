package jp.rei.andou.kanjio.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "kanji_sequence", indices = [
    Index(value = ["freg_sequence", "heisig_revised_sequence", "heisig_sequence"], unique = true)
])
class KanjiSequence {

    @PrimaryKey
    @ColumnInfo(name = "code")
    var code = 0
    @ColumnInfo(name = "freq_level", index = true)
    var freqLevel = 0
    @ColumnInfo(name = "freq_sequence")
    var freqSequence = 0
    @ColumnInfo(name = "heisig_level", index = true)
    var heisigLevel = 0
    @ColumnInfo(name = "heisig_revised_level", index = true)
    var heisigRevisedLevel = 0
    @ColumnInfo(name = "heisig_revised_sequence")
    var heisigRevisedSequence = 0
    @ColumnInfo(name = "heisig_sequence")
    var heisigSequence = 0
    @ColumnInfo(name = "jlpt_level", index = true)
    var jlptLevel = 0
    @ColumnInfo(name = "jlpt_revised_level", index = true)
    var jlptRevisedLevel = 0
    @ColumnInfo(name = "jlpt_revised_sequence", uniqueIndex = true) //todo
    var jlptRevisedSequence = 0
    @ColumnInfo(name = "jlpt_sequence", uniqueIndex = true)
    var jlptSequence = 0
    @ColumnInfo(name = "jouyou_level", index = true)
    var jouyouLevel = 0
    @ColumnInfo(name = "jouyou_revised_level", index = true)
    var jouyouRevisedLevel = 0
    @ColumnInfo(name = "jouyou_revised_sequence", uniqueIndex = true)
    var jouyouRevisedSequence = 0
    @ColumnInfo(name = "jouyou_sequence", uniqueIndex = true)
    var jouyouSequence = 0
    @ColumnInfo(name = "kanken_level", index = true)
    var kankenLevel = 0
    @ColumnInfo(name = "kanken_sequence", uniqueIndex = true)
    var kankenSequence = 0
    @ColumnInfo(name = "kic_level", index = true)
    var kicLevel = 0
    @ColumnInfo(name = "kic_sequence", uniqueIndex = true)
    var kicSequence = 0
    @ColumnInfo(name = "kklc_level", index = true)
    var kklcLevel = 0
    @ColumnInfo(name = "kklc_sequence", uniqueIndex = true)
    var kklcSequence = 0
    
}
