package com.segovia_jorge.a02_crud_vistas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {

    private var estudios: ArrayList<EstudioVideojuego> = arrayListOf()
    private var adapter: ArrayAdapter<EstudioVideojuego>? = null
    private var posicion = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DataBase.tables = SqliteHelper(this)
        val lista_Estudios = findViewById<ListView>(R.id.lista_Estudios)
        estudios = DataBase.tables!!.obtenerTodosEstudios()
        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            estudios
        )

        lista_Estudios.adapter = adapter
        adapter!!.notifyDataSetChanged()

        val irCrearEstudio = findViewById<Button>(R.id.agregar_Estudio)
        irCrearEstudio.setOnClickListener {
            goToActivity(CrearEditarEstudio::class.java)
        }

        val botonMapa = findViewById<Button>(R.id.btn_ir_mapa)
        botonMapa.setOnClickListener {
            goToActivity(googleMaps::class.java)
        }

        registerForContextMenu(lista_Estudios)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)

        val inflater = menuInflater
        inflater.inflate(R.menu.menu_estudio, menu)

        val register = menuInfo as AdapterView.AdapterContextMenuInfo
        posicion = register.position
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.editar_Estudio -> {
                goToActivity(CrearEditarEstudio::class.java, estudios[posicion])
                true
            }
            R.id.borrar_Estudio -> {
                openDialog(estudios[posicion].id)
                true
            }
            R.id.ver_Juegos -> {
                goToActivity(ListaVideojuegos::class.java, estudios[posicion])
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    private fun goToActivity(
        activityClass: Class<*>,
        dataToPass: EstudioVideojuego? = null
    ) {
        val intent = Intent(this, activityClass)
        if (dataToPass != null) {
            intent.apply {
                putExtra("estudioSeleccionado", dataToPass)
            }
        }
        startActivity(intent)
    }

    private fun openDialog(registerIndex: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("¿Eliminar el estudio?")
        builder.setPositiveButton("Eliminar") { _, _ ->
            DataBase.tables!!.borrarEstudio(registerIndex)
            estudios.clear()
            estudios.addAll(DataBase.tables!!.obtenerTodosEstudios())
            adapter!!.notifyDataSetChanged()
        }
        builder.setNegativeButton("Cancelar", null)

        builder.create().show()
    }

}
