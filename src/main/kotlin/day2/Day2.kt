package day2

import day2.Command.*

object Day2 {

    fun problem1() {
        val pattern = Regex("([a-zA-Z]*) ([0-9]*)")
        val point = Point(0, 0)
        val content = javaClass.getResource("/day2.txt").readText()

        content.lineSequence().map {
            val result = pattern.matchEntire(it)
            val amount = result!!.groups[2]!!.value.toInt()
            when (result.groups[1]!!.value) {
                "forward" -> Forward(amount)
                "up" -> Up(amount)
                "down" -> Down(amount)
                else -> throw RuntimeException("Failed to parse line: $it")
            }
        }.fold(point) { acc, command ->
            acc.apply {
                when (command) {
                    is Down -> y += command.amount
                    is Forward ->  x += command.amount
                    is Up -> y -= command.amount
                }
            }
        }

        println("x: ${point.x} y: ${point.y} mul: ${point.x * point.y}")
    }

    fun problem2() {
        val pattern = Regex("([a-zA-Z]*) ([0-9]*)")
        val content = javaClass.getResource("/day2.txt").readText()

        val (x, y, _) = content.lineSequence().map {
            val result = pattern.matchEntire(it)
            val amount = result!!.groups[2]!!.value.toInt()
            when (result.groups[1]!!.value) {
                "forward" -> Forward(amount)
                "up" -> Up(amount)
                "down" -> Down(amount)
                else -> throw RuntimeException("Failed to parse line: $it")
            }
        }.fold(Triple(0, 0, 0)) { (x, y, aim), command ->
            when (command) {
                is Down -> Triple(x, y, aim + command.amount)
                is Forward -> Triple(x + command.amount, y + aim * command.amount, aim)
                is Up -> Triple(x, y, aim - command.amount)
            }
        }
        println("x: $x y: $y mul: ${x * y}")
    }
}

data class Point(var x: Int, var y: Int)



sealed class Command(val amount: Int) {
    class Forward(amount: Int) : Command(amount)
    class Down(amount: Int) : Command(amount)
    class Up(amount: Int) : Command(amount)
}