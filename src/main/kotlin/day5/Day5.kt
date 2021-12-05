package day5

object Day5 {
    var maxX = 0
    var maxY = 0
    private val linePattern = Regex("([0-9]+),([0-9]+) -> ([0-9]+),([0-9]+)")
    fun problem1() {
        val content = javaClass.getResource("/day5.txt").readText()
        val lines = content.lineSequence().map { parseLine(it) }.filter { it.x1 == it.x2 || it.y1 == it.y2 }.toList()
        val board = Array(maxY + 1) { IntArray(maxX + 1) }
        lines.forEach { board.apply(it) }
        val sum = countCoverage(board)
        println("sum: $sum")
    }

    fun problem2() {
        val content = javaClass.getResource("/day5.txt").readText()
        val lines = content.lineSequence().map { parseLine(it) }.toList()
        val board = Array(maxY + 1) { IntArray(maxX + 1) }
        lines.forEach { board.apply(it) }
        val sum = countCoverage(board)
        println("sum: $sum")
    }

    fun countCoverage(board: Array<IntArray>): Int {
        var sum = 0
        board.forEach { row ->
            row.forEach {
                if (it > 1) sum++
            }
        }
        return sum
    }

    fun parseLine(input: String): Line {
        val result = linePattern.matchEntire(input)
        if (result == null) throw RuntimeException("not good")
        val x1 = result.groups[1]!!.value.toInt()
        val y1 = result.groups[2]!!.value.toInt()
        val x2 = result.groups[3]!!.value.toInt()
        val y2 = result.groups[4]!!.value.toInt()
        if (x1 > maxX) maxX = x1
        if (x2 > maxX) maxX = x2
        if (y1 > maxY) maxY = y1
        if (y2 > maxY) maxY = y2
        return Line(
            x1, y1, x2, y2
        )
    }
}

class Line(val x1: Int, val y1: Int, val x2: Int, val y2: Int) {
    override fun toString(): String {
        return "$x1,$y1 -> $x2,$y2"
    }

    fun getCoveredCoords(): List<Pair<Int, Int>> {

        val yRange = if (y1 > y2) {
            y1 downTo y2
        } else {
            y1 .. y2
        }


        val xRange = if (x1 > x2) {
            x1 downTo x2
        } else {
            x1 .. x2
        }
        return if (x1 == x2) {
            yRange.map {
                x1 to it
            }
        } else if (y1 == y2) {
            xRange.map {
                it to y1
            }
        } else {
            xRange.zip(yRange) { x, y ->
                x to y
            }
        }
    }
}

fun Array<IntArray>.apply(line: Line) {
    line.getCoveredCoords().forEach { (x,y) ->
        this[y][x] += 1
    }
}