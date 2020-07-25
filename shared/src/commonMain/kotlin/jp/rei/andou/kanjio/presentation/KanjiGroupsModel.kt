package jp.rei.andou.kanjio.presentation

import jp.rei.andou.kanjio.data.KanjiGroup
import jp.rei.andou.kanjio.data.KanjiGroupLevel

class KanjiGroupsModel(
    val kanjiGroupLevels: Map<KanjiGroup, List<KanjiGroupLevel>>,
    private val currentGroupId: Int
) {
    val kanjiGroups: List<KanjiGroup> = kanjiGroupLevels.keys.toList()
    val currentKanjiGroup: KanjiGroup? = kanjiGroups.find { group -> group.id == currentGroupId }

    fun getCurrentKanjiGroupLevel() = kanjiGroupLevels[currentKanjiGroup]
}