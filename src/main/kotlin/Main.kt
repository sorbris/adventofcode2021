import kotlin.system.measureTimeMillis

fun main(args: Array<String>) {
    val elapsed = measureTimeMillis {
        day14.Day14.problem1()
    }
    println("problem1 took $elapsed millis")
    val elapsed2 = measureTimeMillis {
        day14.Day14.problem2()
    }
    println("problem2 took $elapsed2 millis")
}