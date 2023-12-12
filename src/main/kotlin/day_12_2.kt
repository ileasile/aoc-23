import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.api.toColumn
import org.jetbrains.kotlinx.dataframe.api.toDataFrame

fun main() {
    solve(12) {
        val inputLines = readLines()
        val repeatCount = 5
        println(inputLines.subList(0, 1).sumOf { line ->
            val (s, d) = line.split(" ")
            val description = d.split(",").map { it.toInt() }.repeatList(repeatCount)
            val record = ("$s?").repeat(repeatCount - 1) + s
            calcArrangements(record, description)
        })
    }
}

fun renderAsTable(
    record: String,
    mask: BooleanArray,
    vararg dps: Array<LongArray>,
): DataFrame<*> {
    val maskSize = mask.size
    return " E$record"
        .mapIndexed { i, c -> if (i > 0) "${i - 1}\n$c" else " " }
        .mapIndexed { columnIndex, name ->
            val values = if (columnIndex == 0) {
                mask.map { if (it) "#" else "." }
            }
            else {
                (0 ..< maskSize).map { i ->
                    dps.map { dp ->
                        dp[columnIndex - 1][i]
                    }
                }
            }
            values.toColumn(name)
        }.toDataFrame()
}

fun calcArrangements(
    record: String,
    description: List<Int>,
    display: (Any) -> Unit = {},
): Long {

    // 1, 2, 3
    // 0101101110

    val mask = getMask(description)

    val n = record.length + 1
    val m = mask.size

    val dpPt = Array(n) { LongArray(m) { 0 } }
    val dpBr = Array(n) { LongArray(m) { 0 } }

    dpPt[0][0] = 1
    dpBr[0][0] = 1

    for (i in 1 ..< n) {
        val c = record[i - 1]
        if (c == '.' || c == '?') {
            dpPt[i][0] = dpPt[i - 1][0]
            dpBr[i][0] = dpBr[i - 1][0]
        }

        for (j in 1 ..< m) {
            fun considerPoint() {
                if (!mask[j]) {
                    dpPt[i][j] += dpPt[i - 1][j]
                    dpBr[i][j] += dpPt[i - 1][j]
                }
            }

            fun considerBroken() {
                if (mask[j]) {
                    dpPt[i][j] += dpBr[i - 1][j - 1]
                    dpBr[i][j] += dpBr[i - 1][j - 1]
                } else {
                    dpPt[i][j] += dpBr[i][j - 1]
                }
            }

            when(c) {
                '.' -> {
                    considerPoint()
                }
                '#' -> {
                    considerBroken()
                }
                '?' -> {
                    considerPoint()
                    considerBroken()
                }
            }
        }
    }

    display(renderAsTable(record, mask, dpBr, dpPt))

    return dpPt.last().last()
}

fun getMask(description: List<Int>): BooleanArray {
    val sum = description.sum()
    val n = description.size

    val mask = BooleanArray(n + sum + 1) { false }
    var ctr = 0
    for (i in 0 ..< n) {
        repeat(description[i]) {
            ++ctr
            mask[ctr] = true
        }
        ++ctr
    }

    return mask
}

fun List<Int>.repeatList(n: Int): List<Int> {
    return buildList {
        repeat(n) {
            addAll(this@repeatList)
        }
    }
}
