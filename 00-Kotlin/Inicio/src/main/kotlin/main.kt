import java.util.Date

fun main (){
    println("Hola mundo");

    //Inmutables (No se pueden RE ASIGNAR =)
    val inmutable:String = "Adrian";
    //inmutable = "Vicente" //Error

    //Mutables
    var mutable:String = "Vicente";

    //VAL > VAR

    //Duck typing
    var ejemploVariable = "Jorge Segovia"
    val edadEjemplo: Int = 12
    ejemploVariable.trim();
    //ejemploVariable = edadEjemplo // Error; tipo incorrecto
    //variable primitivas
    val nombre:String = "Adrian"
    val sueldo:Double = 1.2
    val estadoCivil:Char = 'C'
    val mayorEdad:Boolean = true
    //Clases en Java
    var fechaNacimiento:Date = Date();
}