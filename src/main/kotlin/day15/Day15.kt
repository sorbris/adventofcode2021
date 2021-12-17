package day15

import java.util.*

object Day15 {
    const val ANSI_RESET = "\u001B[0m"
    const val ANSI_GREEN = "\u001B[32m"
    fun problem1() {
        val content = javaClass.getResource("/day15-test.txt").readText()
        val field = arrayListOf<Int>()
        val lines = content.lines()
        val xMax = lines.first().length
        lines.forEach { it.forEach { c -> field.add(c.digitToInt()) } }
        val cave = Cave(field, xMax)
        val queue = TreeSet<Path>()
        queue.add(Path(0, 0, listOf(0)))
        var found = false
        val cheapestPaths = mutableMapOf<Int, Int>()
        while (!found) {
            val path = queue.pollFirst()!!
            found = path.coord == cave.lastIndex
            if (found) {
//                var s = 0
//                for (v in path.steps.drop(1)) {
//                    val x = v.rem(xMax)
//                    val y = v.div(xMax)
//                    s += field[v]
//                    println("($x,$y) + ${field[v]}  acc: $s")
//                }
                println("path found, cost: ${path.accumulatedValue}")
            } else {
                addUp(path, cheapestPaths, cave, xMax, queue)
                addDown(path, cheapestPaths, cave, xMax, queue)
                addLeft(path, cheapestPaths, cave, xMax, queue)
                addRight(path, cheapestPaths, cave, xMax, queue)
            }
        }
    }

    private fun addLeft(path: Path, cheapestPaths: MutableMap<Int, Int>, cave: Cave, xMax: Int, queue: TreeSet<Path>) {
        val c = path.coord - 1
        if (c > 0 && path.coord.rem(xMax) > 0) {
            val cost = path.accumulatedValue + cave[c]
            if ((cheapestPaths[c] ?: Integer.MAX_VALUE) > cost) {
                queue.add(Path(c, cost, path.steps + c))
                cheapestPaths[c] = cost
            }
        }
    }

    private fun addRight(path: Path, cheapestPaths: MutableMap<Int, Int>, cave: Cave, xMax: Int, queue: TreeSet<Path>) {
        val c = path.coord + 1
        if (c <= cave.lastIndex && path.coord.rem(xMax) < xMax - 1) {
            val cost = path.accumulatedValue + cave[c]
            if ((cheapestPaths[c] ?: Integer.MAX_VALUE) > cost) {
                queue.add(Path(c, cost, path.steps + c))
                cheapestPaths[c] = cost
            }
        }
    }

    private fun addUp(path: Path, cheapestPaths: MutableMap<Int, Int>, cave: Cave, xMax: Int, queue: TreeSet<Path>) {
        val c = path.coord - xMax
        if (c > 0) {
            val cost = path.accumulatedValue + cave[c]
            if ((cheapestPaths[c] ?: Integer.MAX_VALUE) > cost) {
                queue.add(Path(c, cost, path.steps + c))
                cheapestPaths[c] = cost
            }
        }
    }

    private fun addDown(path: Path, cheapestPaths: MutableMap<Int, Int>, cave: Cave, xMax: Int, queue: TreeSet<Path>) {
        val c = path.coord + xMax
        if (c <= cave.lastIndex) {
            val cost = path.accumulatedValue + cave[c]
            if ((cheapestPaths[c] ?: Integer.MAX_VALUE) > cost) {
                queue.add(Path(c, cost, path.steps + c))
                cheapestPaths[c] = cost
            }
        }
    }

    fun problem2() {
        val content = javaClass.getResource("/day15.txt").readText()
        val field = arrayListOf<Int>()
        val lines = content.lines()
        val xMax = lines.first().length * 5
        val rMax = lines.first().length
        lines.forEach { it.forEach { c -> field.add(c.digitToInt()) } }
        val cave = Cave(field, rMax, 5)
        val queue = TreeSet<Path>()
        queue.add(Path(0, 0, listOf(0)))
        var found = false
        val cheapestPaths = mutableMapOf<Int, Int>()

        while (!found) {
            val path = queue.pollFirst()!!

            found = path.coord == cave.lastIndex
            if (found) {
//                var s = 0
//                for (v in path.steps.drop(1)) {
//                    val x = v.rem(xMax)
//                    val y = v.div(xMax)
//                    s += field[v]
//                    println("($x,$y) + ${field[v]}  acc: $s")
//                }
                println("path found, cost: ${path.accumulatedValue}")
//                print(path, cave, xMax)
                println()
            } else {
                addUp(path, cheapestPaths, cave, xMax, queue)
                addDown(path, cheapestPaths, cave, xMax, queue)
                addLeft(path, cheapestPaths, cave, xMax, queue)
                addRight(path, cheapestPaths, cave, xMax, queue)
            }
        }
    }

    fun print(path: Path, cave: Cave, xMax: Int) {

        for (i in cave.indices) {

            if (i.rem(xMax) == 0) {
                print('\n')
            }
            if (i in path.steps) {
                print(ANSI_GREEN)
                print(cave[i])
                print(ANSI_RESET)
            } else {
                print(cave[i])
            }
        }
    }
}

data class Path(val coord: Int, val accumulatedValue: Int, val steps: List<Int>) : Comparable<Path> {

    override fun compareTo(other: Path): Int {
        var comparison = accumulatedValue.compareTo(other.accumulatedValue)
        if (comparison == 0) {
            comparison = coord.compareTo(other.coord)
        }
        return comparison
    }
}

class Cave(val data: List<Int>, val xMax: Int, val m: Int = 1) {

    val multiplier = m * m

    val lastIndex: Int = data.size * multiplier - 1
    val indices: IntRange
        get() = 0 until data.size * multiplier

    operator fun get(index: Int): Int {
        val x = index.rem(xMax*m)
        val y = index.div(xMax*m)
        val dx = x.rem(xMax)
        val dy = y.rem(xMax)
        val i = dy*xMax + dx
        val v = data[i]
        val xp = x.div(xMax)
        val yp = y.div(xMax)
        return when (val value = v + xp + yp) {
            in 1..9 -> value
            else -> value - 9
        }
    }

}