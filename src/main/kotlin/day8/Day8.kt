package day8

object Day8 {
    private val UNIQUE_DIGIT_LENS = intArrayOf(2,3,4,7)
    fun problem1() {
        var count = 0
        val content = javaClass.getResource("/day8.txt").readText().lineSequence()
            .map { it.split("|").last().trim().split(" ").forEach { if (it.length in UNIQUE_DIGIT_LENS) count++ } }.toList()
        println("count $count")
    }

    fun problem2() {

        val content = javaClass.getResource("/day8-test.txt").readText()

    }

}