import kotlin.math.absoluteValue

fun main() {
    solve(11) {
        val inputLines = readLines()
        val n = inputLines.size
        val m = inputLines.first().length

        class Gal(val i: Int, val j: Int)
        val galaxies = mutableListOf<Gal>()
        val rows = BooleanArray(n) { false }
        val cols = BooleanArray(m) { false }

        for (i in 0..<n) {
            for (j in 0..<n) {
                if (inputLines[i][j] == '#') {
                    galaxies.add(Gal(i, j))
                    rows[i] = true
                    cols[j] = true
                }
            }
        }

        var ctr = -1
        val rowMap = IntArray(n) {
            ctr += if (rows[it]) 1 else 2
            ctr
        }
        ctr = -1
        val colMap = IntArray(m) {
            ctr += if (cols[it]) 1 else 2
            ctr
        }

        val newGals = galaxies.map { Gal(rowMap[it.i], colMap[it.j]) }

        val ng = newGals.size
        var dist = 0L
        for (i in 0 ..< ng) {
            for (j in i+1 ..< ng) {
                val g1 = newGals[i]
                val g2 = newGals[j]

                dist += (g1.i - g2.i).absoluteValue + (g1.j - g2.j).absoluteValue
            }
        }

        println(dist)
    }
}
