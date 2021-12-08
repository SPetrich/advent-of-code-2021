import java.io.File
import kotlin.math.pow
import kotlin.test.assertEquals

fun main() = Display().decode()

class Display {

    val sample = File(ClassLoader.getSystemResource("dez08/sample.txt").toURI()).readLines()
    val input = File(ClassLoader.getSystemResource("dez08/input.txt").toURI()).readLines()

    fun decode() {
        countUniques(sample).let { count ->
            println("uniques: $count")
            assertEquals(26, count)
        }
        countUniques(input).let { count ->
            println("uniques: $count")
        }

        sumOutputs(sample).let { sum ->
            println("sum: $sum")
            assertEquals(61229.0, sum)
        }
        sumOutputs(input).let { sum ->
            println("sum: $sum")
        }
    }

    fun countUniques(input: List<String>) = getValues(input).count { listOf(2, 3, 4, 7).contains(it.length) }

    fun getValues(input: List<String>) = input.map { it.split("|")[1].split(" ") }.flatten()

    fun sumOutputs(input: List<String>) = getSignals(input)
        .sumOf { it ->
            val decode = HashMap<Int, Collection<Char>>()
            it.forEach {
                if (it.size == 2) decode[1] = it
                if (it.size == 3) decode[7] = it
                if (it.size == 4) decode[4] = it
                if (it.size == 7) decode[8] = it
            }
            it.forEach { if (it.size == 6 && it.containsAll(decode[4]!!)) decode[9] = it }
            it.forEach { if (it.size == 6 && it.containsAll(decode[1]!!) && !it.containsAll(decode[9]!!)) decode[0] = it }
            it.forEach { if (it.size == 6 && !it.containsAll(decode[0]!!) && !it.containsAll(decode[9]!!)) decode[6] = it }
            it.forEach { if (it.size == 5 && it.containsAll(decode[1]!!)) decode[3] = it }
            it.forEach { if (it.size == 5 && decode[6]!!.containsAll(it)) decode[5] = it }
            it.forEach { if (it.size == 5 && !it.containsAll(decode[3]!!) && !it.containsAll(decode[5]!!)) decode[2] = it }

            it.subList(10, 14).reversed().withIndex().sumOf {
                decode.entries.associate { (k, v) -> v to k }[it.value]!!.times(10.0.pow(it.index)) }
        }

    fun getSignals(input: List<String>) = input.map { it.split("|", " ").filter { it != "" }.map { it.toCharArray().toSet() } }

}