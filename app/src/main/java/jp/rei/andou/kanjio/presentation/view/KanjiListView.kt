package jp.rei.andou.kanjio.presentation.view

import jp.rei.andou.kanjio.data.entities.Kanji
import jp.rei.andou.kanjio.data.model.KanjiGroup

interface KanjiListView : CommonView {

    fun showList(list: List<Kanji>)
    fun setTitle(currentKanjiGroup: KanjiGroup)

}