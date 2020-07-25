package jp.rei.andou.kanjio.presentation

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import jp.rei.andou.kanjio.App
import jp.rei.andou.kanjio.R
import jp.rei.andou.kanjio.data.KanjiGroupLevel
import jp.rei.andou.kanjio.presentation.view.KanjiGroupViewImpl
import jp.rei.andou.kanjio.presentation.view.KanjiListViewImpl
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import javax.inject.Inject

class KanjiListActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    @Inject
    lateinit var kanjiPresenter: KanjiPresenter
    @Inject
    lateinit var kanjiGroupsPresenter: KanjiGroupsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (application as App).applicationComponent.inject(this)

        setSupportActionBar(toolbar)

        //todo private val kanjiGroupLevelChannel: ReceiveChannel<KanjiGroupLevel>にて片方は情報を発信し、漢字一覧のRENDERERが受けるのはいかが？
        kanjiGroupsPresenter.onKanjiGroupLevelSelectedListener = { kanjiGroupLevel: KanjiGroupLevel ->
            kanjiPresenter.renderCurrentKanjiList(kanjiGroupLevel)
        }

        kanjiPresenter.attachView(KanjiListViewImpl(toolbar, kanji_list))
        kanjiPresenter.startFlow()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.kanji_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.groups -> createKanjiGroupView()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun createKanjiGroupView(): KanjiGroupView {
        return KanjiGroupViewImpl(layoutInflater, toolbar, kanjiGroupsPresenter)
    }
}
