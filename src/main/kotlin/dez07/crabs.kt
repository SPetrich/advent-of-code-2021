import java.io.File
import kotlin.test.assertEquals
import kotlin.math.abs

fun main() = Crabs().align()

class Crabs {

    val sample = File(ClassLoader.getSystemResource("dez07/sample.txt").toURI()).readLines()[0]
    val input = File(ClassLoader.getSystemResource("dez07/input.txt").toURI()).readLines()[0]

    fun align() {
        align(sample).let { fuel ->
            println("fuel: $fuel")
            assertEquals(37, fuel)
        }
        align(input).let { fuel ->
            println("fuel: $fuel")
        }

        align2(sample).let { fuel ->
            println("fuel: $fuel")
            assertEquals(168, fuel)
        }
        align2(input).let { fuel ->
            println("fuel: $fuel")
        }

    }

    fun align(input: String): Int = input.split(",").map { it.toInt() }.let { p ->
        (0..p.maxOrNull()!!).minOf { i -> p.sumOf { abs(it - i) } }
    }

    fun align2(input: String): Int = input.split(",").map { it.toInt() }.let { p ->
        (0..p.maxOrNull()!!).minOf { i -> p.sumOf { (1..abs(it - i)).sum() } }
    }

}