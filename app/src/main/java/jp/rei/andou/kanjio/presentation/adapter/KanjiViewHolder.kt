package jp.rei.andou.kanjio.presentation.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import jp.rei.andou.kanjio.R

class KanjiViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val kanji: TextView = view.findViewById(R.id.kanji)
    val onyomi: TextView = view.findViewById(R.id.onyomi)
    val kunyomi: TextView = view.findViewById(R.id.kunyomi)
}