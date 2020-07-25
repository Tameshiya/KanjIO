package jp.rei.andou.kanjio.presentation.view

import android.view.LayoutInflater
import android.view.View
import android.widget.NumberPicker
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import jp.rei.andou.kanjio.R
import jp.rei.andou.kanjio.presentation.KanjiGroupView
import jp.rei.andou.kanjio.presentation.KanjiGroupsPresenter
import kotlinx.android.synthetic.main.dialog_list.view.*

//todo rewrite to custom view
//todo separate logic to presenter
class KanjiGroupViewImpl(
    layoutInflater: LayoutInflater,
    private val toolbar: Toolbar,
    private val kanjiGroupsPresenter: KanjiGroupsPresenter
) : KanjiGroupView {

    private val groups: NumberPicker
    private val levels: NumberPicker
    private val dialog: AlertDialog

    init {
        //todo support configuration changes
        val dialogListView: View = layoutInflater.inflate(R.layout.dialog_list, null)
        groups = dialogListView.groups
        levels = dialogListView.levels
        dialog = AlertDialog.Builder(layoutInflater.context)
            .setView(dialogListView)
            .setNegativeButton("取り消し") { _, _ ->}
            .setPositiveButton("決定") { dialog, _ ->
                val groupPosition: Int = groups.value
                val levelPosition: Int = levels.value
                kanjiGroupsPresenter.submitKanjiGroup(groupPosition, levelPosition)
                dialog.cancel()
            }.create()

        kanjiGroupsPresenter.attachView(this)
        kanjiGroupsPresenter.renderKanjiGroupsView()
    }

    override fun showKanjiGroupsDialog(
        kanjiGroupsLabels: List<String>,
        initialKanjiGroupsLevelsLabels: List<String>
    ) {
        groups.setup(kanjiGroupsLabels)
        levels.setup(initialKanjiGroupsLevelsLabels)

        groups.setOnValueChangedListener { _, _, newPosition ->
            kanjiGroupsPresenter.updateLevels(newPosition)
        }
        //todo fix dialog window leaking
        dialog.show()
    }

    override fun updateLevels(kanjiLevelsLabels: List<String>) = levels.setup(kanjiLevelsLabels)

    override fun updateTitle(kanjiGroupLabel: String) {
        toolbar.title = kanjiGroupLabel
    }

    private fun NumberPicker.setup(values: List<String>) {
        displayedValues = null
        maxValue = values.size - 1
        minValue = 0
        displayedValues = values.toTypedArray()
    }
}