package repositories

import model.Kanji
import model.KanjiGroup

interface KanjiRepository {

    val kanjiGroup: KanjiGroup

    fun getKanjiByLevel(level: Int): List<Kanji>

    fun getKanjiGroupLevel() : Int

}
