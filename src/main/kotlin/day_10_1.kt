fun main() {
    solve(10) {
        val matrix = readLines()

        data class Vertex(val i: Int, val j: Int)
        data class VertexWithDistance(val distance: Int, val vertex: Vertex)

        val n = matrix.size
        val m = matrix.first().length
        val q = ArrayDeque<VertexWithDistance>()
        val visited = List(n) { BooleanArray(m) { false } }

        for (i in 0..< n) {
            for (j in 0 ..< m) {
                if (matrix[i][j] == 'S') {
                    q.add(VertexWithDistance(0, Vertex(i, j)))
                    visited[i][j] = true
                    break
                }
            }
            if (q.isNotEmpty()) break
        }

        fun sym(vertex: Vertex) = matrix[vertex.i][vertex.j]

        fun canGo(from: Vertex, to: Vertex): Boolean {
            if (to.i < 0 || to.i >= n || to.j < 0 || to.j >= m) return false

            if (visited[to.i][to.j]) return false

            val toC = sym(to)

            return when(toC) {
                '-' -> { to.i == from.i }
                '|' -> { to.j == from.j }
                'L' -> { to.i == from.i && to.j == from.j - 1 || to.j == from.j && to.i == from.i + 1 }
                'J' -> { to.i == from.i && to.j == from.j + 1 || to.j == from.j && to.i == from.i + 1 }
                '7' -> { to.i == from.i && to.j == from.j + 1 || to.j == from.j && to.i == from.i - 1 }
                'F' -> { to.i == from.i && to.j == from.j - 1 || to.j == from.j && to.i == from.i - 1 }
                '.' -> { false }
                else -> false
            }
        }

        val shifts = listOf(-1, 0, 1).flatMap { out ->
            listOf(-1, 0, 1).mapNotNull {
                if (out == 0 && it == 0) null else {
                    it to out
                }
            }
        }


        var maxD = 0
        while (q.isNotEmpty()) {
            val (d, v) = q.removeFirst()
            for (sh in shifts) {
                val adjV = Vertex(v.i + sh.first, v.j + sh.second)
                if (!canGo(v, adjV)) continue

                visited[adjV.i][adjV.j] = true
                val newD = d + 1
                maxD = maxOf(maxD, newD)
                q.add(VertexWithDistance(newD, adjV))
            }
        }

        println(maxD)
    }
}
