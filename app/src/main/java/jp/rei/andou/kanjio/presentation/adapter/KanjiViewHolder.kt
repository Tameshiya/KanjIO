package jp.rei.andou.kanjio.presentation.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.kanji_item.view.*

class KanjiViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val kanji: TextView = view.kanji
    val onyomi: TextView = view.onyomi
    val kunyomi: TextView = view.kunyomi
}