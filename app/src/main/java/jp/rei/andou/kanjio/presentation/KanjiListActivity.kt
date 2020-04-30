package jp.rei.andou.kanjio.presentation

import android.content.Context
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Path
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import jp.rei.andou.kanjio.App
import jp.rei.andou.kanjio.R
import jp.rei.andou.kanjio.presentation.presenter.KanjiPresenter
import jp.rei.andou.kanjio.presentation.view.KanjiListViewImpl
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_list.view.*
import kotlinx.android.synthetic.main.dialog_list_item.view.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import model.KanjiGroup
import javax.inject.Inject


class KanjiListActivity : AppCompatActivity() {

    @Inject
    lateinit var kanjiPresenter: KanjiPresenter

    override fun onCreate(savedInstanceState: Bundle?) = runBlocking {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (application as App).applicationComponent.inject(this@KanjiListActivity)

        setSupportActionBar(toolbar)

        kanjiPresenter.attachView(KanjiListViewImpl(toolbar, kanji_list), lifecycle)
        kanjiPresenter.init()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.kanji_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = runBlocking {
        when (item.itemId) {
            R.id.groups -> {
                //todo rewrite dialogs to mvp approach
                showGroupsDialog()
            }

            R.id.levels -> {
                MainScope().launch {
                    showLevelsDialog()
                }
            }
        }
        return@runBlocking super.onOptionsItemSelected(item)
    }

    private suspend fun showLevelsDialog() = kanjiPresenter.getKanjiGroupLevels().collect { maxLevel ->
        val dialogListView: View = layoutInflater.inflate(R.layout.dialog_list, null)
        val levels: RecyclerView = dialogListView.list
        levels.layoutManager = LinearLayoutManager(this@KanjiListActivity)
        val dialog = AlertDialog.Builder(this@KanjiListActivity).setView(dialogListView).create()
        val kanjiGroupLevels = (1 until maxLevel + 1).map { it.toString() }
        levels.adapter = KanjiFilterAdapter(
            content = kanjiGroupLevels,
            titleRenderer =
            { position -> kanjiGroupLevels[position] },
            onGroupClickListener =
            {
                MainScope().launch {
                    kanjiPresenter.changeNewKanjiGroupLevel(it.toInt())
                    dialog.dismiss()
                }
            }
        )
        //todo fix window leaking
        dialog.show()
    }

    private fun showGroupsDialog() {
        val dialogListView: View = layoutInflater.inflate(R.layout.dialog_list, null)
        val groups: RecyclerView = dialogListView.list
        groups.layoutManager = LinearLayoutManager(this)
        val dialog = AlertDialog.Builder(this).setView(dialogListView).create()
        val kanjiGroups = KanjiGroup.values().toList()
        groups.adapter =
            KanjiFilterAdapter(
                content = kanjiGroups,
                titleRenderer = { position -> kanjiGroups[position].title },
                onGroupClickListener = {
                    MainScope().launch {
                        kanjiPresenter.setNewKanjiGroup(it)
                        dialog.dismiss()
                    }
                })
        //todo fix window leaking
        dialog.show()
    }

    class ListDialogViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val title: TextView = item.title
    }

    class KanjiFilterAdapter<T>(
        private val content: List<T>,
        private val titleRenderer: (position: Int) -> String,
        private val onGroupClickListener: (T) -> Unit
    ) : RecyclerView.Adapter<ListDialogViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListDialogViewHolder {
            return ListDialogViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.dialog_list_item,
                    parent,
                    false
                )
            )
        }

        override fun getItemCount(): Int = content.size

        override fun onBindViewHolder(holder: ListDialogViewHolder, position: Int) {
            holder.title.text = titleRenderer(position)
            holder.itemView.setOnClickListener {
                onGroupClickListener(content[position])
            }
        }

    }

    /*todo private fun parsePathsDatas(pathDatas: String): Path {
        val newPath = Path()
        pathDatas.split("|").map {PathParser.createPathFromPathData(it)}.forEach(newPath::addPath)
        return newPath
    }*/
}

class KanjiView(context: Context, private val path: Path) : View(context) {

    private val paint = Paint()

    init {
        paint.strokeWidth = 20F
        paint.style = Paint.Style.STROKE
        paint.strokeCap = Paint.Cap.ROUND
        paint.strokeJoin = Paint.Join.ROUND
        val scaleMatrix = Matrix()
        scaleMatrix.setScale(10F, 10F, 0F, 0F)
        path.transform(scaleMatrix)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawPath(path, paint)
    }
}
