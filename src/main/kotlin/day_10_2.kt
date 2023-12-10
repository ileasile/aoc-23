import java.util.*
import kotlin.collections.ArrayDeque
import kotlin.collections.List
import kotlin.collections.first
import kotlin.collections.flatMap
import kotlin.collections.isNotEmpty
import kotlin.collections.joinToString
import kotlin.collections.listOf
import kotlin.collections.mapNotNull
import kotlin.collections.setOf
import kotlin.math.absoluteValue

fun main() {
    solve(10) {
        val matrix = readLines()

        val n = matrix.size
        val m = matrix.first().length
        val q = Stack<VertexWithDistance>()

        val visited = List(n) { BooleanArray(m) { false } }
        fun Vertex.isVisited() = visited[i][j]
        fun Vertex.setVisited() {
            visited[i][j] = true
        }

        val infty = Int.MIN_VALUE
        val leftColor = -1
        val rightColor = -2
        val colors = List(n) { IntArray(m) { infty } }
        fun Vertex.color() = colors[i][j]
        fun Vertex.setColor(color: Int) {
            colors[i][j] = color
        }

        var _startVertex: Vertex? = null

        for (i in 0..< n) {
            for (j in 0 ..< m) {
                if (matrix[i][j] == 'S') {
                    val v = Vertex(i, j)
                    _startVertex = v
                    q.add(VertexWithDistance(0, v))
                    v.setVisited()
                    v.setColor(0)
                    break
                }
            }
            if (q.isNotEmpty()) break
        }

        val startVertex = _startVertex!!

        fun sym(vertex: Vertex) = matrix[vertex.i][vertex.j]

        fun canGo(from: Vertex, to: Vertex): Boolean {
            if (to.isNotValid(n, m)) return false

            if (to.isVisited()) return false

            val shi = to.i - from.i
            val shj = to.j - from.j

            val up = setOf('J', 'L', '|', 'S')
            val down = setOf('7', 'F', '|', 'S')
            val left = setOf('J', '7', '-', 'S')
            val right = setOf('L', 'F', '-', 'S')

            val fromC = sym(from)

            if(
                shi == 1 && fromC !in down ||
                shi == -1 && fromC !in up ||
                shj == 1 && fromC !in right ||
                shj == -1 && fromC !in left
            ) return false

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

        var maxD = 0
        while (q.isNotEmpty()) {
            val (_, v) = q.pop()
            v.setColor(maxD)
            ++maxD
            for (sh in shifts) {
                val adjV = Vertex(v.i + sh.first, v.j + sh.second)
                if (!canGo(v, adjV)) continue

                adjV.setVisited()
                q.add(VertexWithDistance(0, adjV))
            }
        }

        val loopSize = maxD

        markSides(loopSize, n, m, colors, leftColor, rightColor, startVertex)

        for (i in 0 ..< n) {
            for (j in 0 ..< m) {
                val sv = Vertex(i, j)
                if (sv.isVisited()) continue
                if (sv.color() > 0 || sv.color() == infty) continue

                val q2 = ArrayDeque<Vertex>()
                sv.setVisited()
                q2.add(sv)
                val myColor = sv.color()
                while (q2.isNotEmpty()) {
                    val v = q2.removeLast()
                    for (sh in shifts) {
                        val adjV = Vertex(v.i + sh.first, v.j + sh.second)
                        if (adjV.isNotValid(n, m)) continue
                        if (adjV.isVisited()) continue

                        adjV.setVisited()
                        adjV.setColor(myColor)
                        q2.add(adjV)
                    }
                }
            }
        }

        println(colors.joinToString("\n") {
            it.joinToString("") { k ->
                if (k > 0) "W"
                else if (k == leftColor) "L"
                else if (k == rightColor) "R"
                else "X"
            }
        })

        var ctr = 0
        for (i in 0  ..< n) {
            for (j in 0 ..< m) {
                if (colors[i][j] == rightColor) {
                    ++ctr
                }
            }
        }

        println(ctr)
    }
}

val shifts = listOf(-1, 0, 1).flatMap { out ->
    listOf(-1, 0, 1).mapNotNull {
        if (out == 0 && it == 0) null else {
            it to out
        }
    }
}

data class Vertex(val i: Int, val j: Int)
data class VertexWithDistance(val distance: Int, val vertex: Vertex)

fun Vertex.isNotValid(n: Int, m: Int) = i < 0 || i >= n || j < 0 || j >= m

fun markSides(loopSize: Int, n: Int, m: Int, colors: List<IntArray>, leftColor: Int, rightColor: Int, startVertex: Vertex) {
    fun Vertex.color() = colors[i][j]
    fun Vertex.setColor(color: Boolean) {
        if (isNotValid(n, m)) return
        if (color() >= 0) return
        colors[i][j] = if (color) leftColor else rightColor
    }

    var cur = startVertex
    while (true) {
        // println(cur.color())
        val nextVertexI = (cur.color() + 1) % loopSize
        val shift = shifts.first { shift ->
            val v = Vertex(cur.i + shift.first, cur.j + shift.second)
            !v.isNotValid(n, m) && v.color() == nextVertexI
        }
        when {
            shift.first.absoluteValue == 1 -> {
                val prima = shift.first != 1

                Vertex(cur.i, cur.j - 1).setColor(prima)
                Vertex(cur.i, cur.j + 1).setColor(!prima)

                Vertex(cur.i + shift.first, cur.j - 1).setColor(prima)
                Vertex(cur.i + shift.first, cur.j + 1).setColor(!prima)
            }
            shift.second.absoluteValue == 1 -> {
                val prima = shift.second == 1

                Vertex(cur.i - 1, cur.j).setColor(prima)
                Vertex(cur.i + 1, cur.j).setColor(!prima)

                Vertex(cur.i - 1, cur.j + shift.second).setColor(prima)
                Vertex(cur.i + 1, cur.j + shift.second).setColor(!prima)
            }
        }
        if (nextVertexI == 0) break
        cur = Vertex(cur.i + shift.first, cur.j + shift.second)
    }
}
