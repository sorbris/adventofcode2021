package day10

import kotlin.math.exp

object Day10 {
    val points = mapOf(')' to 3, ']' to 57, '}' to 1197, '>' to 25137)
    val points2 = mapOf(')' to 1, ']' to 2, '}' to 3, '>' to 4)

    fun problem1() {
        val content = javaClass.getResource("/day10.txt").readText()
        val points = content.lines().map { findError(it) }.sum()

        println("points: $points")
    }

    fun problem2() {
        val content = javaClass.getResource("/day10.txt").readText()
        val points = content.lineSequence().map { findError2(it) }.filter { it != 0L }.sorted().toList()
        val p = points[points.size/2]
        println("points: $p")
    }

    fun findError(input: String): Int {
        val expected = ArrayDeque<Char>()
        for (c in input) {
            when (c) {
                '<' -> expected.add('>')
                '(' -> expected.add(')')
                '[' -> expected.add(']')
                '{' -> expected.add('}')
                else -> {
                    if (expected.removeLastOrNull() != c) {
                        return points[c]!!
                    }
                }
            }
        }

        return 0
    }

    fun findError2(input: String): Long {
        val expected = ArrayDeque<Char>()
        for (c in input) {
            when (c) {
                '<' -> expected.add('>')
                '(' -> expected.add(')')
                '[' -> expected.add(']')
                '{' -> expected.add('}')
                else -> {
                    if (expected.removeLastOrNull() != c) {
                        return 0
                    }
                }
            }
        }

        var sum = 0L
        expected.asReversed().forEach { c ->
            sum *= 5
            sum += points2[c]!!
        }
        return sum
    }
}