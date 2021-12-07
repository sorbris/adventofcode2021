package day7

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

object Day7 {

    fun problem1() {
        val content = javaClass.getResource("/day7.txt").readText()
        val crabs = content.split(",").map { it.toInt() }
        val (min,max) = crabs.fold(Integer.MAX_VALUE to Integer.MIN_VALUE) { (min,max), crab ->
            val mi = min(min, crab)
            val ma = max(max, crab)
            mi to ma
        }
        println("min value found: $min, max value: $max")
        val values = IntArray(max - min +1)
        crabs.forEach { crab ->
            for (v in min .. max) {
                values[v - min] += abs(v - crab)
            }
        }

        val fuel = values.fold(Integer.MAX_VALUE) { acc, i ->
            min(acc, i)
        }
        println("min fuel: $fuel")

    }

    fun problem2() {
        val content = javaClass.getResource("/day7.txt").readText()
        val crabs = content.split(",").map { it.toInt() }
        val (min,max) = crabs.fold(Integer.MAX_VALUE to Integer.MIN_VALUE) { (min,max), crab ->
            val mi = min(min, crab)
            val ma = max(max, crab)
            mi to ma
        }
        println("min value found: $min, max value: $max")
        val values = IntArray(max - min +1)
        crabs.forEach { crab ->
            for (v in min .. max) {
                val steps = abs(v - crab)
                values[v - min] += (steps*(steps+1))/2
            }
        }

        val fuel = values.fold(Integer.MAX_VALUE) { acc, i ->
            min(acc, i)
        }
        println("min fuel: $fuel")
    }
}