package com.segovia_jorge.pedidosya

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class Item(val imageResId: Int, val text: String, val price: String? = null) {

    class ItemAdapter(private val itemList: List<Item>, private val viewType: Int) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        companion object {
            const val VIEW_TYPE_CATEGORY = 1
            const val VIEW_TYPE_SUGGESTION = 2
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return when (viewType) {
                VIEW_TYPE_CATEGORY -> {
                    val view = LayoutInflater.from(parent.context).inflate(R.layout.item_local_popular, parent, false)
                    CategoryViewHolder(view)
                }
                VIEW_TYPE_SUGGESTION -> {
                    val view = LayoutInflater.from(parent.context).inflate(R.layout.item_sugerencia, parent, false)
                    SuggestionViewHolder(view)
                }
                else -> throw IllegalArgumentException("Invalid view type")
            }
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val currentItem = itemList[position]
            when (holder) {
                is CategoryViewHolder -> {
                    holder.imageView.setImageResource(currentItem.imageResId)
                    holder.textView.text = currentItem.text
                }
                is SuggestionViewHolder -> {
                    holder.imageView.setImageResource(currentItem.imageResId)
                    holder.textView.text = currentItem.text
                    holder.priceView.text = currentItem.price
                }
            }
        }

        override fun getItemViewType(position: Int): Int {
            return viewType
        }

        override fun getItemCount() = itemList.size

        class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val imageView: ImageView = itemView.findViewById(R.id.item_image)
            val textView: TextView = itemView.findViewById(R.id.item_text)
        }

        class SuggestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val imageView: ImageView = itemView.findViewById(R.id.item_image)
            val textView: TextView = itemView.findViewById(R.id.item_text)
            val priceView: TextView = itemView.findViewById(R.id.item_precio)
        }
    }
}
