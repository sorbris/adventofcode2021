package day11

import java.lang.Integer.max
import java.lang.Integer.min

object Day11 {

    fun problem1() {
        val content = javaClass.getResource("/day11.txt").readText()
        val grid = content.lines().map { it.map { c -> c.digitToInt() }.toMutableList() }


        var flashCount = 0L
        for (i in 0..99) {
            val flashes = cycle(grid)
            for (flash in flashes) {
                flashCount += flash(flash.first, flash.second, grid)
            }
        }
        print(grid, 100)
        println("flashes in total: $flashCount")
    }

    fun print(grid: List<MutableList<Int>>, steps: Int) {
        println("Steps: $steps")
        grid.forEach {
            val sb = StringBuilder()
            it.forEach {d -> sb.append(d.digitToChar()) }
            println(sb.toString())
        }
    }

    fun cycle(grid: List<MutableList<Int>>): MutableList<Pair<Int, Int>> {
        val flashes = mutableListOf<Pair<Int, Int>>()
        for (i in grid.indices) {
            for (j in grid[i].indices) {
                grid[i][j]++
                if (grid[i][j] >= 10) {
                    flashes.add(i to j)
                }
            }
        }
        return flashes
    }

    fun problem2() {
        val content = javaClass.getResource("/day11.txt").readText()
        val grid = content.lines().map { it.map { c -> c.digitToInt() }.toMutableList() }

        val size = grid.size * grid[0].size
        var found = false
        var cycle = 0

        while (!found) {
            val flashes = cycle(grid)
            var flashCount = 0
            for (flash in flashes) {
                flashCount += flash(flash.first, flash.second, grid)
            }
            found = flashCount == size
            cycle++
        }
        println("cycle: $cycle")
    }

    fun flash(x: Int, y: Int, grid: List<MutableList<Int>>): Int {
        if (grid[x][y] == 0) { // already flashed
            // Do nothing
            return 0
        } else if (grid[x][y] >= 9) {
            grid[x][y] = 0
            val sx = max(0, x-1)
            val ex = min(x+1, grid.lastIndex)
            val sy = max(0, y - 1)
            val ey = min(y+1, grid[x].lastIndex)
            var sum = 1
            for (i in sx .. ex) {
                for (j in sy .. ey) {
                    sum += flash(i, j, grid)
                }
            }
            return sum
        } else {
            grid[x][y]++
            return 0
        }
    }
}