package jp.rei.andou.kanjio.presentation.view

import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import jp.rei.andou.kanjio.R
import jp.rei.andou.kanjio.data.KanjiGroup
import jp.rei.andou.kanjio.data.KanjiGroupLevel
import jp.rei.andou.kanjio.presentation.KanjiGroupView
import jp.rei.andou.kanjio.presentation.KanjiGroupsPresenter
import kotlinx.android.synthetic.main.dialog_list.view.*

//todo refactor whole view
class KanjiGroupViewImpl(
    private val layoutInflater: LayoutInflater,
    private val kanjiGroupsPresenter: KanjiGroupsPresenter
) : KanjiGroupView {

    override fun showKanjiGroupView(
        kanjiGroupLevels: Map<KanjiGroup, List<KanjiGroupLevel>>,
        currentGroupId: Int
    ) {
        val currentKanjiGroup: KanjiGroup = kanjiGroupLevels.keys.find { it.id == currentGroupId } ?: return
        val dialogListView: View = layoutInflater.inflate(R.layout.dialog_list, null)
        val groups: RecyclerView = dialogListView.groups
        groups.setup()
        val kanjiGroups: List<KanjiGroup> = kanjiGroupLevels.keys.toList()
        groups.adapter = KanjiFilterAdapter(
            initialContent = kanjiGroups,
            titleRenderer = { position -> kanjiGroups[position].title }
        )

        val levels: RecyclerView = dialogListView.levels
        levels.setup()
        val currentKanjiGroupLevel: List<KanjiGroupLevel> = kanjiGroupLevels[currentKanjiGroup] ?: emptyList()
        levels.adapter = KanjiFilterAdapter(
            initialContent = currentKanjiGroupLevel,
            titleRenderer = { position -> currentKanjiGroupLevel.getOrNull(position)?.name ?: "?" }
        )
        groups.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val groupPosition: Int? = recyclerView.getFirstVisiblePosition()
                if (groupPosition == null || groupPosition == -1) {
                    return
                }
                kanjiGroups[groupPosition]
                    .let { kanjiGroupLevels[it] }
                    ?.let { (levels.adapter as KanjiFilterAdapter<KanjiGroupLevel>).updateKanji(it) }
            }
        })
        val dialog = AlertDialog.Builder(layoutInflater.context)
            .setView(dialogListView)
            .setNegativeButton("取り消し") { _, _ ->}
            .setPositiveButton("決定") { dialog, _ ->
                val groupPosition: Int = groups.getFirstVisiblePosition() ?: return@setPositiveButton
                val levelPosition: Int = levels.getFirstVisiblePosition() ?: return@setPositiveButton
                kanjiGroups.getOrNull(groupPosition)?.let { selectedGroup ->
                    kanjiGroupLevels[selectedGroup]
                }?.let { kanjiGroupLevels: List<KanjiGroupLevel> ->
                    kanjiGroupLevels[levelPosition]
                }?.let { kanjiGroupLevel ->
                    kanjiGroupsPresenter.selectKanjiGroupLevel(kanjiGroupLevel)
                }
                dialog.cancel()
            }.create()
        //todo fix window leaking
        dialog.show()
    }

    private fun RecyclerView.getFirstVisiblePosition(): Int? {
        return (layoutManager as? LinearLayoutManager)?.findFirstCompletelyVisibleItemPosition()
    }

    private fun RecyclerView.setup() {
        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(this)
        layoutManager = LinearLayoutManager(context)
    }
}