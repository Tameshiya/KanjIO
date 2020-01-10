package jp.rei.andou.kanjio.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import jp.rei.andou.kanjio.R
import kotlinx.android.synthetic.main.kanji_item.view.*
import model.Kanji

class KanjiAdapter(private val list: List<Kanji>) : RecyclerView.Adapter<KanjiViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KanjiViewHolder {
        return KanjiViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.kanji_item, parent, false)
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: KanjiViewHolder, position: Int) {
        with(list[position]) {
            val kanji = "&#$code;" //todo move formatting to repo
            holder.kanji.text = HtmlCompat.fromHtml(kanji, HtmlCompat.FROM_HTML_MODE_LEGACY)
            holder.onyomi.text = onReading
            holder.kunyomi.text = kunReading
        }
    }
}

class KanjiViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val kanji: TextView = view.kanji
    val onyomi: TextView = view.onyomi
    val kunyomi: TextView = view.kunyomi
}