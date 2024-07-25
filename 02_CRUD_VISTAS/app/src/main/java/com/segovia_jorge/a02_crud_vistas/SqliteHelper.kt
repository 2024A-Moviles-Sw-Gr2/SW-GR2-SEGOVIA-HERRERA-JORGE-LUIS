package com.segovia_jorge.a02_crud_vistas

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SqliteHelper(
    context: Context?
) : SQLiteOpenHelper(context, "AndroidApp", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        val crearTablaEstudio = """
            CREATE TABLE Estudio(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre_Estudio VARCHAR(100),
                empleados_Estudio INTEGER,
                pais_Estudio VARCHAR(50)
            );
        """.trimIndent()

        val crearVideojuego = """
            CREATE TABLE Videojuego(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre_Juego VARCHAR(100),
                genero_Juego VARCHAR(200),
                id_Estudio INTEGER,
                FOREIGN KEY (id_Estudio) REFERENCES Estudio(id) ON DELETE CASCADE
            );
        """.trimIndent()

        db?.execSQL(crearTablaEstudio)
        db?.execSQL(crearVideojuego)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

    fun obtenerTodosEstudios():ArrayList<EstudioVideojuego> {
        val lectureDB =readableDatabase
        val queryScript ="""
            SELECT * FROM Estudio
        """.trimIndent()
        val queryResult = lectureDB.rawQuery(
            queryScript,
            emptyArray()
        )
        val response = arrayListOf<EstudioVideojuego>()

        if(queryResult.moveToFirst()) {
            do {
                response.add(
                    EstudioVideojuego(
                        queryResult.getInt(0),
                        queryResult.getString(1),
                        queryResult.getInt(2),
                        queryResult.getString(3)
                    )
                )
            } while(queryResult.moveToNext())
        }
        queryResult.close()
        lectureDB.close()

        return response
    }

    fun obtenerVideojuegosPorEstudio(id_Estudio: Int):ArrayList<Videojuego> {
        val lectureDB =readableDatabase
        val queryScript ="""
            SELECT * FROM Videojuego WHERE id_Estudio=?
        """.trimIndent()
        val queryResult = lectureDB.rawQuery(
            queryScript,
            arrayOf(id_Estudio.toString())
        )
        val response = arrayListOf<Videojuego>()

        if(queryResult.moveToFirst()) {
            do {
                response.add(
                    Videojuego(
                        queryResult.getInt(0),
                        queryResult.getString(1),
                        queryResult.getString(2),
                        queryResult.getInt(3)
                    )
                )
            } while(queryResult.moveToNext())
        }
        queryResult.close()
        lectureDB.close()

        return response
    }

    fun crearEstudio(
        nombre_Estudio: String,
        empleados_Estudio: Int,
        pais_Estudio: String
    ): Boolean {
        val writeDB = writableDatabase
        val valuesToStore = ContentValues()
        valuesToStore.put("nombre_Estudio", nombre_Estudio)
        valuesToStore.put("empleados_Estudio", empleados_Estudio)
        valuesToStore.put("pais_Estudio", pais_Estudio)

        val storeResult = writeDB.insert(
            "Estudio",
            null,
            valuesToStore
        )
        writeDB.close()

        return storeResult.toInt() != -1
    }

    fun crearJuego(
        nombre_Juego: String,
        genero_Juego: String,
        id_Estudio: Int
    ): Boolean {
        val writeDB = writableDatabase
        val valuesToStore = ContentValues()
        valuesToStore.put("nombre_Juego", nombre_Juego)
        valuesToStore.put("genero_Juego", genero_Juego)
        valuesToStore.put("id_Estudio", id_Estudio)

        val storeResult = writeDB.insert(
            "Videojuego",
            null,
            valuesToStore
        )
        writeDB.close()

        return storeResult.toInt() != -1
    }

    fun actualizarEstudio(
        id: Int,
        nombre_Estudio: String,
        empleados_Estudio: Int,
        pais_Estudio: String
    ): Boolean {
        val writeDB = writableDatabase
        val valuesToUpdate = ContentValues()
        valuesToUpdate.put("nombre_Estudio", nombre_Estudio)
        valuesToUpdate.put("empleados_Estudio", empleados_Estudio)
        valuesToUpdate.put("pais_Estudio", pais_Estudio)

        val parametersUpdateQuery = arrayOf(id.toString())
        val updateResult = writeDB.update(
            "Estudio",
            valuesToUpdate,
            "id=?",
            parametersUpdateQuery
        )
        writeDB.close()

        return updateResult != -1
    }

    fun actualizarJuego(
        id: Int,
        nombre_Juego: String,
        genero_Juego: String,
        id_Estudio: Int
    ): Boolean {
        val writeDB = writableDatabase
        val valuesToUpdate = ContentValues()
        valuesToUpdate.put("nombre_Juego", nombre_Juego)
        valuesToUpdate.put("genero_Juego", genero_Juego)
        valuesToUpdate.put("id_Estudio", id_Estudio)

        val parametersUpdateQuery = arrayOf(id.toString())
        val updateResult = writeDB.update(
            "Videojuego",
            valuesToUpdate,
            "id=?",
            parametersUpdateQuery
        )
        writeDB.close()

        return updateResult != -1
    }

    fun borrarEstudio(id:Int): Boolean {
        val writeDB = writableDatabase
        val parametersDeleteQuery = arrayOf(id.toString())
        val deleteResult = writeDB.delete(
            "Estudio",
            "id=?",
            parametersDeleteQuery
        )
        writeDB.close()

        return deleteResult != -1
    }

    fun borrarVideojuego(id:Int): Boolean {
        val writeDB = writableDatabase
        val parametersDeleteQuery = arrayOf(id.toString())
        val deleteResult = writeDB.delete(
            "Videojuego",
            "id=?",
            parametersDeleteQuery
        )
        writeDB.close()

        return deleteResult != -1
    }

}