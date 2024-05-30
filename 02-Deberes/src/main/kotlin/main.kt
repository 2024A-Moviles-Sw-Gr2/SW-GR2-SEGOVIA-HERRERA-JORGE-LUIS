import EstudioVideojuego.Companion.solicitarDatosEstudio
import EstudioVideojuego.Companion.solicitarNombreEstudio
import Videojuego.Companion.solicitarDatosVideojuego
import Videojuego.Companion.solicitarNombreVideojuego
import java.text.SimpleDateFormat
import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)
    val dateFormat = SimpleDateFormat("yyyy-MM-dd")

    while (true) {
        mostrarMenu()
        when (scanner.nextInt()) {
            1 -> {
                limpiarPantalla()
                val estudio = solicitarDatosEstudio(scanner, dateFormat)
                EstudioVideojuego.crearEstudioVideojuego(estudio)
            }
            2 -> {
                limpiarPantalla()
                EstudioVideojuego.leerEstudioVideojuego().forEach { estudio ->
                    println(estudio)
                    estudio.juegos.forEach { juego ->
                        println("  - $juego")
                    }
                }
            }
            3 -> {
                limpiarPantalla()
                val nombre = solicitarNombreEstudio(scanner)
                val nuevoEstudio = solicitarDatosEstudio(scanner, dateFormat)
                EstudioVideojuego.actualizarEstudioVideojuego(nombre, nuevoEstudio)
            }
            4 -> {
                limpiarPantalla()
                val nombre = solicitarNombreEstudio(scanner)
                EstudioVideojuego.borrarEstudioVideojuego(nombre)
            }
            5 -> {
                limpiarPantalla()
                val videojuego = solicitarDatosVideojuego(scanner, dateFormat)
                Videojuego.crearVideojuego(videojuego, videojuego.nombreEstudio)
            }
            6 -> {
                limpiarPantalla()
                Videojuego.leerVideojuego().forEach {
                    println(it)
                }
            }
            7 -> {
                limpiarPantalla()
                val nombreJuego = solicitarNombreVideojuego(scanner)
                val nuevoVideojuego = solicitarDatosVideojuego(scanner, dateFormat)
                Videojuego.actualizarVideojuego(nombreJuego, nuevoVideojuego)
            }
            8 -> {
                limpiarPantalla()
                val nombreJuego = solicitarNombreVideojuego(scanner)
                Videojuego.borrarVideojuego(nombreJuego)
            }
            9 -> return
            else -> println("Opción no válida")
        }
    }
}

fun mostrarMenu() {
    println("Seleccione una opción:")
    println("1. Crear Estudio")
    println("2. Leer Estudios")
    println("3. Actualizar Estudio")
    println("4. Borrar Estudio")
    println("5. Crear Videojuego")
    println("6. Leer Videojuegos")
    println("7. Actualizar Videojuego")
    println("8. Borrar Videojuego")
    println("9. Salir")
}

fun limpiarPantalla() {
    Runtime.getRuntime().exec("cmd /c cls")
}
