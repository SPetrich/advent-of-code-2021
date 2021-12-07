import java.io.File
import kotlin.test.assertEquals

fun main() = Bingo().play()

class Bingo {

    val sample = File(ClassLoader.getSystemResource("dez04/sample.txt").toURI()).readLines()
    val input = File(ClassLoader.getSystemResource("dez04/input.txt").toURI()).readLines()

    fun play() {
        bingo(sample).let { (score, winNr) ->
            println("score: $score, winNr: $winNr")
            assertEquals(188, score)
            assertEquals(24, winNr)
            assertEquals(4512, score * winNr)
        }
        bingo(input).let { (score, winNr) ->
            println("score: $score, winNr: $winNr")
            println("result: ${score * winNr}")
        }

        bingo2(sample).let { (score, winNr) ->
            println("score: $score, winNr: $winNr")
            assertEquals(148, score)
            assertEquals(13, winNr)
            assertEquals(1924, score * winNr)
        }
        bingo2(input).let { (score, winNr) ->
            println("score: $score, winNr: $winNr")
            println("result: ${score * winNr}")
        }
    }

    fun bingo(input: List<String>): Pair<Int, Int> {
        val numbers = getNumbers(input)
        var fields = getFields(input)

        numbers.forEach { nr ->
            fields = fields.map {
                it.map {
                    if (it == nr) -1 else it
                }.also {
                    if (it.isBingo()) {
                        return Pair(it.filter { it != -1 }.sum(), nr)
                    }
                }
            }
        }

        return Pair(0, 0)
    }

    fun bingo2(input: List<String>): Pair<Int, Int> {
        val numbers = getNumbers(input)
        var fields = getFields(input)

        numbers.forEach { nr ->
            fields = fields.map {
                it.map {
                    if (it == nr) -1 else it
                }.let {
                    if (it.isBingo()) it.toMutableList().plus(0) else it
                }
            }.also {
                if (it.size == 1 && it.all { it.size == 26 }) {
                    return Pair(it[0].filter { it != -1 }.sum(), nr)
                }
            }.filter { it.size == 25 }
        }

        return Pair(0, 0)
    }

    private fun List<Int>.isBingo(): Boolean =
        this.chunked(5).any { it.sum() == -5 }
                || this.rotated().chunked(5).any { it.sum() == -5 }

    private fun <E> List<E>.rotated(): List<E> {
        val rotated = this.toMutableList()
        for (r in 0..4) {
            for (c in 0..4) {
                rotated[r * 5 + c] = this[(5 - 1 - c) * 5 + r]
            }
        }
        return rotated
    }

    fun getNumbers(input: List<String>): List<Int> = input[0].split(",").map { it.toInt() }

    fun getFields(input: List<String>) = input
        .subList(2, input.size).chunked(6).map {
            it.joinToString(" ").split(" ").filter { it.isNotBlank() }.map { it.toInt() }
        }
}