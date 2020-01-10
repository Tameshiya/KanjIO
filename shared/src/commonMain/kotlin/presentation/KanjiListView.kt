package presentation

import model.Kanji
import model.KanjiGroup

interface KanjiListView {

    fun showList(list: List<Kanji>)

    fun setTitle(currentKanjiGroup: KanjiGroup)

}
