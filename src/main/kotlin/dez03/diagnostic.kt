import java.io.File
import kotlin.math.pow

fun main () {

    var x = 0
    val ones = HashMap<Int, Int>()
    val zeros = HashMap<Int, Int>()
    File(ClassLoader.getSystemResource("dez03/input.txt").toURI()).readBytes().forEach {
        when (it) {
            48.toByte() -> zeros[x] = (zeros[x] ?: 0) + 1
            49.toByte() -> ones[x] = (ones[x] ?: 0) + 1
            10.toByte() -> x = -1
            else -> println("ERROR")
        }
        x++
    }
    println(ones)
    println(zeros)

    val epsilon = ones.entries.sumOf { if (it.value > zeros[it.key]!!) 2.0.pow(ones.size - 1 - it.key) else 0.0 }
    val gamma = zeros.entries.sumOf { if (it.value > ones[it.key]!!) 2.0.pow(zeros.size - 1 - it.key) else 0.0 }

    println(gamma)
    println(epsilon)
    println(gamma *epsilon)

}