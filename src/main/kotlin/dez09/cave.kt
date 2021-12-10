import java.io.File
import java.lang.Math.abs
import kotlin.test.assertEquals

fun main() = Cave().explore()

class Cave {

    val sample = File(ClassLoader.getSystemResource("dez09/sample.txt").toURI()).readLines()
    val input = File(ClassLoader.getSystemResource("dez09/input.txt").toURI()).readLines()

    fun explore() {
        lowPoints(sample).let {
            println("sum: $it")
            assertEquals(15, it)
        }
        lowPoints(input).let {
            println("sum: $it")
        }

        basins(sample).let {
            println("top3 product: $it")
            assertEquals(1134, it)
        }
        basins(input).let {
            println("top3 product: $it")
        }
    }

    fun lowPoints(input: List<String>) = getMap(input)
            .also { map -> map.indices.forEach {
                    y -> map[y].indices.forEach {
                        x -> if (((x > 0 && abs(map[y][x]) < abs(map[y][x - 1])) || x == 0)
                              && ((x < map[0].size - 1 && abs(map[y][x]) < abs(map[y][x + 1])) || x == map[0].size - 1)
                              && ((y > 0 && abs(map[y][x]) < abs(map[y - 1][x])) || y == 0)
                              && ((y < map.size - 1 && abs(map[y][x]) < abs(map[y + 1][x])) || y == map.size - 1)
                        ) map[y][x] = map[y][x] * -1 } }
            }.let { it.sumOf { it.filter { it < 1 }.map { abs(it) + 1 }.sum() } }


    private fun basins(input: List<String>) = getMap(input)
            .also { map -> map.indices.forEach {
                    y -> map[y].indices.forEach {
                        x -> markConnected(map, x, y, x * 100 + y + 10) } } }
            .let { it.flatten().filter { it != 9 }.groupingBy { it }.eachCount().entries.map { it.value }
                .sortedDescending().take(3).reduce(Int::times) }

    private fun markConnected(map: List<MutableList<Int>>, x: Int, y: Int, i: Int) {
        if (map[y][x] < 9) {
            map[y][x] = i
            if (x < map[y].size - 1 && map[y][x + 1] < 9) markConnected(map, x + 1, y, i)
            if (x > 0 && map[y][x - 1] < 9) markConnected(map, x - 1, y, i)
            if (y < map.size - 1 && map[y + 1][x] != 9) markConnected(map, x, y + 1, i)
            if (y > 0 && map[y - 1][x] != 9) markConnected(map, x, y - 1, i)
        }
    }


    fun getMap(input: List<String>) = input.map { it.toCharArray().map { it.toString().toInt() }.toMutableList() }

}