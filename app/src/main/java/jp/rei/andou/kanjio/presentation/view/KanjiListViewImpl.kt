package jp.rei.andou.kanjio.presentation.view

import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import jp.rei.andou.kanjio.data.entities.Kanji
import jp.rei.andou.kanjio.data.model.KanjiGroup
import jp.rei.andou.kanjio.presentation.adapter.KanjiAdapter

class KanjiListViewImpl(
    private val toolbar: Toolbar,
    private val recylerView: RecyclerView
) : KanjiListView {

    override fun showList(list: List<Kanji>) {
        recylerView.adapter = KanjiAdapter(list)
    }

    override fun setTitle(currentKanjiGroup: KanjiGroup) {
        toolbar.title = currentKanjiGroup.title
    }
}