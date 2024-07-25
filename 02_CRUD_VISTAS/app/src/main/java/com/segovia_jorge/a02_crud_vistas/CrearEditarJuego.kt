package com.segovia_jorge.a02_crud_vistas

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class CrearEditarJuego : AppCompatActivity() {

    private lateinit var nombreJuego: EditText
    private lateinit var generoJuego: EditText
    private lateinit var idEstudio: EditText
    private lateinit var guardarVideojuego: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_crear_juego)

        initializeViews()
        val juegoSeleccionado = getParcelableExtraCompat<Videojuego>("juegoSeleccionado")
        val estudioSeleccionado = getParcelableExtraCompat<EstudioVideojuego>("estudioSeleccionado")
        val create = intent.getBooleanExtra("create", true)

        if (create) {
            idEstudio.setText(estudioSeleccionado?.id.toString())
            guardarVideojuego.setOnClickListener {
                DataBase.tables?.crearJuego(
                    nombreJuego.text.toString(),
                    generoJuego.text.toString(),
                    idEstudio.text.toString().toInt()
                )
                if (estudioSeleccionado != null) {
                    goToActivity(ListaVideojuegos::class.java, estudioSeleccionado)
                }
            }
        } else {
            juegoSeleccionado?.let {
                nombreJuego.setText(it.nombreJuego)
                generoJuego.setText(it.generoJuego)
                idEstudio.setText(it.idEstudio.toString())
            }
            guardarVideojuego.setOnClickListener {
                juegoSeleccionado?.let {
                    DataBase.tables?.actualizarJuego(
                        it.id,
                        nombreJuego.text.toString(),
                        generoJuego.text.toString(),
                        idEstudio.text.toString().toInt()
                    )
                }
                goToActivity(ListaVideojuegos::class.java, estudioSeleccionado!!)
            }
        }
    }

    private fun initializeViews() {
        nombreJuego = findViewById(R.id.nombre_Juego)
        generoJuego = findViewById(R.id.genero_Juego)
        idEstudio = findViewById(R.id.id_Estudio)
        guardarVideojuego = findViewById(R.id.guardar_Videojuego)
    }

    private inline fun <reified T : Parcelable> getParcelableExtraCompat(key: String): T? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(key, T::class.java)
        } else {
            intent.getParcelableExtra(key)
        }
    }

    private fun goToActivity(activityClass: Class<*>, dataToPass: EstudioVideojuego) {
        val intent = Intent(this, activityClass).apply {
            putExtra("estudioSeleccionado", dataToPass)
        }
        startActivity(intent)
    }
}
