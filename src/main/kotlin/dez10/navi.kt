import java.io.File
import kotlin.test.assertEquals

fun main() = Navi().explore()

class Navi {

    private val sample = File(ClassLoader.getSystemResource("dez10/sample.txt").toURI()).readLines()
    private val input = File(ClassLoader.getSystemResource("dez10/input.txt").toURI()).readLines()

    private val charValues = mapOf('(' to -3, '[' to -57, '{' to -1197, '<' to -25137, ')' to 3, ']' to 57, '}' to 1197, '>' to 25137)
    private val closingScore = mapOf(-3 to 1, -57 to 2, -1197 to 3, -25137 to 4)

    fun explore() {
        corruptedSum(sample).let {
            println("sum: $it")
            assertEquals(26397, it)
        }
        corruptedSum(input).let {
            println("sum: $it")
        }

        completionMedian(sample).let {
            println("median: $it")
            assertEquals(288957, it)
        }
        completionMedian(input).let {
            println("median: $it")
        }
    }

    private fun corruptedSum(sample: List<String>) =
        getSignals(sample)
            .also { resolveMatches(it) }
            .mapNotNull { it.firstOrNull { it > 0 } }
            .sum()

    private fun completionMedian(sample: List<String>) =
        getSignals(sample)
            .also { resolveMatches(it) }
            .filter { it.none { it > 0 } }
            .map { it.filter { it < 0 } }
            .map { it.map { closingScore[it]!!.toLong() } }
            .map { it.reversed().reduce { acc, i -> i.plus(acc * 5) } }
            .sorted()
            .let { it[it.size / 2] }

    private fun resolveMatches(it: List<MutableList<Int>>) =
        it.forEach { line ->
            line.indices.drop(1).forEach {
                val ref = (it - 1 downTo 0).firstOrNull { line[it] != 0 }
                if (line[it] > 0 && ref != null && line[ref] + line[it] == 0) {
                    line[it] = 0
                    line[ref] = 0
                } } }

    private fun getSignals(input: List<String>) = input.map { it.toCharArray().map { charValues[it]!! }.toMutableList() }

}