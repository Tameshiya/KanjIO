package jp.rei.andou.kanjio.presentation

import jp.rei.andou.kanjio.data.Kanji
import jp.rei.andou.kanjio.data.KanjiGroup

interface KanjiListView : CommonView {

    fun showList(list: List<Kanji>)

    fun setTitle(currentKanjiGroup: KanjiGroup)

}
