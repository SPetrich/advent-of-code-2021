import java.io.File
import kotlin.test.assertEquals

fun main() = Vents().overlaps()

class Vents {

    private val sample = File(ClassLoader.getSystemResource("dez05/sample.txt").toURI()).readLines()
    private val input = File(ClassLoader.getSystemResource("dez05/input.txt").toURI()).readLines()

    fun overlaps() {
        countSimpleOverlaps(sample).let {
            println("count: $it")
            assertEquals(5, it)
        }
        countSimpleOverlaps(input).let {
            println("count: $it")
        }

        countOverlaps(sample).let {
            println("count: $it")
            assertEquals(12, it)
        }
        countOverlaps(input).let {
            println("count: $it")
        }
    }

    private fun countSimpleOverlaps(input: List<String>) = getVectors(input)
        .mapNotNull { v ->
            if (v[0] == v[2]) (v[1]..v[3]).union(v[3]..v[1]).map { Pair(v[0], it) }
            else if (v[1] == v[3]) (v[0]..v[2]).union(v[2]..v[0]).map { Pair(it, v[1]) }
            else null
        }
        .flatten()
        .groupingBy { it }
        .eachCount()
        .filter { it.value > 1 }
        .count()

    private fun countOverlaps(input: List<String>) = getVectors(input)
        .map { v ->
                val xVals = (v[0]..v[2]).union(v[0] downTo v[2]).toList()
                val yVals = (v[1]..v[3]).union(v[1] downTo v[3]).toList()
                val x = if (xVals.size == 1) MutableList(yVals.size) { xVals[0] } else xVals
                val y = if (yVals.size == 1) MutableList(xVals.size) { yVals[0] } else yVals
                x.withIndex().map { Pair(it.value, y[it.index]) }
        }
        .flatten()
        .groupingBy { it }
        .eachCount()
        .filter { it.value > 1 }
        .count()

    private fun getVectors(input: List<String>) = input.map { it.split(",", " -> ").map { it.toInt() } }
}