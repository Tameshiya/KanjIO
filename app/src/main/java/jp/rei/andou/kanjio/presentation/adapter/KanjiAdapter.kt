package jp.rei.andou.kanjio.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import jp.rei.andou.kanjio.R
import jp.rei.andou.kanjio.data.Kanji

class KanjiAdapter : RecyclerView.Adapter<KanjiViewHolder>() {

    private val kanjiList = mutableListOf<Kanji>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KanjiViewHolder {
        return KanjiViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.kanji_item, parent, false)
        )
    }

    override fun getItemCount(): Int = kanjiList.size

    override fun onBindViewHolder(holder: KanjiViewHolder, position: Int) {
        with(kanjiList[position]) {
            val kanji = "&#$code;" //todo move formatting to repo
            holder.kanji.text = HtmlCompat.fromHtml(kanji, HtmlCompat.FROM_HTML_MODE_LEGACY)
            holder.onyomi.text = onYomi
            holder.kunyomi.text = kunYomi
        }
    }

    fun updateKanji(newKanjiList: List<Kanji>) {
        val diffCallback: DiffUtil.Callback = KanjiListDiffCallback(kanjiList, newKanjiList)
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(diffCallback)
        kanjiList.clear()
        kanjiList.addAll(newKanjiList)
        diffResult.dispatchUpdatesTo(this)
    }
}