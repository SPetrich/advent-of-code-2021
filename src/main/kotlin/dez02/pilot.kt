import java.io.File
import java.lang.Integer.MAX_VALUE

fun main () {

    val fwPattern = "forward "
    val downPattern = "down "
    val upPattern = "up "

    var x = 0
    var depth = 0
    File(ClassLoader.getSystemResource("dez02/input.txt").toURI()).forEachLine{
        with(it) {
            when {
                startsWith(fwPattern) -> x += it.substringAfter(" ").toInt()
                startsWith(downPattern) -> depth += it.substringAfter(" ").toInt()
                startsWith(upPattern) -> depth -= it.substringAfter(" ").toInt()
                else -> println("ERROR")
            }
        }
    }
    println(x)
    println(depth)
    println(x * depth)

}