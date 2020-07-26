package jp.rei.andou.kanjio.presentation.view

import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import jp.rei.andou.kanjio.data.Kanji
import jp.rei.andou.kanjio.presentation.KanjiListView
import jp.rei.andou.kanjio.presentation.adapter.KanjiAdapter

class KanjiListViewImpl(
    private val toolbar: Toolbar,
    recylerView: RecyclerView
) : KanjiListView {

    private val kanjiListAdapter = KanjiAdapter()

    init {
        recylerView.adapter = kanjiListAdapter
        recylerView.layoutManager = LinearLayoutManager(recylerView.context)
        recylerView.setHasFixedSize(true)
    }

    override fun showList(list: List<Kanji>) {
        kanjiListAdapter.updateKanji(list)
    }

    override fun setTitle(kanjiGroupTitle: String) {
        toolbar.title = kanjiGroupTitle
    }
}