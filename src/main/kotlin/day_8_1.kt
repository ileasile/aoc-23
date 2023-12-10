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

        var curNode = "AAA"
        val endNode = "ZZZ"
        var steps = 0
        while (true) {
            for (c in routeStr) {
                curNode = when(c) {
                    'L' -> graph[curNode]!!.left
                    'R' -> graph[curNode]!!.right
                    else -> error("Unknown direction: $c")
                }
                ++steps

                if (curNode == endNode) {
                    println(steps)
                    return@solve
                }
            }
        }
    }
}