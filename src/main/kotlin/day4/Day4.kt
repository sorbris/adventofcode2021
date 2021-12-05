package day4

object Day4 {
    fun problem1() {
        val content = javaClass.getResource("/day4.txt").readText()
        val lines = content.lines()
        var calls = lines.first().split(",").map { it.toInt() }
        val boards = parseBoards(lines.drop(2))
        var call = calls.first()
        calls = calls.drop(1)
        var bingo = false
        while (!bingo) {
            boards.forEach {
                bingo = it.mark(call)
                if (bingo) {
                    calculateScore(call, it)
                    return
                }
            }
            call = calls.first()
            calls = calls.drop(1)
        }
    }

    fun problem2() {
        val content = javaClass.getResource("/day4.txt").readText()
        val lines = content.lines()
        var calls = lines.first().split(",").map { it.toInt() }
        val boards = parseBoards(lines.drop(2))
        var stillIn = ArrayList<Board>(boards)
        var next = arrayListOf<Board>()
        calls.forEach { call ->
            stillIn.forEach { board ->
                val bingo = board.mark(call)
                if (!bingo) {
                    next.add(board)
                } else if (stillIn.size == 1) {
                    calculateScore(call, board)
                }
            }
            stillIn = next
            next = arrayListOf()
        }
    }
}

fun calculateScore(call: Int, board: Board) {
    var sum = 0
    board.numbers.forEach { row ->
        row.forEach {
            if (!it.marked) sum += it.number
        }
    }
    println("Final score: ${sum * call}")
}

private fun parseBoards(text: List<String>): List<Board> {
    val boards = arrayListOf<Board>()
    (text + "").fold(arrayListOf<String>()) { acc, line ->
        if (line.isBlank()) {
            if (acc.isNotEmpty()) {
                boards.add(parseBoard(acc))
            }
            return@fold arrayListOf()
        } else {
            acc.apply { add(line) }
        }
    }
    return boards
}
private val delimiter = Regex("\\s+")
private fun parseBoard(text: List<String>): Board {
    val rows = mutableListOf<List<BoardNumber>>()
    for (s in text) {

        val row = s.trim().split(delimiter).map {
            BoardNumber(it.toInt())
        }
        rows.add(row)
    }
    return Board(rows)
}

class Board(val numbers: List<List<BoardNumber>>) {
    var hasBingo = false
    fun mark(num: Int): Boolean {
        numbers.forEachIndexed { x, rows ->
            var cumy = true
            rows.forEachIndexed { y, number ->
                if (number.number == num) {
                    number.marked = true
                    if (numbers[0][y].marked
                        && numbers[1][y].marked
                        && numbers[2][y].marked
                        && numbers[3][y].marked
                        && numbers[4][y].marked) {
                        hasBingo = true
                        return true
                    }
                } else {
                    cumy = cumy.and(number.marked)
                }
            }
            if (cumy) {
                hasBingo = true
                return true
            }
        }


        return false
    }
}

class BoardNumber(val number: Int, var marked: Boolean = false)