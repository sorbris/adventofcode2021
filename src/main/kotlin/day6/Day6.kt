package day6

object Day6 {
    fun problem1() {
        val content = javaClass.getResource("/day6.txt").readText()
        val initialState = content.split(",").map { it.toInt() }
        val answer = simulateDays(initialState, 80)
        println(answer)
        println(answer.size)
    }

    fun problem2() {
        val content = javaClass.getResource("/day6.txt").readText()
        val initialState = content.split(",").map { it.toInt() }
        val answer = initialState.fold(0L) { acc, fish ->

            acc + simulateFish(256, fish)
        }
        println(answer)
    }

    val memoization = mutableMapOf<Int, Long>()

    fun simulateFish(daysLeft:Int, initialState: Int = 8): Long {
        var fish = 1L // this fish

        val firstSpawn = daysLeft - initialState - 1 // fish doesn't spawn until it passes 0

        if (memoization[firstSpawn] != null) {
            return memoization[firstSpawn]!!
        } else if (firstSpawn < 0) {
            return 1L
        } else {
            val children = mutableListOf<Int>()
            var c = 0
            for (i in firstSpawn downTo 0) {
                if (c == 0) {
                    children.add(i)
                    c = 6
                } else {
                    c--
                }
            }
            for (child in children) {
                fish += simulateFish(child)
            }
            memoization[firstSpawn] = fish
            return fish
        }
    }

    fun simulateDays(initialState: List<Int>, days: Int): List<Int> {
        var state = initialState
        for (i in 1 ..  days) {
            state = simulateDay(state, i)
        }
        return state
    }

    fun simulateDay(state: List<Int>, day: Int): List<Int> {
        val spawn = arrayListOf<Int>()
        val newList = state.map {
            if (it == 0) {
                spawn.add(8)
                6
            } else {
                it - 1
            }
        }

        return newList + spawn
    }
}