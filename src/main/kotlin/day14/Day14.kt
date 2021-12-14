package day14

import kotlin.math.max
import kotlin.math.min

object Day14 {
    fun problem1() {
        val content = javaClass.getResource("/day14.txt").readText()
        val lines = content.lines()
        val rules = mutableMapOf<String,String>()
        var polymer = lines.first()
        lines.drop(2).forEach {
            val p = it.split(" -> ")
            rules[p.first()] = p.last()
        }
        for (i in 1..10) {
            val x = polymer.windowed(2).map { w -> w.first().toString() + rules[w]!! } + polymer.last().toString()
            polymer = x.joinToString("")
        }
        val counts = mutableMapOf<Char, Int>()
        polymer.forEach { c ->
            counts[c] = (counts[c] ?: 0) + 1
        }
        var max = Integer.MIN_VALUE
        var min = Integer.MAX_VALUE

        counts.entries.forEach {
            max = max(max, it.value)
            min = min(min, it.value)
        }
        println("max: $max min: $min")
        println("result: ${max - min}")
    }

    fun problem2() {
        val content = javaClass.getResource("/day14.txt").readText()
        val lines = content.lines()
        val rules = mutableMapOf<String,Char>()
        var polymer = lines.first()
        lines.drop(2).forEach {
            val p = it.split(" -> ")
            if (p.last().length != 1) throw IllegalStateException("wtf")
            rules[p.first()] = p.last().first()
        }
        val result = LongArray('Z' - 'A')
        polymer.windowed(2).forEach {
            val r = checkPair(it, 1, rules)
            for (i in result.indices) {
                result[i] = result[i] + r[i]
            }
            result[it.first() - 'A']++
        }
        result[polymer.last() - 'A']++

        var max = Long.MIN_VALUE
        var min = Long.MAX_VALUE

        result.forEachIndexed { i, it ->
            if (it > 0L) { // Do not check values that do not occur
                max = max(max, it)
                min = min(min, it)
            }
        }
        println("max: $max min: $min")
        println("result: ${max - min}")
    }

    val memory = mutableMapOf<Pair<String, Int>, LongArray>()

    fun checkPair(pair: String, step: Int, rules: Map<String, Char>): LongArray {
        val m = memory[pair to step]
        if (m != null) {
            return m
        }
        val c = rules[pair]!!
        val result = LongArray('Z' - 'A')
        if (step < 40) {
            val l = checkPair(buildString { append(pair.first());append(c) }, step + 1, rules)
            val r = checkPair(buildString { append(c);append(pair.last()) }, step + 1, rules)
            for (i in result.indices) {
                result[i] = l[i] + r[i]
            }
        }
        result[c - 'A']++
        memory[pair to step] = result
        return result
    }

}