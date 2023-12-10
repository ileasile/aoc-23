fun main() {
    solve(8) {
        val routeStr = readLine()
        readLine()

        class Instructions(val left: String, val right: String)
        val graph = mutableMapOf<String, Instructions>()

        readLines().forEach {
            val (from, instr) = it.split(" = ")
            val (left, right) = instr.substring(1, instr.lastIndex).split(", ")
            graph[from] = Instructions(left, right)
        }

        val results = graph.keys.filter { it.endsWith('A') }.toList().map {
            var curNode = it
            var steps = 0L
            while (true) {
                for (c in routeStr) {
                    curNode = when(c) {
                        'L' -> graph[curNode]!!.left
                        'R' -> graph[curNode]!!.right
                        else -> error("Unknown direction: $c")
                    }
                    ++steps

                    if (curNode.endsWith('Z')) {
                        return@map steps
                    }
                }
            }
            error("Unreachable")
        }

        println(lcm(results))
    }


}

fun lcm(numbers: List<Long>): Long {
    fun gcd(a: Long, b: Long): Long {
        return if (b == 0L) a else gcd(b, a % b)
    }

    fun lcm(a: Long, b: Long): Long {
        return a / gcd(a, b) * b
    }

    return numbers.fold(1L) { acc, number -> lcm(acc, number) }
}
