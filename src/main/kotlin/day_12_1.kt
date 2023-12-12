fun main() {
    solve(12) {
        val inputLines = readLines()
        println(inputLines.sumOf { line ->
            val (s, d) = line.split(" ")
            val description = d.split(",").map { it.toInt() }
            count_12(s, description)
            // 6981
        })
    }
}

fun count_12(s: String, description: List<Int>): Int {
    val qCount = s.count { it == '?' }
    val variantsCount = 1 shl qCount

    val sa = BooleanArray(s.length)
    for ((i, c) in s.withIndex()) {
        sa[i] = c == '#'
    }

    var result = 0
    for (variant in 0 ..< variantsCount) {
        var k = 1
        for ((i, c) in s.withIndex()) {
            if (c == '?') {
                sa[i] = (k and variant) > 0
                k = k shl 1
            }
        }

        if (description == describe_12(sa)) ++result
    }

    return result
}

fun describe_12(s: BooleanArray): List<Int> {
    return buildList {
        var ctr = 0
        for (c in s) {
            if (c) {
                ++ctr
            } else {
                if (ctr != 0) {
                    add(ctr)
                }
                ctr = 0
            }
        }
        if (ctr!=0) add(ctr)
    }
}
