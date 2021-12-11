import java.io.File
import kotlin.test.assertEquals

fun main() = Octopus().flashes()

class Octopus {

    private val sample = File(ClassLoader.getSystemResource("dez11/sample.txt").toURI()).readLines()
    private val input = File(ClassLoader.getSystemResource("dez11/input.txt").toURI()).readLines()

    fun flashes() {
        countFlashes(sample).let {
            println("flashes: $it")
            assertEquals(1656, it)
        }
        countFlashes(input).let {
            println("flashes: $it")
        }

        findFullFlash(sample).let {
            println("full flash at step: $it")
            assertEquals(195, it)
        }
        findFullFlash(input).let {
            println("full flash at step: $it")
        }
    }

    private fun countFlashes(sample: List<String>) =
        getInitialEnergy(sample)
            .also { map -> (1..100).forEach{ simulateStep(map) } }
            .flatten()
            .sumOf { it / 100 }

    private fun findFullFlash(sample: List<String>) =
        getInitialEnergy(sample)
            .let { map ->
                var step = 0
                do {
                    simulateStep(map)
                    step++
                } while (! map.isFullFlash())
                step
            }

    private fun simulateStep(map: List<MutableList<Int>>) {
        map.indices.forEach { y ->
            map[y].indices.forEach { x -> markConnected(map, x, y) }
        }
        map.indices.forEach { y ->
            map[y].indices.forEach { x -> if (map[y][x] % 100 == 10) map[y][x] += 90 }
        }
    }

    private fun markConnected(map: List<MutableList<Int>>, x: Int, y: Int) {
        if (x in (0..9) && y in (0..9) && map[y][x] % 100 != 10){
            map[y][x]++
            if (map[y][x] % 100 == 10) {
                (-1..1).forEach { yd -> (-1..1).forEach { xd ->
                    if (yd != 0 || xd != 0) markConnected(map, x + xd, y + yd) } }
            }
        }
    }

    private fun List<MutableList<Int>>.isFullFlash(): Boolean =
        this.flatten().filter { it % 100 == 0 }.size == this.flatten().size

    private fun getInitialEnergy(input: List<String>) =
        input.map { it.toCharArray().map { it.toString().toInt() }.toMutableList() }

}


