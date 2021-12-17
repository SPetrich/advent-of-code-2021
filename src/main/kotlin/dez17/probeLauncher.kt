import java.io.File
import kotlin.math.ceil
import kotlin.math.sqrt
import kotlin.test.assertEquals

fun main() = ProbeLauncher().fire()

class ProbeLauncher {

    private val sample = File(ClassLoader.getSystemResource("dez17/sample.txt").toURI()).readLines()[0]
    private val input = File(ClassLoader.getSystemResource("dez17/input.txt").toURI()).readLines()[0]

    fun fire() {
        fireAHAP(sample).let {
            println("height: $it")
            assertEquals(45, it)
        }
        fireAHAP(input).let {
            println("height: $it")
        }

        fireOptions(sample).let {
            println("nr: $it")
            assertEquals(112, it)
        }
        fireOptions(input).let {
            println("nr: $it")
        }
    }

    private fun fireAHAP(sample: String) =
        allHittingPaths(sample)
            .map { it.map { it.second }.maxOrNull() ?: -1 }
            .maxOrNull()

    private fun fireOptions(sample: String) =
        allHittingPaths(sample)
            .count()

    private fun allHittingPaths(sample: String) =
        getTargetArea(sample)
            .let { area ->
                initialVelocities(area)
                    .map { calcPath(Pair(0, 0), it, area[1], area[2]) }
                    .filter { hitsTarget(area, it) }
            }

    private fun getTargetArea(input: String) = Regex("-?\\d+").findAll(input).map { it.value.toInt() }.toList()

    private fun initialVelocities(area: List<Int>) = (minX(area)..area[1]).map { x -> (area[2]..-area[2]).map { y -> Pair(x, y) } }.flatten()

    private fun minX(area: List<Int>) = ceil(-0.5 + sqrt(0.25 + 2 * area[0])).toInt()

    private fun calcPath(point: Pair<Int, Int>, velocity: Pair<Int, Int>, maxRange: Int, maxDepth: Int): List<Pair<Int, Int>> {
        val nextP = Pair(point.first + velocity.first, point.second + velocity.second)
        val nextV = Pair(maxOf(velocity.first -1, 0), velocity.second -1)
        return listOf(nextP) + if (nextP.first < maxRange && nextP.second > maxDepth) calcPath(nextP, nextV, maxRange, maxDepth) else listOf()
    }

    private fun hitsTarget(area: List<Int>, points: List<Pair<Int, Int>>) =
        points.any { it.first >= area[0] && it.first <= area[1] && it.second >= area[2] && it.second <= area[3] }

}


