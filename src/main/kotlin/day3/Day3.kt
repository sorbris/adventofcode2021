package day3

import kotlin.math.pow

object Day3 {

    fun problem1() {
        val content = javaClass.getResource("/day3.txt").readText()
        val lines = content.lines()
        val counter = Counter(lines.first().length)
        lines.forEach {
            for (i in it.lastIndex downTo 0) {
                if (it[i] == '1') {
                    counter.increment(it.lastIndex - i)
                } else {
                    counter.decrease(it.lastIndex - i)
                }
            }
        }
        val (gamma, epsilon) = counter.get()
        println("gamma: $gamma")
        println("epsilon: $epsilon")
        println("result: $gamma * $epsilon = ${gamma * epsilon}")
    }

    fun problem2() {
        val content = javaClass.getResource("/day3.txt").readText()
        val lines = content.lines()
        val tree = Node("")
        lines.forEach {
            tree.add(it)
        }
        var orating: String? = null
        var co2rating: String? = null
        var currentONode = tree
        var currentCNode = tree
        while (orating == null || co2rating == null) {
            if (orating == null) {
                if (currentONode.items.size == 1) {
                    orating = currentONode.items.first()
                } else {
                    currentONode = if ((currentONode.one?.items?.size ?: 0) >= (currentONode.zero?.items?.size ?: 0)) {
                        currentONode.one!!
                    } else {
                        currentONode.zero!!
                    }
                }
            }
            if (co2rating == null) {
                if (currentCNode.items.size == 1) {
                    co2rating = currentCNode.items.first()
                } else {
                    currentCNode = if ((currentCNode.one?.items?.size ?: 0) < (currentCNode.zero?.items?.size ?: 0)) {
                        currentCNode.one!!
                    } else {
                        currentCNode.zero!!
                    }

                }
            }
        }

        println("orating = $orating")
        println("co2rating = $co2rating")
        val co2dec = co2rating.toDecimal()
        val odec = orating.toDecimal()
        println("orating decimal value = $odec")
        println("co2rating decimal value = $co2dec")
        println("rating: ${odec * co2dec}")
    }
}

class Node(val key: String) {
    val items = arrayListOf<String>()
    var zero: Node? = null
    var one: Node? = null

    fun add(number: String) {
        for (i in key.indices) {
            if (key[i] != number[i]) return
        }
        items.add(number)
        if (number.length > key.length) {
            val node = if (number[key.length] == '0') {
                zero ?: Node(key + '0').also { zero = it }
            } else {
                one ?: Node(key + '1').also { one = it }
            }
            node.add(number)
        }
    }
}

class Counter(size: Int) {
    val array = IntArray(size)
    fun increment(index: Int) {
        array[index] += 1
    }

    fun decrease(index: Int) {
        array[index] -= 1
    }



    fun get(): Pair<Int, Int> {
        var gamma = 0
        var epsilon = 0
        array.forEachIndexed { index, i ->
            val positionValue = Math.pow(2.0, index.toDouble()).toInt()
            val (g, e) = if (i > 0) 1 to 0 else 0 to 1
            gamma += g * positionValue
            epsilon += e * positionValue
        }

        return gamma to epsilon
    }
}

fun String.toDecimal(): Int {
    var sum = 0
    this.reversed().forEachIndexed { index, c ->
        val positionValue = 2.0.pow(index.toDouble()).toInt()
        val digit = c.digitToInt()
        if (digit != 0 && digit != 1) throw RuntimeException("nope")
        sum += digit*positionValue
    }
    return sum
}
