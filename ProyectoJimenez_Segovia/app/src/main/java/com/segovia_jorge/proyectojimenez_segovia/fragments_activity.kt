package com.segovia_jorge.proyectojimenez_segovia

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class fragments_activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragments)

        val fragmentosRecyclerView = findViewById<RecyclerView>(R.id.recyclerViewFragmentos)
        val fragmentos = listOf(
            Fragmento(R.drawable.gohan_fragment, "! Has sobrepasado la línea ¡", "Híbrido"),
            Fragmento(R.drawable.fusion_fragment, "¡Fusión HA!", "Guerrero Fusionado"),
            Fragmento(R.drawable.goku_gt_fragment, "Me sorprende mi fuerza", "GT"),
            Fragmento(R.drawable.goku_ssj4_fragment, "¡Je je je!", "SSJ4"),
            Fragmento(R.drawable.gogeta_movie_fragment, "¡HMMPH!", "Guerro Fusionado"),
            Fragmento(R.drawable.goku_ssj_god, "Un dios contra otro dios", "ki divino")
        )

        val adapter = FragmentosAdapter(fragmentos) { fragmento ->
            val intent = Intent(this, FragmentDetailActivity::class.java).apply {
                putExtra("EXTRA_FRAGMENTO", fragmento)
            }
            startActivity(intent)
        }

        fragmentosRecyclerView.adapter = adapter
        fragmentosRecyclerView.layoutManager = GridLayoutManager(this, 3)
    }
}
