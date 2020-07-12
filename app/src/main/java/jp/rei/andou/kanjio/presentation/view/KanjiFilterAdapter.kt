package jp.rei.andou.kanjio.presentation.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import jp.rei.andou.kanjio.R
import jp.rei.andou.kanjio.data.Kanji
import jp.rei.andou.kanjio.presentation.adapter.KanjiListDiffCallback
import kotlinx.android.synthetic.main.dialog_list_item.view.*

class KanjiFilterAdapter<T>(
    initialContent: List<T>,
    private val titleRenderer: ((position: Int) -> String)? = null,
    private val onGroupClickListener: ((T) -> Unit)? = null
) : RecyclerView.Adapter<ListDialogViewHolder>() {

    private val content: MutableList<T> = initialContent.toMutableList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListDialogViewHolder {
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
        holder.title.text = titleRenderer?.invoke(position)
        holder.itemView.setOnClickListener {
            onGroupClickListener?.invoke(content[position])
        }
    }

    fun updateKanji(newKanjiList: List<T>) {
        content.clear()
        content.addAll(newKanjiList)
        notifyDataSetChanged()
    }

}

class ListDialogViewHolder(item: View) : RecyclerView.ViewHolder(item) {
    val title: TextView = item.title
}