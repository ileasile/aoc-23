import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.sqrt

fun main() {
    solve(6) {
        val timeStr = readLine()
        val distStr = readLine()

        fun String.splitToNum() = substringAfter(':').trim().split(Regex(" +")).joinToString("").toLong()

        class Race(val time: Long, val dist: Long)

        val r = Race(timeStr.splitToNum(), distStr.splitToNum())

        val D = r.time * r.time - 4 * r.dist
        val result = if (D <= 0) {
            0
        } else {
            val sqD = sqrt(D.toDouble())
            val r1 = (r.time + sqD) / 2
            val r2 = (r.time - sqD) / 2

            println("$r1 $r2")

            (floor(r1) - ceil(r2) + 1).toLong()
        }

        println(result)

    }
}
