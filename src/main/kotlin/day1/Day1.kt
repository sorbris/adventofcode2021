package day1

object Day1 {
    fun problem1(): Int {
        val content = javaClass.getResource("/day1.txt").readText()
        val numbers = content.lines().map { it.toInt() }
        var count = 0
        numbers.fold(Integer.MAX_VALUE) { acc, nbr ->
            if (nbr > acc) {
                count++
            }
            nbr
        }
        return count
    }

    fun problem2(): Int {
        val content = javaClass.getResource("/day1.txt").readText()
        var count = 0
        content.lineSequence().windowed(3).map { window ->
            window[0].toInt() + window[1].toInt() + window[2].toInt()
        }.fold(Integer.MAX_VALUE) { acc, sum ->
            if (sum > acc) count++
            sum
        }
        println(count)
        return count

    }
}