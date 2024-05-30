import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class EstudioVideojuego(
    val nombre: String,
    val anioFundacion: Date,
    val esIndie: Boolean,
    val gananciasAnuales: Double,
    val juegos: ArrayList<Videojuego> = ArrayList()
) {
    companion object {
        private val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        private val estudioFile = File("src/main/kotlin/estudioVideojuego.txt")

        init {
            if (!estudioFile.exists()) estudioFile.createNewFile()
        }

        fun solicitarDatosEstudio(scanner: Scanner, dateFormat: SimpleDateFormat): EstudioVideojuego {
            println("Ingrese nombre del estudio:")
            val nombre = scanner.next()

            var anioFundacion: Date? = null
            while (anioFundacion == null) {
                println("Ingrese año de fundación (yyyy-MM-dd):")
                try {
                    anioFundacion = dateFormat.parse(scanner.next())
                } catch (e: Exception) {
                    println("Formato de fecha incorrecto. Inténtelo de nuevo.")
                }
            }

            println("Es indie (true/false):")
            val esIndie = obtenerBooleano(scanner)

            println("Ingrese ganancias anuales:")
            val gananciasAnuales = obtenerDouble(scanner)

            return EstudioVideojuego(nombre, anioFundacion, esIndie, gananciasAnuales)
        }

        private fun obtenerBooleano(scanner: Scanner): Boolean {
            var input: String
            while (true) {
                input = scanner.next().toLowerCase(Locale.getDefault())
                if (input == "true" || input == "false") {
                    return input.toBoolean()
                } else {
                    println("Entrada inválida. Por favor, ingrese 'true' o 'false'.")
                }
            }
        }

        private fun obtenerDouble(scanner: Scanner): Double {
            var input: String
            while (true) {
                input = scanner.next()
                try {
                    return input.toDouble()
                } catch (e: NumberFormatException) {
                    println("Entrada inválida. Por favor, ingrese un número válido.")
                }
            }
        }

        fun solicitarNombreEstudio(scanner: Scanner): String {
            println("Ingrese nombre del estudio:")
            return scanner.next()
        }

        fun crearEstudioVideojuego(estudio: EstudioVideojuego) {
            estudioFile.appendText("${estudio.nombre},${dateFormat.format(estudio.anioFundacion)},${estudio.esIndie},${estudio.gananciasAnuales}\n")
        }

        fun leerEstudioVideojuego(): List<EstudioVideojuego> {
            val estudios = estudioFile.readLines().map {
                val parts = it.split(",")
                EstudioVideojuego(
                    parts[0],
                    dateFormat.parse(parts[1]),
                    parts[2].toBoolean(),
                    parts[3].toDouble()
                )
            }

            val videojuegos = Videojuego.leerVideojuego()

            estudios.forEach { estudio ->
                estudio.juegos.addAll(videojuegos.filter { it.nombreEstudio == estudio.nombre })
            }

            return estudios
        }

        fun actualizarEstudioVideojuego(nombre: String, nuevoEstudio: EstudioVideojuego) {
            val estudios = leerEstudioVideojuego().toMutableList()
            val index = estudios.indexOfFirst { it.nombre == nombre }
            if (index != -1) {
                estudios[index] = nuevoEstudio
                estudioFile.writeText(estudios.joinToString("\n") {
                    "${it.nombre},${dateFormat.format(it.anioFundacion)},${it.esIndie},${it.gananciasAnuales}"
                })
            }
        }

        fun actualizarJuegosEstudio(nombre: String, nuevosJuegos: ArrayList<Videojuego>) {
            val estudios = leerEstudioVideojuego().toMutableList()
            val index = estudios.indexOfFirst { it.nombre == nombre }
            if (index != -1) {
                estudios[index].juegos.clear()
                estudios[index].juegos.addAll(nuevosJuegos)
                estudioFile.writeText(estudios.joinToString("\n") {
                    "${it.nombre},${dateFormat.format(it.anioFundacion)},${it.esIndie},${it.gananciasAnuales}"
                })
            }
        }

        fun borrarEstudioVideojuego(nombre: String) {
            val estudios = leerEstudioVideojuego().filterNot { it.nombre == nombre }
            estudioFile.writeText(estudios.joinToString("\n") {
                "${it.nombre},${dateFormat.format(it.anioFundacion)},${it.esIndie},${it.gananciasAnuales}"
            })
        }
    }

    override fun toString(): String {
        return "EstudioVideojuego(nombre='$nombre', anioFundacion=$anioFundacion, esIndie=$esIndie, gananciasAnuales=$gananciasAnuales, juegos=$juegos)"
    }
}
