package jp.rei.andou.kanjio.presentation.view

import model.Kanji
import model.KanjiGroup

interface KanjiListView : CommonView {

    fun showList(kanjiList: List<Kanji>)
    fun setTitle(currentKanjiGroup: KanjiGroup)

}