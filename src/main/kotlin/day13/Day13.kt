package day13

import java.util.*

object Day13 {
    fun problem1() {
        val content = javaClass.getResource("/day13.txt").readText()
        val (map, instructions) = parse(content.lines())

        val (maxX, _) = applyInstructions(instructions.take(1), map)

        val sum = map.headMap(maxX, true).entries.sumOf { it.value.size }
        println("sum: $sum")
    }

    fun problem2() {
        val content = javaClass.getResource("/day13.txt").readText()
        val (map, instructions) = parse(content.lines())

        val (maxX, maxY) = applyInstructions(instructions, map)

        for (i in 0 until maxY) {
            for (j in 0 until maxX) {
                if (map[j]?.get(i) == true) {
                    print("#")
                } else {
                    print(" ")
                }
            }
            print('\n')
        }
    }

    fun parse(input: List<String>): Pair<TreeMap<Int, TreeMap<Int,Boolean>>, MutableList<Pair<String, Int>>> {
        val map = TreeMap<Int, TreeMap<Int, Boolean>>()
        val instructions = mutableListOf<Pair<String, Int>>()
        val pattern = Regex("fold along ([xy])=([0-9]+)")
        input.forEach { line ->
            if (!line.isNullOrBlank()) {
                val split = line.split(",")
                if (split.size != 1) {
                    val x = split.first().toInt()
                    val y = split.last().toInt()
                    (map[x] ?: TreeMap<Int, Boolean>().also { map[x] = it })[y] = true
                } else {
                    val result = pattern.matchEntire(line)
                    instructions.add(result!!.groups[1]!!.value to result.groups[2]!!.value.toInt())
                }
            }
        }
        return map to instructions
    }

    fun applyInstructions(instructions: List<Pair<String, Int>>, map: TreeMap<Int, TreeMap<Int,Boolean>>): Pair<Int, Int> {
        var maxX = 0
        var maxY = 0
        for (instruction in instructions) {
            when (instruction.first) {
                "y" -> {
                    maxY = instruction.second
                    val additions = mutableListOf<Pair<Int, Int>>()
                    map.forEach { entry ->
                        entry.value.navigableKeySet().tailSet(maxY).forEach {
                            additions.add(entry.key to (maxY - (it - maxY)))
                        }
                    }
                    additions.forEach { (map[it.first] ?: TreeMap<Int, Boolean>().also { m -> map[it.first] = m })[it.second] = true }
                }
                "x" -> {
                    maxX = instruction.second
                    val additions = mutableListOf<Pair<Int, Int>>()
                    map.navigableKeySet().tailSet(maxX).forEach { x ->
                        map[x]!!.navigableKeySet().forEach { y ->
                            additions.add(maxX - (x - maxX) to y)
                        }
                    }
                    additions.forEach { (map[it.first] ?: TreeMap<Int, Boolean>().also { m -> map[it.first] = m })[it.second] = true }
                }
            }
        }
        return maxX to maxY
    }
}