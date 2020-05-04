package jp.rei.andou.kanjio.presentation.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import jp.rei.andou.kanjio.R
import kotlinx.android.synthetic.main.dialog_list_item.view.*

class KanjiFilterAdapter<T>(
    private val content: List<T>,
    private val titleRenderer: (position: Int) -> String,
    private val onGroupClickListener: (T) -> Unit
) : RecyclerView.Adapter<ListDialogViewHolder>() {

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
        holder.title.text = titleRenderer(position)
        holder.itemView.setOnClickListener {
            onGroupClickListener(content[position])
        }
    }

}

class ListDialogViewHolder(item: View) : RecyclerView.ViewHolder(item) {
    val title: TextView = item.title
}