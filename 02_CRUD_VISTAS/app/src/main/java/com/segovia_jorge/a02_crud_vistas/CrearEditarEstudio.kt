package com.segovia_jorge.a02_crud_vistas

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class CrearEditarEstudio : AppCompatActivity() {

    private lateinit var nombreEstudio: EditText
    private lateinit var empleadosEstudio: EditText
    private lateinit var paisEstudio: EditText
    private lateinit var latitudEstudio: EditText
    private lateinit var longitudEstudio: EditText
    private lateinit var guardarEstudio: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_crear_estudio)

        initializeViews()
        val estudioSeleccionado = getParcelableExtraCompat<EstudioVideojuego>("estudioSeleccionado")

        if (estudioSeleccionado != null) {
            populateFields(estudioSeleccionado)
            guardarEstudio.setOnClickListener {
                DataBase.tables?.actualizarEstudio(
                    estudioSeleccionado.id,
                    nombreEstudio.text.toString(),
                    empleadosEstudio.text.toString().toInt(),
                    paisEstudio.text.toString(),
                    latitudEstudio.text.toString().toDouble(),
                    longitudEstudio.text.toString().toDouble()
                )
                goToActivity(MainActivity::class.java)
            }
        } else {
            guardarEstudio.setOnClickListener {
                DataBase.tables?.crearEstudio(
                    nombreEstudio.text.toString(),
                    empleadosEstudio.text.toString().toInt(),
                    paisEstudio.text.toString(),
                    latitudEstudio.text.toString().toDouble(),
                    longitudEstudio.text.toString().toDouble()
                )
                goToActivity(MainActivity::class.java)
            }
        }
    }

    private fun initializeViews() {
        nombreEstudio = findViewById(R.id.nombre_Estudio)
        empleadosEstudio = findViewById(R.id.empleados_Estudio)
        paisEstudio = findViewById(R.id.pais_Estudio)
        latitudEstudio = findViewById(R.id.latitud_Estudio)
        longitudEstudio = findViewById(R.id.longitud_Estudio)
        guardarEstudio = findViewById(R.id.guardar_Estudio)
    }

    private fun populateFields(estudio: EstudioVideojuego) {
        nombreEstudio.setText(estudio.nombreEstudio)
        empleadosEstudio.setText(estudio.empleadosEstudio.toString())
        paisEstudio.setText(estudio.paisEstudio)
        latitudEstudio.setText(estudio.latitudEstudio.toString())
        longitudEstudio.setText(estudio.longitudEstudio.toString())
    }

    private inline fun <reified T : Parcelable> getParcelableExtraCompat(key: String): T? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(key, T::class.java)
        } else {
            intent.getParcelableExtra(key)
        }
    }

    private fun goToActivity(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        startActivity(intent)
    }
}


