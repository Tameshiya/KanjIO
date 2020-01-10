package jp.rei.andou.kanjio

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
import domain.KanjiInteractor
import jp.rei.andou.kanjio.presentation.KanjiAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_list.view.*
import kotlinx.android.synthetic.main.dialog_list_item.view.*
import model.Kanji
import model.KanjiGroup
import presentation.KanjiListView
import presentation.KanjiPresenter
import javax.inject.Inject


class KanjiListActivity : AppCompatActivity(), KanjiListView {

    @Inject
    lateinit var kanjiInteractor: KanjiInteractor

    private lateinit var kanjiPresenter: KanjiPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (application as App).applicationComponent.inject(this)

        setSupportActionBar(toolbar)
        kanji_list.layoutManager = LinearLayoutManager(this)
        kanji_list.setHasFixedSize(true)
        kanjiPresenter = KanjiPresenter(kanjiInteractor).apply { setView(this@KanjiListActivity) }

        /* todo
            KanjiView(
                this,
                parsePathsDatas(
                    "M32.5,16.39c0.13,1.11,0.22,2.5-0.11,3.86c-2.11,8.62-10.26,25.28-20.08,36.96|M27.5,38c0.64,0.66,0.81,2.12,0.81,3.48c0,14.02-0.31,43.23-0.31,52.65|M47.65,13.83c3.83,2.19,9.9,8.98,10.86,12.38|M82.25,11.14c0.03,0.35,0.06,0.9-0.05,1.4c-0.65,2.96-4.39,9.45-9.5,13.42|M44.57,33.74c1.14,0.61,3.2,0.71,4.36,0.61C59,33.5,76.5,31,86.13,30.06c1.89-0.19,3.03,0.29,3.98,0.59|M45.28,50.19c1.13,0.6,3.17,0.66,4.32,0.6c10.15-0.54,21.6-1.98,35.48-3.35c1.87-0.18,2.92-0.19,4.45,0.3|M38.5,68.7c1.42,0.54,4.01,0.67,5.44,0.54c11.16-0.99,34.32-3.49,49.96-4.23c2.36-0.11,2.2-0.01,4.35,0.24|M65.26,33.25c0.95,0.5,1.52,2.25,1.71,3.25c0.19,1,0,55.25-0.19,61.5"
                )
            )
        )*/
    }

    override fun showList(list: List<Kanji>) {
        kanji_list.adapter = KanjiAdapter(list)
    }

    override fun setTitle(currentKanjiGroup: KanjiGroup) {
        toolbar.title = currentKanjiGroup.title
    }

    override fun onDestroy() {
        super.onDestroy()
        //todo handle cases like moxy
        kanjiPresenter.releaseView()
    }


    ///////////move below logic to mvp/////////////


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

            R.id.levels -> {
                showLevelsDialog()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showLevelsDialog() {

        val dialogListView: View = layoutInflater.inflate(R.layout.dialog_list, null)
        val levels: RecyclerView = dialogListView.list
        levels.layoutManager = LinearLayoutManager(this)
        val dialog = AlertDialog.Builder(this).setView(dialogListView).create()
        val maxLevel = kanjiPresenter.getKanjiGroupLevels()
        val kanjiGroupLevels = (1 until maxLevel + 1).map { it.toString() }
        levels.adapter = KanjiFilterAdapter(
            content = kanjiGroupLevels,
            titleRenderer = { position -> kanjiGroupLevels[position] },
            onGroupClickListener = {
                kanjiPresenter.changeNewKanjiGroupLevel(it.toInt())
                dialog.dismiss()
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
        groups.adapter = KanjiFilterAdapter(
            content = kanjiGroups,
            titleRenderer = { position -> kanjiGroups[position].title },
            onGroupClickListener = {
                kanjiPresenter.setNewKanjiGroup(it)
                dialog.dismiss()
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
