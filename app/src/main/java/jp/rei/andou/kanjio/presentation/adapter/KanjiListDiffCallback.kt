package jp.rei.andou.kanjio.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import jp.rei.andou.kanjio.data.Kanji

class KanjiListDiffCallback(
    private val oldKanjiList: List<Kanji>,
    private val newKanjiList: List<Kanji>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldKanjiList.size

    override fun getNewListSize(): Int = newKanjiList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldKanjiList[oldItemPosition] === newKanjiList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldKanjiList[oldItemPosition] == newKanjiList[newItemPosition]
    }

}