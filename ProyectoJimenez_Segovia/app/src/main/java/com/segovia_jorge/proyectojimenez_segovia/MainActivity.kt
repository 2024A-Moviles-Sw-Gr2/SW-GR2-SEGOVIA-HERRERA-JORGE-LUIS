package com.segovia_jorge.proyectojimenez_segovia

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnPersonajes = findViewById<ImageView>(R.id.btnPersonajes)
        val btnFragmentos = findViewById<ImageView>(R.id.btnFragmentos)

        btnPersonajes.setOnClickListener {
            val intent = Intent(this, personajes::class.java)
            startActivity(intent)
        }

        btnFragmentos.setOnClickListener {
            val intent = Intent(this, fragments_activity::class.java)
            startActivity(intent)
        }
    }
}
