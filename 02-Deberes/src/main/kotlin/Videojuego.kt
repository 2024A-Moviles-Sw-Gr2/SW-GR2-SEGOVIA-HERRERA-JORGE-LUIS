import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class Videojuego(
    var fechaLanzamiento: Date,
    var nominado: Boolean,
    var horasDeJuego: Int,
    var nombreJuego: String,
    var precioJuego: Double,
    var nombreEstudio: String
) {
    companion object {
        private val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        private val videojuegoFile = File("src/main/kotlin/videojuego.txt")

        init {
            if (!videojuegoFile.exists()) videojuegoFile.createNewFile()
        }

        fun solicitarDatosVideojuego(scanner: Scanner, dateFormat: SimpleDateFormat): Videojuego {
            println("Ingrese nombre del videojuego:")
            val nombreJuego = solicitarTexto(scanner)

            println("Ingrese fecha de lanzamiento (yyyy-MM-dd):")
            val fechaLanzamiento = solicitarFecha(scanner, dateFormat)

            println("Está nominado (true/false):")
            val nominado = solicitarBoolean(scanner)

            println("Ingrese horas de juego:")
            val horasDeJuego = solicitarEntero(scanner)

            println("Ingrese precio del videojuego:")
            val precioJuego = solicitarDouble(scanner)

            println("Ingrese nombre del estudio:")
            val nombreEstudio = solicitarTexto(scanner)

            return Videojuego(fechaLanzamiento, nominado, horasDeJuego, nombreJuego, precioJuego, nombreEstudio)
        }

        private fun solicitarFecha(scanner: Scanner, dateFormat: SimpleDateFormat): Date {
            while (true) {
                try {
                    val fecha = scanner.next()
                    return dateFormat.parse(fecha)
                } catch (e: Exception) {
                    println("Formato de fecha incorrecto. Inténtelo de nuevo (yyyy-MM-dd):")
                }
            }
        }

        private fun solicitarBoolean(scanner: Scanner): Boolean {
            while (true) {
                try {
                    return scanner.nextBoolean()
                } catch (e: Exception) {
                    println("Por favor, ingrese 'true' o 'false'.")
                }
            }
        }

        private fun solicitarEntero(scanner: Scanner): Int {
            while (true) {
                try {
                    return scanner.nextInt()
                } catch (e: Exception) {
                    println("Por favor, ingrese un número entero válido.")
                    scanner.next()
                }
            }
        }

        private fun solicitarDouble(scanner: Scanner): Double {
            while (true) {
                try {
                    return scanner.nextDouble()
                } catch (e: Exception) {
                    println("Por favor, ingrese un número decimal válido.")
                    scanner.next()
                }
            }
        }

        private fun solicitarTexto(scanner: Scanner): String {
            return scanner.next()
        }

        fun solicitarNombreVideojuego(scanner: Scanner): String {
            println("Ingrese nombre del videojuego:")
            return scanner.next()
        }

        fun crearVideojuego(videojuego: Videojuego, nombreEstudio: String) {
            val estudios = EstudioVideojuego.leerEstudioVideojuego().toMutableList()
            val estudio = estudios.find { it.nombre == nombreEstudio }

            if (estudio != null) {
                videojuegoFile.appendText("${dateFormat.format(videojuego.fechaLanzamiento)},${videojuego.nominado},${videojuego.horasDeJuego},${videojuego.nombreJuego},${videojuego.precioJuego},${videojuego.nombreEstudio}\n")

                estudio.juegos.add(videojuego)
                EstudioVideojuego.actualizarJuegosEstudio(nombreEstudio, estudio.juegos)
            } else {
                println("El estudio '$nombreEstudio' no existe. Por favor, ingrese un estudio existente.")
            }
        }

        fun leerVideojuego(): List<Videojuego> {
            return videojuegoFile.readLines().mapNotNull {
                val parts = it.split(",")
                if (parts.size == 6) {
                    try {
                        Videojuego(
                            dateFormat.parse(parts[0]),
                            parts[1].toBoolean(),
                            parts[2].toInt(),
                            parts[3],
                            parts[4].toDouble(),
                            parts[5]
                        )
                    } catch (e: Exception) {
                        println("Error al leer el videojuego: $it")
                        null
                    }
                } else {
                    println("Formato incorrecto para el videojuego: $it")
                    null
                }
            }
        }

        fun actualizarVideojuego(nombreJuego: String, nuevoVideojuego: Videojuego) {
            val videojuegos = leerVideojuego().toMutableList()
            val index = videojuegos.indexOfFirst { it.nombreJuego == nombreJuego }
            if (index != -1) {
                videojuegos[index] = nuevoVideojuego
                videojuegoFile.writeText(videojuegos.joinToString("\n") {
                    "${dateFormat.format(it.fechaLanzamiento)},${it.nominado},${it.horasDeJuego},${it.nombreJuego},${it.precioJuego},${it.nombreEstudio}"
                })
            }
        }

        fun borrarVideojuego(nombreJuego: String) {
            val videojuegos = leerVideojuego().filterNot { it.nombreJuego == nombreJuego }
            videojuegoFile.writeText(videojuegos.joinToString("\n") {
                "${dateFormat.format(it.fechaLanzamiento)},${it.nominado},${it.horasDeJuego},${it.nombreJuego},${it.precioJuego},${it.nombreEstudio}"
            })
        }
    }

    override fun toString(): String {
        return "Videojuego(fechaLanzamiento=$fechaLanzamiento, nominado=$nominado, horasDeJuego=$horasDeJuego, nombreJuego='$nombreJuego', precioJuego=$precioJuego, nombreEstudio='$nombreEstudio')"
    }
}
