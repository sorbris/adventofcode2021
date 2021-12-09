package day9

import day9.Day9.findDownFrom
import day9.Day9.findUpFrom

object Day9 {
    fun problem1() {
        val content = javaClass.getResource("/day9.txt").readText()
        val grid = content.lines().mapIndexed { i, s -> s.mapIndexed { j, c -> c.digitToInt() } }
        val lowpoints = arrayListOf<Int>()
        for (i in grid.indices) {
            for (j in grid[i].indices) {
                if (checkIfLowPoint(i, j, grid)) {
                    lowpoints.add(grid[i][j])
                }
            }
        }
        println("sum = ${lowpoints.map { it + 1 }.sum()}")
    }

    fun problem2() {
        val content = javaClass.getResource("/day9.txt").readText()
        val grid = content.lines().mapIndexed { i, s -> s.mapIndexed { j, c -> Reading(i, j, c.digitToInt()) } }
        val lowpoints = mutableListOf<Lowpoint>()
        for (i in grid.indices) {
            for (j in grid[i].indices) {
                if (checkIfLowPoint2(i, j, grid)) {
                    val p = grid[i][j]
                    val lowpoint = Lowpoint(i, j, p.elevation)
                    p.added = true
                    lowpoint.basin.addAll(listOf(p) + p.findDownFrom(grid) + p.findLeftFrom(grid) + p.findUpFrom(grid) + p.findRightFrom(grid))
                    lowpoints.add(lowpoint)
                }
            }
        }
        lowpoints.sort()
        println("lowpoints found: ${lowpoints.size}")
        println("answer is : ${lowpoints.takeLast(3).fold(1) { acc, lowpoint ->  acc * lowpoint.basin.size }}")
    }

    fun Reading.findUpFrom(grid: List<List<Reading>>): List<Reading> {
        if (y > 0) {
            val p = grid[x][y-1]
            if (this >= p || p.added || p.elevation == 9) return listOf() // if this elevation is higher or if p is already added to a basin abort

            // if p is part of the basin then mark it as added and repeat
            p.added = true
            return listOf(p) + p.findUpFrom(grid) + p.findLeftFrom(grid) + p.findRightFrom(grid)
        }
        return listOf()
    }

    fun Reading.findDownFrom(grid: List<List<Reading>>): List<Reading> {
        if (y < grid[x].lastIndex) {
            val p = grid[x][y+1]
            if (this >= p || p.added || p.elevation == 9) return listOf() // if this elevation is higher or if p is already added to a basin abort
            p.added = true
            return listOf(p) + p.findDownFrom(grid) + p.findLeftFrom(grid) + p.findRightFrom(grid)
        }
        return listOf()
    }

    fun Reading.findRightFrom(grid: List<List<Reading>>): List<Reading> {
        if (x < grid.lastIndex) {
            val p = grid[x+1][y]
            if (this >= p || p.added || p.elevation == 9) return listOf() // if this elevation is higher or if p is already added to a basin abort
            p.added = true
            return listOf(p) + p.findDownFrom(grid) + p.findUpFrom(grid) + p.findRightFrom(grid)
        }
        return listOf()
    }

    fun Reading.findLeftFrom(grid: List<List<Reading>>): List<Reading> {
        if (x > 0) {
            val p = grid[x-1][y]
            if (this >= p || p.added || p.elevation == 9) return listOf() // if this elevation is higher or if p is already added to a basin abort

            // if p is part of the basin then mark it as added and repeat
            p.added = true
            return listOf(p) + p.findUpFrom(grid) + p.findLeftFrom(grid) + p.findDownFrom(grid)
        }
        return listOf()
    }


    class Reading(val x: Int, val y: Int, val elevation: Int, var added: Boolean = false) {
        operator fun compareTo(other: Reading): Int {
            return elevation.compareTo(other.elevation)
        }
    }

    class Lowpoint(val x: Int, val y: Int, val elevation: Int) : Comparable<Lowpoint> {
        val basin = arrayListOf<Reading>()
        override operator fun compareTo(other: Lowpoint): Int {
            return basin.size.compareTo(other.basin.size)
        }
    }

    private fun checkIfLowPoint2(i: Int, j: Int, grid: List<List<Reading>>): Boolean {
        val p = grid[i][j]
        if (p.added) return false // a point that is part of another basin can't be a lowpoint
        if (i > 0 && p >= grid[i-1][j]) return false
        if (i < grid.lastIndex && p >= grid[i+1][j]) return false
        if (j > 0 && p >= grid[i][j-1]) return false
        if (j < grid[i].lastIndex && p >= grid[i][j+1]) return false

        return true
    }


    private fun checkIfLowPoint(i: Int, j: Int, grid: List<List<Int>>): Boolean {
        val p = grid[i][j]
        if (i > 0 && p >= grid[i-1][j]) return false
        if (i < grid.lastIndex && p >= grid[i+1][j]) return false
        if (j > 0 && p >= grid[i][j-1]) return false
        if (j < grid[i].lastIndex && p >= grid[i][j+1]) return false

        return true
    }
}