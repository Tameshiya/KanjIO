package jp.rei.andou.kanjio.presentation

import jp.rei.andou.kanjio.data.Kanji

interface KanjiListView : CommonView {

    fun showList(list: List<Kanji>)

    fun setTitle(kanjiGroupTitle: String)

}
