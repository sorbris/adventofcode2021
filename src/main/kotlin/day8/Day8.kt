package day8

object Day8 {
    private val UNIQUE_DIGIT_LENS = intArrayOf(2,3,4,7)
    fun problem1() {
        val count = javaClass.getResource("/day8.txt").readText().lineSequence()
            .map { it.split("|").last().trim().split(" ") }.map { it.count { s -> s.length in UNIQUE_DIGIT_LENS } }.sum()
        println("count $count")
    }

    fun problem2() {
        val content = javaClass.getResource("/day8.txt").readText().lines()
        val sum = content.sumOf { getOutput(it) }
        println(sum)
    }

    fun getOutput(input: String): Int {
        val parts = input.split("|")
        val digits = parseDigit(parts.first().trim().split(" "))
        val nums = parts.last().trim().split(" ")
        val sum = nums.foldIndexed(0) { index, acc, num ->
            val mul = 3 - index
            val n = findNum(num, digits)
            acc + n * Math.pow(10.toDouble(), mul.toDouble()).toInt()
        }
        return sum
    }

    fun findNum(num: String, digits: List<Digit>): Int {
        return digits.find { it.signals.length == num.length && num.all { c -> it.signals.contains(c) } }!!.value
    }

    fun parseDigit(input: List<String>) : List<Digit> {
        val sorted = input.sortedWith { o1, o2 -> o1.length.compareTo(o2.length) }.toMutableList()

        val eight = Digit(sorted.last(), 8)
        val four = Digit(sorted.removeAt(2), 4)
        val seven = Digit(sorted.removeAt(1), 7)
        val one = Digit(sorted.removeFirst(), 1)
        val p3 = sorted.find { it.length == 5 && one.signals.all { c -> it.contains(c) } }
        val three = Digit(p3!!, 3)
        sorted.remove(p3)
        val p9 = sorted.find { it.length == 6 && four.signals.all { c -> it.contains(c) } }
        sorted.remove(p9!!)
        val nine = Digit(p9, 9)
        val p0 = sorted.find { it.length == 6 && one.signals.all {c -> it.contains(c) } }
        sorted.remove(p0!!)
        val zero = Digit(p0, 0)
        val six = Digit(sorted.find {it.length == 6}!!, 6)
        val ur = three.signals.find { c -> !six.signals.contains(c) }!!
        val five = Digit(sorted.find { it.length == 5 && !it.contains(ur)}!!, 5)
        val two = Digit(sorted.find { it.length == 5 && it.contains(ur)}!!, 2)

        return listOf(zero, one, two, three, four, five, six, seven, eight, nine)
    }

    class Digit(val signals: String, val value: Int)
}