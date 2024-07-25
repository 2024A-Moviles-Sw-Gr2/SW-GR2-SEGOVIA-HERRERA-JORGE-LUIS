package com.segovia_jorge.a02_crud_vistas

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

class ListaVideojuegos : AppCompatActivity() {

    private var listaVideojuegos:ArrayList<Videojuego> = arrayListOf()
    private var adapter:ArrayAdapter<Videojuego>? = null
    private var posicion = -1
    private var estudioSeleccionado: EstudioVideojuego? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_juegos)


        estudioSeleccionado = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("estudioSeleccionado", EstudioVideojuego::class.java)
        } else {
            intent.getParcelableExtra<EstudioVideojuego>("estudioSeleccionado")
        }
        val juegosEstudio = findViewById<TextView>(R.id.titulo_Lista_Videojuegos)
        juegosEstudio.text = estudioSeleccionado!!.nombreEstudio

        DataBase.tables = SqliteHelper(this)
        val listaJuegos = findViewById<ListView>(R.id.lista_Videojuegos)
        listaVideojuegos = DataBase.tables!!.obtenerVideojuegosPorEstudio(estudioSeleccionado!!.id)
        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            listaVideojuegos
        )

        listaJuegos.adapter = adapter
        adapter!!.notifyDataSetChanged()

        val irCrearJuego = findViewById<Button>(R.id.crear_Videojuego_directo)
        irCrearJuego.setOnClickListener{
            goToActivity(CrearEditarJuego::class.java, null, estudioSeleccionado!!)
        }
        val regresar = findViewById<Button>(R.id.regresar)
        regresar.setOnClickListener{
            goToActivity(MainActivity::class.java)
        }

        registerForContextMenu(listaJuegos)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)

        val inflater = menuInflater
        inflater.inflate(R.menu.menu_juego, menu)

        val register = menuInfo as AdapterView.AdapterContextMenuInfo
        posicion = register.position
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.editarJuego -> {
                goToActivity(CrearEditarJuego::class.java, listaVideojuegos[posicion], estudioSeleccionado!!, false)
                return true
            }
            R.id.borrarJuego -> {
                openDialog(listaVideojuegos[posicion].id)
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    private fun goToActivity(
        activityClass: Class<*>,
        dataToPass: Videojuego? = null,
        dataToPass2: EstudioVideojuego? = null,
        create: Boolean = true
    ) {
        val intent = Intent(this, activityClass)
        if(dataToPass != null) {
            intent.apply {
                putExtra("juegoSeleccionado", dataToPass)
            }
        }
        if(dataToPass2 != null) {
            intent.apply {
                putExtra("estudioSeleccionado", dataToPass2)
            }
        }
        intent.apply {
            putExtra("create", create)
        }
        startActivity(intent)
    }

    private fun openDialog(registerIndex: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("¿Eliminar el juego?")
        builder.setPositiveButton(
            "Eliminar"
        ) { _, _ ->
            DataBase.tables!!.borrarVideojuego(registerIndex)
            listaVideojuegos.clear()
            listaVideojuegos.addAll(DataBase.tables!!.obtenerVideojuegosPorEstudio(estudioSeleccionado!!.id))
            adapter!!.notifyDataSetChanged()
        }
        builder.setNegativeButton("Cancelar", null)

        builder.create().show()
    }

}