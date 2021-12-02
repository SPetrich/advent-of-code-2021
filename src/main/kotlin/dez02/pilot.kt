import java.io.File

fun main () {

    var x = 0
    var depth = 0
    File(ClassLoader.getSystemResource("dez02/input.txt").toURI()).forEachLine{
        when {
            it.startsWith("forward ") -> x += it.substringAfter(" ").toInt()
            it.startsWith("down ") -> depth += it.substringAfter(" ").toInt()
            it.startsWith("up ") -> depth -= it.substringAfter(" ").toInt()
            else -> println("ERROR")
        }
    }
    println(x * depth)

}