import java.io.File

fun main () {

    var x = 0
    var depth = 0
    var aim = 0
    File(ClassLoader.getSystemResource("dez02/input.txt").toURI()).forEachLine{
        when {
            it.startsWith("forward ") -> {
                val move = it.substringAfter(" ").toInt()
                x += move
                depth += move * aim
            }
            it.startsWith("down ") -> aim += it.substringAfter(" ").toInt()
            it.startsWith("up ") -> aim -= it.substringAfter(" ").toInt()
        }
    }
    println(x * depth)

}