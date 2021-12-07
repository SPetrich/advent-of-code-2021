import java.io.File
import kotlin.math.abs

fun main() {
    val input = File(ClassLoader.getSystemResource("dez07/input.txt").toURI()).readLines()[0]

    println("fuel: ${align(input)}")
    println("fuel 2: ${align2(input)}")
}

fun align(input: String) = input.split(",").map { it.toInt() }.let { p ->
    (0..p.maxOrNull()!!).minOf { i -> p.sumOf { abs(it - i) } } }

fun align2(input: String) = input.split(",").map { it.toInt() }.let { p ->
    (0..p.maxOrNull()!!).minOf { i -> p.sumOf { (1..abs(it - i)).sum() } } }