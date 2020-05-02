package jp.rei.andou.kanjio.presentation.view

import jp.rei.andou.kanjio.data.Kanji
import jp.rei.andou.kanjio.data.KanjiGroup

interface KanjiListView : CommonView {

    fun showList(kanjiList: List<Kanji>)
    fun setTitle(currentKanjiGroup: KanjiGroup)

}