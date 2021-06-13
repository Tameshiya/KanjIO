package jp.rei.andou.kanjio.presentation

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import jp.rei.andou.kanjio.App
import jp.rei.andou.kanjio.R
import jp.rei.andou.kanjio.data.KanjiGroup
import jp.rei.andou.kanjio.presentation.view.KanjiFilterAdapter
import jp.rei.andou.kanjio.presentation.view.KanjiListViewImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class KanjiListActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    @Inject
    lateinit var kanjiPresenter: KanjiPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (application as App).applicationComponent.inject(this)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        kanjiPresenter.attachView(KanjiListViewImpl(toolbar, findViewById(R.id.kanji_list)))
        launch {
            kanjiPresenter.init()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel("Activity has been destroyed")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.kanji_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.groups -> {
                //todo rewrite dialogs to mvp approach
                showGroupsDialog()
            }

            R.id.levels -> launch {
                kanjiPresenter.getKanjiGroupLevels()
                    .collect { maxLevel -> showLevelsDialog(maxLevel) }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showLevelsDialog(maxLevel: Int) {
        val dialogListView: View = layoutInflater.inflate(R.layout.dialog_list, null)
        val levels: RecyclerView = dialogListView.findViewById(R.id.list) //todo cache
        levels.layoutManager = LinearLayoutManager(this@KanjiListActivity)
        val dialog = AlertDialog.Builder(this@KanjiListActivity).setView(dialogListView).create()
        val kanjiGroupLevels = (1 until maxLevel + 1).map { it.toString() }
        levels.adapter = KanjiFilterAdapter(
            content = kanjiGroupLevels,
            titleRenderer = { position -> kanjiGroupLevels[position] },
            onGroupClickListener = {
                launch {
                    kanjiPresenter.changeNewKanjiGroupLevel(it.toInt())
                }
                dialog.dismiss()
            }
        )
        //todo fix window leaking
        dialog.show()
    }

    private fun showGroupsDialog() {
        val dialogListView: View = layoutInflater.inflate(R.layout.dialog_list, null)
        val groups: RecyclerView = dialogListView.findViewById(R.id.list) //todo cache
        groups.layoutManager = LinearLayoutManager(this)
        val dialog = AlertDialog.Builder(this).setView(dialogListView).create()
        val kanjiGroups = KanjiGroup.values().toList()
        groups.adapter =
            KanjiFilterAdapter(
                content = kanjiGroups,
                titleRenderer = { position -> kanjiGroups[position].title },
                onGroupClickListener = {
                    launch {
                        kanjiPresenter.setNewKanjiGroup(it)
                    }
                    dialog.dismiss()
                })
        //todo fix window leaking
        dialog.show()
    }
}
