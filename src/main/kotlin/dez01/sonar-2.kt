package dez01

import java.io.File

fun main () {

    var i = 0
    val measurementSet = MeasurementSet()
    File(ClassLoader.getSystemResource("dez01/input.txt").toURI()).forEachLine{
        measurementSet.add(it.toInt())
        if ( measurementSet.measurementIncreased() ) i++
    }
    println(i)

}

class MeasurementSet {
    private var current: Int = 0
    private var last: Int = 0
    private var secondLast: Int = 0
    private var thirdLast: Int = 0

    fun add(value: Int){
        thirdLast = secondLast
        secondLast = last
        last = current
        current = value
    }

    fun measurementIncreased() = isEvaluable() && getCurrentSum() > getLastSum()

    private fun isEvaluable() = thirdLast > 0
    private fun getCurrentSum() = current + last + secondLast
    private fun getLastSum() = last + secondLast + thirdLast
}