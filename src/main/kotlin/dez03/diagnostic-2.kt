import java.io.File
import kotlin.math.pow

fun main () {

    val matrix: MutableList<MutableList<Boolean>> = mutableListOf()
    var l: MutableList<Boolean> = mutableListOf()
    File(ClassLoader.getSystemResource("dez03/input.txt").toURI()).readBytes().forEach {
        when (it.toInt()) {
            48 -> l.add(false)
            49 -> l.add(true)
            10 -> { matrix.add(l); l = mutableListOf()}
            else -> println("ERROR")
        }
    }
    matrix.add(l)
    println(matrix)

    val oxy = lifeSupportRating(matrix, false)
    println(oxy)

    val co2 = lifeSupportRating(matrix, true)
    println(co2)

    println(oxy * co2)

}

private fun lifeSupportRating(matrix: MutableList<MutableList<Boolean>>, calculateCo2: Boolean): Double {
    var filteredMatrix = matrix
    for (i in 0 until matrix[0].size) {
        filteredMatrix = filteredMatrix.filter {
            if (calculateCo2.xor(filteredMatrix.isTrueTheDominatingBit(i))) it[i] else !it[i]
        }.toMutableList()
        if (filteredMatrix.size == 1) {
            return toDecimal(filteredMatrix.first())
        }
    }
    return 0.0
}

fun toDecimal(list: MutableList<Boolean>) =
    list.withIndex().sumOf { if (it.value) 2.0.pow(list.size - 1 - it.index) else 0.0 }

fun MutableList<MutableList<Boolean>>.isTrueTheDominatingBit(i: Int): Boolean {
    return this.filter { it[i] }.size * 2 >= this.size
}