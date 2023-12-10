import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.sqrt

fun main() {
    solve(6) {
        val timeStr = readLine()
        val distStr = readLine()

        fun String.splitToNums() = substringAfter(':').trim().split(Regex(" +")).map { it.toInt() }

        class Race(val time: Int, val dist: Int)

        val races = timeStr.splitToNums().zip(distStr.splitToNums()).map { Race(it.first, it.second) }

        fun <T> List<T>.prodOf(f: (T) -> Long): Long {
            return fold(1L) { acc: Long, t: T -> acc * f(t) }
        }

        val result = races.prodOf { r ->
            val D = r.time * r.time - 4 * r.dist
            if (D <= 0) {
                0
            } else {
                val sqD = sqrt(D.toDouble())
                val r1 = (r.time + sqD) / 2
                val r2 = (r.time - sqD) / 2

                println("$r1 $r2")

                (floor(r1) - ceil(r2) + 1).toLong()
            }
        }

        println(result)

    }
}
