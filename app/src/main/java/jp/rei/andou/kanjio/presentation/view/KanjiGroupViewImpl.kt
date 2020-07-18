package jp.rei.andou.kanjio.presentation.view

import android.view.LayoutInflater
import android.view.View
import android.widget.NumberPicker
import androidx.appcompat.app.AlertDialog
import jp.rei.andou.kanjio.R
import jp.rei.andou.kanjio.data.KanjiGroup
import jp.rei.andou.kanjio.data.KanjiGroupLevel
import jp.rei.andou.kanjio.presentation.KanjiGroupView
import jp.rei.andou.kanjio.presentation.KanjiGroupsPresenter
import jp.rei.andou.kanjio.presentation.KanjiPresenter
import kotlinx.android.synthetic.main.dialog_list.view.*

//todo rewrite to custom view
//todo separate logic to presenter
class KanjiGroupViewImpl(
    private val layoutInflater: LayoutInflater,
    private val kanjiGroupsPresenter: KanjiGroupsPresenter,
    private val kanjiPresenter: KanjiPresenter
) : KanjiGroupView {

    override fun showKanjiGroupView(
        kanjiGroupLevels: Map<KanjiGroup, List<KanjiGroupLevel>>,
        currentGroupId: Int
    ) {
        val currentKanjiGroup: KanjiGroup = kanjiGroupLevels.keys.find { it.id == currentGroupId } ?: return
        val dialogListView: View = layoutInflater.inflate(R.layout.dialog_list, null)
        val groups: NumberPicker = dialogListView.groups
        val kanjiGroups: List<KanjiGroup> = kanjiGroupLevels.keys.toList()
        val kanjiGroupsLabels: List<String> = kanjiGroups.map { it.title }
        groups.setup(kanjiGroupsLabels.toTypedArray())

        val levels: NumberPicker = dialogListView.levels
        val currentKanjiGroupLevel: List<String> = kanjiGroupLevels[currentKanjiGroup]?.map { it.name } ?: emptyList()
        levels.setup(currentKanjiGroupLevel.toTypedArray())
        groups.setOnValueChangedListener { picker, oldPosition, newPosition ->
            kanjiGroups[newPosition]
                .let { kanjiGroupLevels[it] }
                ?.let { levels.setup(it.map {level -> level.name }.toTypedArray()) }
        }
        val dialog = AlertDialog.Builder(layoutInflater.context)
            .setView(dialogListView)
            .setNegativeButton("取り消し") { _, _ ->}
            .setPositiveButton("決定") { dialog, _ ->
                val groupPosition: Int = groups.value
                val levelPosition: Int = levels.value
                kanjiGroups.getOrNull(groupPosition)?.let { selectedGroup ->
                    kanjiGroupLevels[selectedGroup]
                }?.let { kanjiGroupLevels: List<KanjiGroupLevel> ->
                    kanjiGroupLevels[levelPosition]
                }?.let { kanjiGroupLevel ->
                    kanjiGroupsPresenter.selectKanjiGroupLevel(kanjiGroupLevel)
                    kanjiPresenter.renderCurrentKanjiList(kanjiGroupLevel)
                }
                dialog.cancel()
            }.create()
        //todo fix window leaking
        dialog.show()
    }


    private fun NumberPicker.setup(values: Array<String>) {
        displayedValues = null
        maxValue = values.size - 1
        minValue = 0
        displayedValues = values
    }
}