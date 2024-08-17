package com.segovia_jorge.proyectojimenez_segovia

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class FragmentosAdapter(
    private val fragmentos: List<Fragmento>,
    private val onFragmentoClick: (Fragmento) -> Unit
) : RecyclerView.Adapter<FragmentosAdapter.FragmentoViewHolder>() {

    inner class FragmentoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgFragmento: ImageView = view.findViewById(R.id.imgFragmento)

        fun bind(fragmento: Fragmento) {
            imgFragmento.setImageResource(fragmento.imageResId)
            itemView.setOnClickListener { onFragmentoClick(fragmento) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FragmentoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_fragmento, parent, false)
        return FragmentoViewHolder(view)
    }

    override fun onBindViewHolder(holder: FragmentoViewHolder, position: Int) {
        holder.bind(fragmentos[position])
    }

    override fun getItemCount() = fragmentos.size
}
