import java.io.File
import java.lang.Integer.MAX_VALUE

fun main () {

    var i = 0
    var last = MAX_VALUE
    File(ClassLoader.getSystemResource("dez01/input.txt").toURI()).forEachLine{
        if ( it.toInt() > last ) i++
        last = it.toInt()
    }
    println(i)

}