package jp.rei.andou.kanjio.presentation

import jp.rei.andou.kanjio.data.KanjiGroup
import jp.rei.andou.kanjio.data.KanjiGroupLevel

interface KanjiGroupView : CommonView {

    fun showKanjiGroupView(
        kanjiGroupLevels: Map<KanjiGroup, List<KanjiGroupLevel>>,
        currentGroupId: Int
    )

}
