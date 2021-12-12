package day12

object Day12 {
    fun problem1() {
        val content = javaClass.getResource("/day12.txt").readText()
        val nodes = mutableMapOf<String, Node>()
        content.lines().forEach { parse(it, nodes) }
        val result = mutableListOf<List<Node>>()
        findPaths(nodes["start"]!!, listOf(), result)
        println("paths: ${result.size}")
    }

    fun findPaths(node: Node, visited: List<Node>, result: MutableList<List<Node>>) {
        if (node.key == "end") {
            result.add(visited + node)
        } else if (!node.small || !visited.contains(node)) {
            node.connections.values.forEach {
                findPaths(it, visited + node, result)
            }
        }
    }

    fun parse(input: String, nodes: MutableMap<String, Node>) {
        val keys = input.split("-")

        val node1 = nodes[keys.first()] ?: Node(keys.first(), keys.first().all { it.isLowerCase() }).also { nodes[it.key] = it }
        val node2 = nodes[keys[1]] ?: Node(keys[1], keys[1].all { it.isLowerCase() }).also { nodes[it.key] = it }
        node1.connect(node2)
    }

    fun problem2() {
        val content = javaClass.getResource("/day12.txt").readText()
        val nodes = mutableMapOf<String, Node>()
        content.lines().forEach { parse(it, nodes) }
        val result = mutableListOf<List<Node>>()
        findPaths2(nodes["start"]!!, listOf(), mutableMapOf(), null, result)
        println("paths: ${result.size}")
    }

    fun findPaths2(node: Node, path: List<Node>, visited: Map<String, Int>, visitedTwice: Node?, result: MutableList<List<Node>>) {
        if (node.key == "end") {
            result.add(path + node)
        } else if (!node.small) {
            node.connections.values.forEach {
                findPaths2(it, path + node, visited, visitedTwice, result)
            }
        } else if (visited[node.key] == null) {
            val map = visited.toMutableMap()
            map[node.key] = 1
            node.connections.values.forEach {
                findPaths2(it, path + node, map, visitedTwice, result)
            }
        } else if (visitedTwice == null && node.key != "start") {
            val map = visited.toMutableMap()
            map[node.key] = 2
            node.connections.values.forEach {
                findPaths2(it, path + node, map, node, result)
            }
        }
    }
}

class Node (val key: String,
            val small: Boolean
){
    val connections = mutableMapOf<String, Node>()

    fun connect(node: Node) {
        connections[node.key] = node
        node.connections[key] = this
    }
}